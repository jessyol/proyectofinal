document.addEventListener("DOMContentLoaded", () => {
  const usuariosBody = document.getElementById("usuariosBody");
  const form = document.getElementById("formNuevoUsuario");
  const msg = document.getElementById("msg");

  // Cargar usuarios
  async function cargarUsuarios() {
    try {
      const res = await fetch("http://localhost:8080/api/usuarios");
      const data = await res.json();
      usuariosBody.innerHTML = data.map(u => `
        <tr>
          <td>${u.id}</td>
          <td>${u.nombre}</td>
          <td>${u.email}</td>
          <td>${u.role}</td>
        </tr>
      `).join("");
    } catch (err) {
      usuariosBody.innerHTML = "<tr><td colspan='4'>Error al cargar usuarios</td></tr>";
    }
  }

  cargarUsuarios();

  // Crear nuevo usuario
  form.addEventListener("submit", async e => {
    e.preventDefault();
    const body = {
      id: parseInt(form.id.value,10),
      nombre: form.nombre.value,
      email: form.email.value,
      role: form.role.value
    };
    try {
      const res = await fetch("http://localhost:8080/api/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
      });
      const data = await res.json();
      if(!res.ok){ msg.innerText = data.detail || "Error"; return; }
      msg.innerText = "Usuario creado";
      form.reset();
      cargarUsuarios();
    } catch(err){
      msg.innerText = "Error de conexión";
    }
  });
});
