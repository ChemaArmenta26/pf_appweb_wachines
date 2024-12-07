function parseDate(dateStr) {
    const [day, month, year] = dateStr.split('/');
    return new Date(year, month - 1, day);
}

document.addEventListener('DOMContentLoaded', function () {
    const container = document.querySelector('main');
    const posts = Array.from(container.getElementsByClassName('post'));
    
    // Agregar estilos para el icono de anclado
    const style = document.createElement('style');
    style.textContent = `
        .post {
            position: relative;
        }
        .icono-anclado {
            position: absolute;
            top: 10px;
            right: 80px;
            z-index: 1;
        }
        .icono-anclado img {
            width: 20px;
            height: 20px;
            transform: rotate(-45deg);
        }
    `;
    document.head.appendChild(style);

    // Ordenar los posts
    posts.sort((a, b) => {
        const tipoA = a.dataset.tipo;
        const tipoB = b.dataset.tipo;
        
        // Los posts anclados van primero
        if (tipoA === 'ANCLADO' && tipoB !== 'ANCLADO') return -1;
        if (tipoA !== 'ANCLADO' && tipoB === 'ANCLADO') return 1;
        
        // Si tienen el mismo tipo, ordenar por fecha
        const fechaA = parseDate(a.dataset.fecha);
        const fechaB = parseDate(b.dataset.fecha);
        return fechaB - fechaA;
    });

    // Agregar icono de anclado y reordenar
    posts.forEach(post => {
        if (post.dataset.tipo === 'ANCLADO') {
            // Solo agregar el icono si no existe ya
            if (!post.querySelector('.icono-anclado')) {
                const iconoAnclado = document.createElement('div');
                iconoAnclado.className = 'icono-anclado';
                iconoAnclado.innerHTML = `<img src="img/pin.png" alt="Post Anclado">`;
                post.insertBefore(iconoAnclado, post.firstChild);
            }
        }
        container.appendChild(post);
    });

    // Volver a configurar los eventos después del reordenamiento
    configurarEventos();
});

// administrarPosts.js
function obtenerDatosPost(boton) {
    const post = boton.closest('.post');
    return {
        id: boton.dataset.id,
        tipo: post.dataset.tipo
    };
}

async function enviarSolicitud(endpoint, datos) {
    try {
        const response = await fetch(endpoint, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(datos)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error en la solicitud:', error);
        throw error;
    }
}

async function manejarAnclarPost(evento) {
    try {
        const boton = evento.currentTarget;
        const post = boton.closest('.post');
        const {id, tipo} = obtenerDatosPost(boton);
        
        // Determinar si vamos a anclar o desanclar
        const accion = tipo === 'ANCLADO' ? 'desanclar' : 'anclar';
        
        const datos = {id, accion};
        const respuesta = await enviarSolicitud('AdminPostServlet', datos);
        
        if (respuesta.success) {
            // Actualizar el UI sin recargar la página
            post.dataset.tipo = accion === 'anclar' ? 'ANCLADO' : 'NORMAL';
            
            // Actualizar el ícono de anclado
            if (accion === 'anclar') {
                const iconoAnclado = document.createElement('div');
                iconoAnclado.className = 'icono-anclado';
                iconoAnclado.innerHTML = `<img src="img/pin.png" alt="Post Anclado">`;
                post.insertBefore(iconoAnclado, post.firstChild);
            } else {
                const iconoAnclado = post.querySelector('.icono-anclado');
                if (iconoAnclado) {
                    iconoAnclado.remove();
                }
            }
            
            // Reordenar los posts
            const container = document.querySelector('main');
            const posts = Array.from(container.getElementsByClassName('post'));
            posts.sort((a, b) => {
                const tipoA = a.dataset.tipo;
                const tipoB = b.dataset.tipo;
                if (tipoA === 'ANCLADO' && tipoB !== 'ANCLADO') return -1;
                if (tipoA !== 'ANCLADO' && tipoB === 'ANCLADO') return 1;
                const fechaA = parseDate(a.dataset.fecha);
                const fechaB = parseDate(b.dataset.fecha);
                return fechaB - fechaA;
            });
            posts.forEach(post => container.appendChild(post));
        } else {
            alert(`Error al ${accion} el post: ${respuesta.message}`);
        }
    } catch (error) {
        console.error(`Error al ${accion} el post:`, error);
        alert(`Ocurrió un error al intentar ${accion} el post.`);
    }
}

async function manejarEliminarPost(evento) {
    try {
        if (!confirm('¿Estás seguro de que quieres eliminar este post?')) {
            return;
        }
        const boton = evento.currentTarget;
        const id = obtenerDatosPost(boton).id;
        const datos = {id, accion: 'eliminar'};
        const respuesta = await enviarSolicitud('AdminPostServlet', datos);
        
        if (respuesta.success) {
            const post = boton.closest('.post');
            post.remove();
        } else {
            alert(`Error al eliminar el post: ${respuesta.message}`);
        }
    } catch (error) {
        console.error('Error al eliminar el post:', error);
        alert('Ocurrió un error al intentar eliminar el post.');
    }
}

function configurarEventos() {
    const botonesAnclar = document.querySelectorAll('.anclar-btn');
    const botonesEliminar = document.querySelectorAll('.eliminar-btn');
    
    botonesAnclar.forEach(boton => {
        // Remover event listeners antiguos antes de agregar nuevos
        boton.removeEventListener('click', manejarAnclarPost);
        boton.addEventListener('click', manejarAnclarPost);
    });
    
    botonesEliminar.forEach(boton => {
        // Remover event listeners antiguos antes de agregar nuevos
        boton.removeEventListener('click', manejarEliminarPost);
        boton.addEventListener('click', manejarEliminarPost);
    });
}

document.addEventListener('DOMContentLoaded', configurarEventos);


