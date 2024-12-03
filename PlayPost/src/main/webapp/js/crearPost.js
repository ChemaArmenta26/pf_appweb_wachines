// Función para obtener los datos del formulario
function obtenerDatosFormulario() {
    const formData = new FormData();

    const titulo = document.getElementById('titulo').value;
    const descripcion = document.getElementById('descripcion').value;
    const imagen = document.getElementById('subir_imagen').files[0];
    
    const postAncladoCheckbox = document.getElementById('postAnclado');
    const tipoPost = postAncladoCheckbox && postAncladoCheckbox.checked ? 'ANCLADO' : 'COMUN';
    
    const categoriaSeleccionada = document.querySelector('input[name="categoria"]:checked').value;

    formData.append('titulo', titulo);
    formData.append('descripcion', descripcion);
    formData.append('imagen', imagen);
    formData.append('tipo', tipoPost);
    formData.append('categoria', categoriaSeleccionada);

    return formData;
}

// Función para enviar los datos al servidor
async function enviarPost(formData) {
    formData.append('accion', 'agregar');
    const response = await fetch('PostControlador', {
        method: 'POST',
        body: formData
    });
    return await response.json();
}

// Función que maneja el envío del formulario
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
        alert('Error al crear el post');
    }
}

// Cuando la página termine de cargar, configuramos el formulario
document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.getElementById('postForm');

    formulario.addEventListener('submit', manejarEnvioFormulario);
});
