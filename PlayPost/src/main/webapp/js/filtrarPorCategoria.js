function configurarEventos() {
    const botonesCategorias = document.querySelectorAll('.categoria');
    botonesCategorias.forEach(boton => {
        boton.addEventListener('click', manejarEvento);
    });
}

function manejarEvento(evento) {
    const boton = evento.currentTarget;
    const categoria = obtenerDatosBoton(boton);
    
    // Redirigir directamente a la URL con los parámetros
    window.location.href = `PostControlador?accion=filtrarCategoria&categoria=${categoria}&vista=normal`;
}

function obtenerDatosBoton(boton) {
    const categoria = boton.dataset.categoria;
    console.log('Categoría seleccionada:', categoria); 
    return categoria;
}

document.addEventListener('DOMContentLoaded', configurarEventos);