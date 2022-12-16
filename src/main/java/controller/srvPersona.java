package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Persona;
import modelo.PersonaDAO;
import modelo.Rol;
/**
 *
 * @author Esmeralda Hernandez
 */
@WebServlet(name = "srvPersona", urlPatterns = {"/srvPersona"})
public class srvPersona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "listar":
                    listar(response);
                    break;
                case "listarRoles":
                    listarRoles(response);
                    break;
                case "registrar":
                    registrar(request, response);
                    break;
                case "editar":
                    editar(request, response);
                    break;
                case "leer":
                    leer(request, response);
                    break;
                case "eliminar":
                    eliminar(request, response);
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
   private void printMessage(String msj, boolean rpt, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"rpt\": " + rpt + ", \"msj\": \"" + msj + "\"}");
    }

    private void printError(String msjError, HttpServletResponse response) throws IOException {
        response.getWriter().print("{\"msj\": \"" + msjError + "\"}");
    }

    private void listar(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            PersonaDAO dao = new PersonaDAO();
            List<Persona> persona = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(persona);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }
    
     private void listarRoles(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            PersonaDAO dao = new PersonaDAO();
            List<Rol> rol = dao.listarRoles();
            Gson gson = new Gson();
            String json = gson.toJson(rol);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }
    
    
    private void registrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("persona") != null) {
            Gson gson = new Gson();
            Persona person = gson.fromJson(request.getParameter("persona"), Persona.class);
            String rpt;
            try {
                PersonaDAO dao = new PersonaDAO();
                dao.registrar(person);
                rpt = "Registrada";
                this.printMessage("Persona " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("persona") != null) {
            Gson gson = new Gson();
            Persona person = gson.fromJson(request.getParameter("persona"), Persona.class);
            String rpt;
            try {
                PersonaDAO dao = new PersonaDAO();
                dao.actualizar(person);
                rpt = "Actualizada";
                this.printMessage("Persona " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void leer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PersonaDAO dao;
        Persona per;
        if (request.getParameter("id") != null) {
            per = new Persona();
            per.setIdPersona(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new PersonaDAO();
                per = dao.leer(per);
                String json = new Gson().toJson(per);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro de la persona", false, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Persona emp;
        if (request.getParameter("id") != null) {
            emp = new Persona();
            emp.setIdPersona(Integer.parseInt(request.getParameter("id")));
            try {
                PersonaDAO dao = new PersonaDAO();
                dao.eliminar(emp);
                this.printMessage("Persona eliminada correctamente", true, response);
            } catch (IOException e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del equipo", false, response);
        }
    }
}
