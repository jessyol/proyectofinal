const API = "http://localhost:8080/api/productos";

/**
 * Función "ayudante" que realiza peticiones fetch
 * y añade automáticamente el token de autenticación.
 */
async function fetchAPI(url, options = {}) {
    const token = localStorage.getItem("token");
    
    // Configura los headers por defecto
    const headers = {
        "Content-Type": "application/json",
        ...options.headers, // Permite sobrescribir headers si es necesario
    };

    // Si tenemos un token, lo añadimos a la cabecera de Autorización
    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    try {
        const response = await fetch(url, { ...options, headers });
        return response;
    } catch (error) {
        console.error("Error en la petición fetch:", error);
        throw error;
    }
}

/**
 * Lógica que se ejecuta al cargar la página
 */
document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "login.html";
        return;
    }

    cargarProductos();
    cargarStats(); // Asumiendo que tienes esta función

    // Asegúrate de que tu formulario tenga el id="formNuevoProducto"
    const form = document.getElementById("formNuevoProducto");
    if (form) {
        form.addEventListener("submit", crearProducto);
    }
});

/**
 * Carga la lista de productos
 */
async function cargarProductos() {
    try {
        // CORRECCIÓN DE URL: Se usa fetchAPI y solo la constante API
        const res = await fetchAPI(API); 
        if (!res.ok) throw new Error(`Error al cargar: ${res.statusText}`);

        const productos = await res.json();
        const tbody = document.getElementById("productosBody");
        tbody.innerHTML = ""; // Limpia la tabla

        productos.forEach(p => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>$${p.precio.toFixed(2)}</td>
                <td>${p.cantidad}</td> <td><button onclick="borrarProducto(${p.id})">Eliminar</button></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        document.getElementById("productosBody").innerHTML = `<tr><td colspan="5">Error: ${error.message}</td></tr>`;
    }
}

/**
 * Crea un nuevo producto
 */
async function crearProducto(e) {
    e.preventDefault();
    const form = e.target;
    const body = {
        nombre: form.nombre.value,
        precio: parseFloat(form.precio.value),
        cantidad: parseInt(form.stock.value, 10) // CORRECCIÓN: Enviar 'cantidad' al backend
    };

    try {
        // CORRECCIÓN DE URL: Se usa fetchAPI y solo la constante API
        const res = await fetchAPI(API, { 
            method: "POST",
            body: JSON.stringify(body)
        });

        if (res.ok) {
            document.getElementById("msg").innerText = "Producto creado ✅";
            form.reset();
            cargarProductos();
        } else {
            const err = await res.json();
            document.getElementById("msg").innerText = "Error: " + (err.detail || "No se pudo crear");
        }
    } catch (error) {
        document.getElementById("msg").innerText = "Error de conexión.";
    }
}

/**
 * Borra un producto
 */
async function borrarProducto(id) {
    if (!confirm("¿Eliminar producto?")) return;

    // CORRECCIÓN DE URL: Se usa fetchAPI y la URL correcta con el ID
    const res = await fetchAPI(`${API}/${id}`, { 
        method: "DELETE" 
    });

    if (res.ok) {
        cargarProductos();
    } else {
        alert("Error al eliminar");
    }
}

/**
 * Carga las estadísticas (si las tienes)
 */
async function cargarStats() {
    // Aquí iría la lógica para cargar las estadísticas
    // console.log("Cargando estadísticas...");
}

/**
 * Cierra la sesión
 */
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("userRole"); 
    window.location.href = "login.html";
}