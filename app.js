// app.js
document.addEventListener('DOMContentLoaded', () => {
  const hamb = document.getElementById('hamburger');
  const nav = document.getElementById('mainNav');
  hamb.addEventListener('click', () => {
    nav.classList.toggle('open');
  });
});

// Ejemplo: obtener productos (usa el backend en http://localhost:8080)
async function fetchProductos() {
  try {
    const res = await fetch('http://localhost:8080/api/productos');
    if(!res.ok) throw new Error('Error cargando productos');
    const productos = await res.json();
    console.log('Productos:', productos);
  } catch(e) {
    console.error(e);
  }
}