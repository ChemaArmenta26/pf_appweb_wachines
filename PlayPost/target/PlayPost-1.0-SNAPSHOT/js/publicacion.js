let currentPostId = null;

document.addEventListener('DOMContentLoaded', () => {
    initPost();
    initComentarios();
});

function cargarImagen(imgElement, src, fallbackSrc = '/img/default-avatar.png') {
    if (!src) {
        imgElement.src = fallbackSrc;
        return;
    }

    const img = new Image();
    img.onload = function () {
        imgElement.src = src;
    };
    img.onerror = function () {
        imgElement.src = fallbackSrc;
    };
    img.src = src;
}

function initPost() {
    const urlParams = new URLSearchParams(window.location.search);
    currentPostId = urlParams.get('id');

    if (currentPostId) {
        console.log('Cargando post con ID:', currentPostId);
        cargarPost(currentPostId);
    } else {
        console.log('No hay ID de post');
    }
}

function initComentarios() {
    const formComentario = document.getElementById('form-comentario');
    if (formComentario) {
        formComentario.addEventListener('submit', async (e) => {
            e.preventDefault();
            await manejarNuevoComentario(e);
        });
    }

    document.addEventListener('click', manejarClicksBotones);

    document.addEventListener('submit', manejarSubmitsFormularios);
}


function manejarClicksBotones(e) {
    if (e.target.classList.contains('btnResponder')) {
        const comentarioContainer = e.target.closest('.comentarioPost');
        const formRespuesta = comentarioContainer.querySelector('.formRespuestaHijo');
        if (formRespuesta) {
            const estaVisible = formRespuesta.style.display === 'block';
            formRespuesta.style.display = estaVisible ? 'none' : 'block';
            if (!estaVisible) {
                formRespuesta.querySelector('textarea').focus();
            }
        }
    }

    if (e.target.classList.contains('cancelar')) {
        const formRespuesta = e.target.closest('.formRespuestaHijo');
        if (formRespuesta) {
            formRespuesta.style.display = 'none';
            formRespuesta.querySelector('form').reset();
        }
    }
}

function manejarSubmitsFormularios(e) {
    if (e.target.classList.contains('form-respuesta')) {
        e.preventDefault();
        const comentarioId = e.target.closest('.comentarioContenedor').dataset.comentarioId;
        manejarNuevaRespuesta(e.target, comentarioId);
    }
}

async function cargarPost(id) {
    if (!id) {
        console.error('No se proporcionó ID del post');
        return;
    }

    console.log('Iniciando cargarPost con ID:', id);

    try {
        const response = await fetch(`PublicacionServlet?id=${id}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Cache-Control': 'no-cache, no-store, must-revalidate',
                'Pragma': 'no-cache',
                'Expires': '0'
            },
            credentials: 'same-origin'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Datos frescos recibidos del servidor:', data);
        await actualizarUI(data);
    } catch (error) {
        console.error('Error al cargar el post:', error);
        alert('Error al cargar el post. Por favor, intente de nuevo.');
    }
}

async function actualizarUI(data) {
    if (!data)
        return;

    console.log('Data recibida:', data);

    const elementos = {
        titulo: document.querySelector('.tituloPost'),
        fecha: document.querySelector('.fechaPost'),
        imagen: document.querySelector('.imgPost'),
        contenido: document.querySelector('.infoPost p')
    };

    if (elementos.imagen) {
        console.log('URL de imagen:', data.imagenData);
        elementos.imagen.src = data.imagenData || '/img/default-avatar.png';
        debugImageLoad(elementos.imagen);
    }

    if (elementos.titulo)
        elementos.titulo.textContent = data.titulo;
    if (elementos.fecha)
        elementos.fecha.textContent = data.fechaFormateada;
    if (elementos.contenido)
        elementos.contenido.textContent = data.contenido;

    if (elementos.imagen) {
        cargarImagen(elementos.imagen, data.imagenData);
    }

    await actualizarComentarios(data.comentarios || []);
}

function actualizarComentarios(comentarios) {
    const container = document.getElementById('comentariosContenedor');
    if (!container)
        return;

    container.innerHTML = '';

    const contadorElement = document.getElementById('contador-comentarios');
    if (contadorElement) {
        const comentariosPrincipales = comentarios.filter(comentario => !comentario.comentarioMayorId);
        contadorElement.textContent = comentariosPrincipales.length;
    }

    comentarios
        .filter(comentario => !comentario.comentarioMayorId)
        .forEach(comentario => {
            const elementoComentario = crearElementoComentario(comentario);
            container.appendChild(elementoComentario);
        });
}

async function manejarNuevoComentario(e) {
    try {
        const form = e.target;
        const comentario = form.querySelector('textarea[name="comentario"]').value.trim();
        const postId = form.querySelector('input[name="postId"]').value;
        const usuarioId = form.querySelector('input[name="usuarioId"]').value;

        if (!comentario) {
            alert('El comentario no puede estar vacío');
            return;
        }

        const datos = {comentario, postId, usuarioId};
        const response = await fetch('ComentarioServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(datos)
        });

        const responseData = await response.json();

        if (responseData.success) {
            const nuevoComentario = crearElementoComentario(responseData.comentario);
            document.getElementById('comentariosContenedor').appendChild(nuevoComentario);

            const contadorElement = document.getElementById('contador-comentarios');
            if (contadorElement) {
                contadorElement.textContent = parseInt(contadorElement.textContent) + 1;
            }

            form.reset();
        } else {
            throw new Error(responseData.error || 'Error al enviar el comentario');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error al enviar el comentario');
    }
}

async function recargarComentarios(postId) {
    try {
        const response = await fetch(`PublicacionServlet?id=${postId}`, {
            headers: {
                'Accept': 'application/json',
                'Cache-Control': 'no-cache'
            }
        });

        if (!response.ok)
            throw new Error(`HTTP error! status: ${response.status}`);

        const data = await response.json();
        actualizarComentarios(data.comentarios || []);
    } catch (error) {
        console.error('Error al recargar comentarios:', error);
    }
}

async function manejarNuevaRespuesta(form, comentarioId) {
    try {
        const comentario = form.querySelector('textarea[name="comentario"]').value.trim();
        const postId = document.querySelector('input[name="postId"]').value;
        const usuarioId = document.querySelector('input[name="usuarioId"]').value;

        if (!comentario) {
            alert('La respuesta no puede estar vacía');
            return;
        }

        const datos = {
            comentario,
            postId,
            usuarioId,
            comentarioMayorId: comentarioId
        };

        const response = await fetch('ComentarioServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams(datos)
        });

        if (!response.ok) {
            throw new Error('Error al enviar la respuesta');
        }

        const responseData = await response.json();

        if (responseData.success) {
            form.reset();
            form.closest('.formRespuestaHijo').style.display = 'none';

            await recargarComentarios(postId);
        } else {
            throw new Error(responseData.message || 'Error al procesar la respuesta');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('Error al enviar la respuesta');
    }
}

function crearElementoComentario(comentario) {
    const template = document.getElementById('comentario-template');
    if (!template) {
        console.error('Template de comentario no encontrado');
        return document.createElement('div');
    }

    const clon = template.content.cloneNode(true);
    const container = clon.querySelector('.comentarioContenedor');

    if (!container) {
        console.error('Contenedor no encontrado en el template');
        return document.createElement('div');
    }

    container.dataset.comentarioId = comentario.id;

    const fotoPerfil = container.querySelector('.fotoPerfil');
    const nombreUsuario = container.querySelector('.nombre-usuario');
    const contenidoComentario = container.querySelector('.contenido-comentario');

    if (fotoPerfil && comentario.usuario) {
        cargarImagen(fotoPerfil, comentario.usuario.avatar);
        fotoPerfil.alt = `Foto de perfil de ${comentario.usuario.nombreCompleto}`;
    }

    if (nombreUsuario) {
        nombreUsuario.textContent = comentario.usuario.nombreCompleto;
    }

    if (contenidoComentario) {
        contenidoComentario.textContent = comentario.contenido;
    }

    if (comentario.respuestas && comentario.respuestas.length > 0) {
        const respuestasContainer = container.querySelector('.respuestasContenedor');
        if (respuestasContainer) {
            comentario.respuestas.forEach(respuesta => {
                const elementoRespuesta = crearElementoRespuesta(respuesta);
                respuestasContainer.appendChild(elementoRespuesta);
            });
        }
    }

    return container;
}

function debugImageLoad(imgElement) {
    console.log('Intentando cargar imagen:', imgElement.src);
    imgElement.onerror = function (e) {
        console.error('Error cargando imagen:', imgElement.src, e);
    };
    imgElement.onload = function () {
        console.log('Imagen cargada exitosamente:', imgElement.src);
    };
}

function crearElementoRespuesta(respuesta) {
    const template = document.getElementById('respuesta-template');
    if (!template) {
        console.error('Template de respuesta no encontrado');
        return document.createElement('div');
    }

    const clon = template.content.cloneNode(true);
    const container = clon.querySelector('.comentarioHijoPost');

    const fotoPerfil = container.querySelector('.fotoPerfil');
    const nombreUsuario = container.querySelector('.nombre-usuario');
    const contenidoComentario = container.querySelector('.contenido-comentario');

    if (fotoPerfil && respuesta.usuario) {
        cargarImagen(fotoPerfil, respuesta.usuario.avatar);
        fotoPerfil.alt = `Foto de perfil de ${respuesta.usuario.nombreCompleto}`;
    }

    if (nombreUsuario) {
        nombreUsuario.textContent = respuesta.usuario.nombreCompleto;
    }

    if (contenidoComentario) {
        contenidoComentario.textContent = respuesta.contenido;
    }

    return container;
}

window.cargarPost = cargarPost;
window.actualizarUI = actualizarUI;
window.actualizarComentarios = actualizarComentarios;