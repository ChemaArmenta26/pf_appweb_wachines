// Función para obtener los datos del post desde un botón
function obtenerDatosPost(boton) {
    const id = boton.dataset.id;
    return id;
}

// Función para enviar una solicitud al servidor
async function enviarSolicitud(endpoint, datos) {
    const response = await fetch(endpoint, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(datos)
    });
    return await response.json();
}

// Función para manejar la acción de "anclar"
async function manejarAnclarPost(evento) {
    try {
        const boton = evento.currentTarget;
        const id = obtenerDatosPost(boton);

            const datos = {id, accion: 'anclar'};
        const respuesta = await enviarSolicitud('AdminPostServlet', datos);

        if (respuesta.success) {
            window.location.reload();
        } else {
            alert(`Error al anclar el post: ${respuesta.message}`);
        }
    } catch (error) {
        console.error('Error al anclar el post:', error);
        alert('Ocurrió un error al intentar anclar el post.');
    }
}

// Función para manejar la acción de "eliminar"
async function manejarEliminarPost(evento) {
    try {
        if (!confirm('¿Estás seguro de que quieres eliminar este post?')) {
            return;
        }
        const boton = evento.currentTarget;
        const id = obtenerDatosPost(boton);

        const datos = {id, accion: 'eliminar'};
        const respuesta = await enviarSolicitud('AdminPostServlet', datos);

        if (respuesta.success) {
            window.location.reload(); 
        } else {
            alert(`Error al eliminar el post: ${respuesta.message}`);
        }
    } catch (error) {
        console.error('Error al eliminar el post:', error);
        alert('Ocurrió un error al intentar eliminar el post.');
    }
}

// Función para configurar eventos en los botones
function configurarEventos() {
    const botonesAnclar = document.querySelectorAll('.anclar-btn');
    const botonesEliminar = document.querySelectorAll('.eliminar-btn');
    
    botonesAnclar.forEach(boton => boton.addEventListener('click', manejarAnclarPost));
    botonesEliminar.forEach(boton => boton.addEventListener('click', manejarEliminarPost));
}

document.addEventListener('DOMContentLoaded', configurarEventos);
