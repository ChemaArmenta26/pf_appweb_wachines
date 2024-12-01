/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Función para convertir fecha (dd/mm/yyyy) a objeto Date
function parseDate(dateStr) {
    const [day, month, year] = dateStr.split('/');
    return new Date(year, month - 1, day);
}

document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('postContainer');
    const posts = Array.from(container.getElementsByClassName('entrada'));
    const style = document.createElement('style');
    
    style.textContent = `
        .icono-anclado {
            position: absolute;
            top: 10px;
            right: 10px;
            z-index: 1;
        }
        .icono-anclado img {
            width: 20px;
            height: 20px;
            transition: transform 0.3s ease;
        }
    `;
    
    document.head.appendChild(style);

    // Ordenar posts
    posts.sort((a, b) => {
        const tipoA = a.dataset.tipo;
        const tipoB = b.dataset.tipo;

        // Si uno es anclado y otro no, el anclado va primero
        if (tipoA === 'ANCLADO' && tipoB !== 'ANCLADO')
            return -1;
        if (tipoA !== 'ANCLADO' && tipoB === 'ANCLADO')
            return 1;

        // Si ambos son del mismo tipo, ordenar por fecha
        const fechaA = parseDate(a.dataset.fecha);
        const fechaB = parseDate(b.dataset.fecha);
        return fechaB - fechaA;
    });

    // Añadir iconos a posts anclados y reordenar
    posts.forEach(post => {
        if (post.dataset.tipo === 'ANCLADO') {
            const iconoAnclado = document.createElement('div');
            iconoAnclado.className = 'icono-anclado';
            iconoAnclado.innerHTML = `
                <img src="img/pin.png" alt="Post Anclado">
            `;
            post.insertBefore(iconoAnclado, post.firstChild);
        }
        container.appendChild(post);
    });
});
