function obtenerDatosFormulario() {
    const formData = new FormData();
    const titulo = document.getElementById('titulo').value;
    const descripcion = document.getElementById('descripcion').value;
    const imagen = document.getElementById('subir_imagen').files[0];
    const postAncladoCheckbox = document.getElementById('postAnclado');
    const tipoPost = postAncladoCheckbox && postAncladoCheckbox.checked ? 'ANCLADO' : 'COMUN';
    const categoriaSeleccionada = document.querySelector('input[name="categoria"]:checked').value;

    if (!titulo.trim()) {
        alert('Por favor completa el título');
        return null;
    }
    if (!descripcion.trim()) {
        alert('Por favor completa la descripción');
        return null;
    }

    formData.append('titulo', titulo);
    formData.append('descripcion', descripcion);
    if (imagen) {
        formData.append('imagen', imagen);
    }
    formData.append('tipo', tipoPost);
    formData.append('categoria', categoriaSeleccionada);
    
    return formData;
}

async function enviarPost(formData) {
    formData.append('accion', 'agregar');
    const response = await fetch('PostControlador', {
        method: 'POST',
        body: formData
    });
    return await response.json();
}
async function manejarEnvioFormulario(evento) {
    evento.preventDefault();
    try {
        const datos = obtenerDatosFormulario();
        const respuesta = await enviarPost(datos);
        if (respuesta.success) {
            window.location.href = 'PostControlador?accion=mostrarPagPrincipal';
        } else {
            alert('Error al crear el post: ' + respuesta.message);
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.getElementById('postForm');
    formulario.addEventListener('submit', manejarEnvioFormulario);
});