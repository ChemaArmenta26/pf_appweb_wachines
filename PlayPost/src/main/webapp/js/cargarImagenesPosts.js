/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function () {
    const imagenesPosts = document.querySelectorAll('.post-imagen');

    imagenesPosts.forEach(imagen => {
        const urlImagen = imagen.dataset.imagenUrl;

        if (urlImagen && urlImagen !== '') {
            const img = new Image();
            img.onload = function () {
                imagen.src = urlImagen;
            };
            img.onerror = function () {
                imagen.src = '/img/default-avatar.png';
            };
            img.src = urlImagen;
        }
    });
});
