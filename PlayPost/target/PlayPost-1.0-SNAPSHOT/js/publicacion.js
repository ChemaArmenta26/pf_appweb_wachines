let currentPostId = null;

document.addEventListener('DOMContentLoaded', () => {
    initPost();
    initComentarios();
});

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
            headers: {
                'Accept': 'application/json',
                'Cache-Control': 'no-cache, no-store, must-revalidate',
                'Pragma': 'no-cache'
            },
            credentials: 'same-origin'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Datos recibidos del servidor:', data);
        console.log('Comentarios recibidos:', data.comentarios);
        actualizarUI(data);
    } catch (error) {
        console.error('Error al cargar el post:', error);
        alert('Error al cargar el post. Por favor, intente de nuevo.');
    }
}

async function actualizarUI(data) {
    if (!data)
        return;

    const elementos = {
        titulo: document.querySelector('.tituloPost'),
        fecha: document.querySelector('.fechaPost'),
        imagen: document.querySelector('.imgPost'),
        contenido: document.querySelector('.infoPost p')
    };

    if (elementos.titulo)
        elementos.titulo.textContent = data.titulo;
    if (elementos.fecha)
        elementos.fecha.textContent = data.fechaFormateada;
    if (elementos.contenido)
        elementos.contenido.textContent = data.contenido;
    if (elementos.imagen && data.imagenData) {
        elementos.imagen.src = data.imagenData;
        elementos.imagen.alt = data.titulo || '';
    }

    await actualizarComentarios(data.comentarios || []);
}

function actualizarComentarios(comentarios) {
    console.log('Actualizando comentarios:', comentarios);

    const container = document.getElementById('comentariosContenedor');
    if (!container) {
        console.error('No se encontró el contenedor de comentarios');
        return;
    }

    container.innerHTML = '';

    const contadorElement = document.getElementById('contador-comentarios');
    if (contadorElement) {
        contadorElement.textContent = comentarios.length;
        console.log('Número total de comentarios:', comentarios.length);
    }

    comentarios.forEach((comentario, index) => {
        console.log(`Procesando comentario ${index + 1}:`, comentario);
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

        console.log('Datos del nuevo comentario:', datos);

        const response = await fetch('ComentarioServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Cache-Control': 'no-cache, no-store, must-revalidate',
                'Pragma': 'no-cache'
            },
            credentials: 'same-origin',
            body: new URLSearchParams(datos)
        });

        if (!response.ok)
            throw new Error(`Error HTTP: ${response.status}`);

        const responseData = await response.json();
        console.log('Respuesta del servidor al crear comentario:', responseData);

        const nuevoComentario = crearElementoComentario(responseData.comentario);
        const comentariosContainer = document.getElementById('comentariosContenedor');
        comentariosContainer.appendChild(nuevoComentario);

        await actualizarUI(responseData.postData);

        form.reset();
    } catch (error) {
        console.error('Error al manejar nuevo comentario:', error);
        alert('Error al enviar el comentario');
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
                'Content-Type': 'application/x-www-form-urlencoded',
                'Cache-Control': 'no-cache, no-store, must-revalidate',
                'Pragma': 'no-cache'
            },
            body: new URLSearchParams(datos)
        });

        if (!response.ok) {
            throw new Error('Error al enviar la respuesta');
        }

        const responseData = await response.json();
        console.log('Respuesta del servidor al crear respuesta:', responseData);

        const nuevaRespuesta = crearElementoRespuesta(responseData.respuesta);
        const respuestasContainer = form.closest('.comentarioContenedor').querySelector('.respuestasContenedor');
        respuestasContainer.appendChild(nuevaRespuesta);

        await actualizarUI(responseData.postData);

        form.reset();
        form.closest('.formRespuestaHijo').style.display = 'none';
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

    if (fotoPerfil) {
        fotoPerfil.src = comentario.usuario.avatar || '/img/iconoPerfil_rojo.png';
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
                const respuestaElement = crearElementoRespuesta(respuesta);
                respuestasContainer.appendChild(respuestaElement);
            });
        }
    }

    return container;
}

function crearElementoRespuesta(respuesta) {
    const div = document.createElement('div');
    div.className = 'comentarioHijoPost';
    div.innerHTML = `
        <label class="usuario">
            <img class="fotoPerfil" 
                 src="${respuesta.usuario.avatar || '/img/iconoPerfil_rojo.png'}" 
                 alt="Foto de perfil de ${respuesta.usuario.nombreCompleto}">
            ${respuesta.usuario.nombreCompleto}
        </label>
        <p>${respuesta.contenido}</p>
    `;
    return div;
}

window.cargarPost = cargarPost;
window.actualizarUI = actualizarUI;
window.actualizarComentarios = actualizarComentarios;