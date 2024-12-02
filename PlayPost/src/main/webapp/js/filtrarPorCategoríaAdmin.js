/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function configurarEventos(){
    const botonesCategorias = document.querySelectorAll('.categoria');
    
    botonesCategorias.forEach(boton => {
        boton.addEventListener('click', manejarEvento);
    });
}

async function manejarEvento(evento){
    try{
        const boton = evento.currentTarget;
        const categoria = obtenerDatosBoton(boton);
        
        const datos = {categoria, accion: 'filtrarCategoria'};
        const respuesta = await enviarSolicitud('PostControlador', datos);
        
        if (respuesta.success) {
            actualizarPosts(respuesta.posts);
        } else {
            alert(`Error al filtrar los posts: ${respuesta.message}`);
        }
    } catch (error){
        console.error('Error al filtrar los posts:', error);
        alert('Ocurrió un error al intentar filtrar los posts.');
    }
}

async function enviarSolicitud(endpoint, datos){
    const response = await fetch(endpoint, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(datos)
    });
    return await response.json();
}

function obtenerDatosBoton(boton) {
    const categoria = boton.dataset.categoria;
    return categoria;
}

function actualizarPosts(posts) {
    const main = document.querySelector('main');
    main.innerHTML = '';
    
    posts.forEach(post => {
        const postHTML = `
            <section class="post">
                <div class="contenedorPost">
                    <a href="PublicacionServlet?id=${post.id}">
                        <div class="contenido-principal">
                            <div class="infoPost">
                                <h1 class="tituloPost">${post.titulo}</h1>
                                <h3 class="fechaPost">${new Date(post.fechaHoraCreacion).toLocaleDateString()}</h3>
                                <p class="infoPost">${post.contenido}</p>
                            </div>
                            <div class="contenedorImg">
                                <img class="imgPost" src="data:image/jpeg;base64,${post.imagenData}" alt="Imagen del post">
                            </div>
                        </div>
                        <div class="info">
                            <label id="usuario">
                                <img id="fotoPerfil" src="img/iconamoon_profile-circle-bold.png">
                                ${post.usuario.nombreCompleto}
                            </label>
                        </div>             
                    </a>
                </div>
                <div class="contenedorIconos">
                    <img class="iconoPost eliminar-btn" src="img/trashcan.png" data-id="${post.id}" alt="Botón para eliminar el post">
                    <img class="iconoPost anclar-btn" src="img/pin.png" data-id="${post.id}" alt="Botón para anclar un post">
                </div>
            </section>
        `;
        main.innerHTML += postHTML;
    });
}

document.addEventListener('DOMContentLoaded', configurarEventos);
