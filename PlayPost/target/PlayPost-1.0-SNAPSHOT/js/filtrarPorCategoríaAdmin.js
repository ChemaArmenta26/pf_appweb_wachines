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

function manejarEvento(evento) {
    const boton = evento.currentTarget;
    const categoria = obtenerDatosBoton(boton);
    
    // Redirigir directamente a la URL con los parámetros
    window.location.href = `PostControlador?accion=filtrarCategoria&categoria=${categoria}`;
}

function obtenerDatosBoton(boton) {
    const categoria = boton.dataset.categoria;
    return categoria;
}

document.addEventListener('DOMContentLoaded', configurarEventos);
