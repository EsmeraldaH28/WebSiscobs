package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Persona;
import modelo.Usuario;
import modelo.UsuarioDAO;

@WebServlet(name = "srvUsuario", urlPatterns = {"/session"})
public class srvUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "identificar":
                    this.iniciarSession(request, response);
                    break;
                case "cerrar":
                    cerrarsession(request, response);
                    break;
                case "listar":
                    listarUsuarios(response);
                    break;
                case "listarEmpleadosSinLogin":
                    listarEmpleadosSinLogin(response);
                    break;
                case "registrar":
                    registrar(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "leer":
                    leerUsuario(request, response);
                    break;
                case "desactivarUsuario":
                    desactivarUsuarios(request, response);
                    break;
                case "solicitarCambioC":
                    solicitarCambioContrasenia(request, response);
                    break;
                case "cambiarPassword":
                    cambiarPassword(request, response);
                    break;
            }
        } else {
            response.sendRedirect("index.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void iniciarSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("datos") != null) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(request.getParameter("datos"), Usuario.class);

            UsuarioDAO dao = new UsuarioDAO();
            try {
                usuario = dao.identificar(usuario);
                if (usuario == null) {
                    this.printMessage("Credenciales incorrectas o usuario desactivado. Para más información contactar a administración", false, response);
                } else {
                    request.getSession().setAttribute("usuario", usuario);
                    this.printMessage("Acceso permitido", true, response);
                }
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        }
    }

    private void printMessage(String msj, boolean rpt, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"rpt\": " + rpt + ", \"msj\": \"" + msj + "\"}");
    }

    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");
    }

    private void cerrarsession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("usuario");
        sesion.invalidate();
        response.sendRedirect("index.jsp");
    }
    
    private void listarUsuarios(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> emp = dao.listarUsuarios();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void listarEmpleadosSinLogin(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Persona> emp = dao.listarPersonasSinLogin();
            Gson gson = new Gson();
            response.setContentType("application/json;charset=UTF-8");
            String json = gson.toJson(emp, new TypeToken<List<Persona>>() {
            }.getType());
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("usu") != null) {
            Gson gson = new Gson();
            Usuario usu = gson.fromJson(request.getParameter("usu"), Usuario.class);
            String rpt;
            try {
                UsuarioDAO dao = new UsuarioDAO();
                dao.registrarUsuarios(usu);
                rpt = "Registrado";
                this.printMessage("Usuario " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("usu") != null) {
            Gson gson = new Gson();
            Usuario usu = gson.fromJson(request.getParameter("usu"), Usuario.class);
            String rpt;
            try {
                UsuarioDAO dao = new UsuarioDAO();
                dao.actualizarUsuario(usu);
                rpt = "Actualizado";
                this.printMessage("Usuario " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void leerUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDAO dao;
        Usuario usu;
        if (request.getParameter("id") != null) {
            usu = new Usuario();
            usu.setIdUsuario(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new UsuarioDAO();
                usu = dao.leerUsuario(usu);
                String json = new Gson().toJson(usu);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del usuario", false, response);
        }
    }

    private void desactivarUsuarios(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario emp;
        if (request.getParameter("id") != null) {
            emp = new Usuario();
            emp.setIdUsuario(Integer.parseInt(request.getParameter("id")));
            try {
                UsuarioDAO dao = new UsuarioDAO();
                dao.desactivarUsuarios(emp);
                this.printMessage("Usuario desactivado correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del usuario", false, response);
        }
    }
    
    private void solicitarCambioContrasenia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioDAO dao;
        Usuario usu;
        Map<String, Object> map = new HashMap<>();
        if (request.getParameter("nombreUsuario") != null) {
            usu = new Usuario();
            usu.setUsuario(request.getParameter("nombreUsuario"));
            try {
                dao = new UsuarioDAO();
                usu = dao.solicitarCambioContrasenia(usu);
                if(usu != null){
                    map.put("rpta", true);
                    map.put("msje", "Usuario encontrado correctamente");
                    map.put("body", usu);
                }else{
                    map.put("rpta", false);
                    map.put("msje", "Oh lo siento :( no se encontro el usuario");
                    map.put("body", null);
                }
                String json = new Gson().toJson(map);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parámetro del usuario", false, response);
        }
    }

    private void cambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            if (request.getParameter("usuario") != null 
                    && request.getParameter("clave") != null) {
            Usuario usu = new Usuario();
            usu.setUsuario(request.getParameter("usuario"));
            usu.setClave(request.getParameter("clave"));
            String rpt;
            try {
                UsuarioDAO dao = new UsuarioDAO();
                dao.cambiarContrasenia(usu);
                rpt = "actualizado";
                this.printMessage("Datos del usuario " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

}
