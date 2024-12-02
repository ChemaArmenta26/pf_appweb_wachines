/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function configurarEventos() {
    const botonesCategorias = document.querySelectorAll('.categoria');

    botonesCategorias.forEach(boton => {
        boton.addEventListener('click', manejarEvento);
    });
}

async function manejarEvento(evento) {
    try {
        const boton = evento.currentTarget;
        const categoria = obtenerDatosBoton(boton);

        const datos = {categoria, accion: 'filtrarCategoria'};

        const respuesta = await enviarSolicitud('PostControlador', datos);
        console.log('Respuesta del servidor:', respuesta);

        if (respuesta.success) {
            actualizarPosts(respuesta.posts);
        } else {
            alert(`Error al filtrar los posts: ${respuesta.message}`);
        }
    } catch (error) {
        console.error('Error al filtrar los posts:', error);
        alert('Ocurrió un error al intentar filtrar los posts.');
    }
}

async function enviarSolicitud(endpoint, datos) {
    console.log('Iniciando solicitud a:', endpoint); // Debug 4
    console.log('Datos de la solicitud:', JSON.stringify(datos)); // Debug 5
    const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    
    console.log('Response status:', response.status); // Debug 6
        console.log('Response ok:', response.ok); // Debug 7
    return await response.json();
}

function obtenerDatosBoton(boton) {
    return boton.dataset.categoria;
}

function actualizarPosts(posts) {
    const container = document.getElementById('postContainer');

    if (!posts || posts.length === 0) {
        container.innerHTML = '<p class="no-posts">No se encontraron publicaciones para esta categoría.</p>';
        return;
    }

    container.innerHTML = '';

    posts.forEach(post => {
        const fechaFormateada = new Date(post.fechaHoraCreacion).toLocaleDateString();
        const avatarSrc = post.usuario.avatar || '/img/iconamoon_profile-circle-bold';
        const numComentarios = post.comentarios ? post.comentarios.length : 0;

        const postHTML = `
            <section class="entrada" data-tipo="${post.tipo}" data-fecha="${fechaFormateada}" data-id="${post.id}">
                <h2>
                    <a href="PublicacionServlet?id=${post.id}">
                        ${post.titulo}
                    </a>
                </h2>
                <h3>${fechaFormateada}</h3>
                <img src="https://www.lanacion.com.ar/resizer/v2/lionel-messi-fue-a-la-cancha-y-sorprendio-a-todos-HDDKI5EXMRDABESAGAD2FMPUOQ.png?auth=f318caaefdc7e003119b3341aacfa764f16fc6ff90109bddd3df750dae292e56&width=880&height=586&quality=70&smart=true">
                <p class="contenido-breve">${post.contenido}</p>
                <div class="info">
                    <label>
                        <img id="iconoComentario" src="/img/material-symbols-light_comment-sharp.png">
                        ${numComentarios}
                    </label>
                    <label id="usuario">
                        <img id="fotoPerfil" src="${avatarSrc}">
                        ${post.usuario.nombreCompleto}
                    </label>
                </div>
            </section>
        `;
        container.innerHTML += postHTML;
    });
}

document.addEventListener('DOMContentLoaded', configurarEventos);