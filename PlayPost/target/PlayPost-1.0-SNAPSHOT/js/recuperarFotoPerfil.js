/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function previewImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('preview-avatar').src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const fotoPerfilNav = document.getElementById('fotoPerfil_nav');
    const rutaImagen = fotoPerfilNav.dataset.imagenUrl; // Esta será la ruta que viene de la base de datos

    if (rutaImagen && rutaImagen !== '') {
        fotoPerfilNav.src = rutaImagen;
    }
    // Si no hay ruta de imagen, se mantiene la imagen por defecto
});
