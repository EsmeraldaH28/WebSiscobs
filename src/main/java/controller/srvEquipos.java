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
import modelo.Equipo;
import modelo.EquipoDAO;
import modelo.Estado;
import modelo.Marca;

/**
 *
 * @author Esmeralda Hernandez
 */
@WebServlet(name = "srvEquipos", urlPatterns = {"/srvEquipos"})
public class srvEquipos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "listar":
                    listar(response);
                    break;
                case "listarEstados":
                    listarEstados(response);
                    break;
                case "listarMarcas":
                    listarMarcas(response);
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
            EquipoDAO dao = new EquipoDAO();
            List<Equipo> emp = dao.listar();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void listarEstados(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            EquipoDAO dao = new EquipoDAO();
            List<Estado> emp = dao.listarEstados();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void listarMarcas(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            EquipoDAO dao = new EquipoDAO();
            List<Marca> emp = dao.listarMarcas();
            Gson gson = new Gson();
            String json = gson.toJson(emp);
            out.print(json);
        } catch (Exception e) {
            this.printError(e.getMessage(), response);
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("equipo") != null) {
            Gson gson = new Gson();
            Equipo equi = gson.fromJson(request.getParameter("equipo"), Equipo.class);
            String rpt;
            try {
                EquipoDAO dao = new EquipoDAO();
                dao.registrar(equi);
                rpt = "registrado";
                this.printMessage("Equipo " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("equipo") != null) {
            Gson gson = new Gson();
            Equipo equipo = gson.fromJson(request.getParameter("equipo"), Equipo.class);
            String rpt;
            try {
                EquipoDAO dao = new EquipoDAO();
                dao.actualizar(equipo);
                rpt = "actualizado";
                this.printMessage("Equipo " + rpt + " correctamente", true, response);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("Rellene el formulario", false, response);
        }
    }

    private void leer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EquipoDAO dao;
        Equipo equi;
        if (request.getParameter("id") != null) {
            equi = new Equipo();
            equi.setIdEquipo(Integer.parseInt(request.getParameter("id")));
            try {
                dao = new EquipoDAO();
                equi = dao.leer(equi);
                String json = new Gson().toJson(equi);
                response.getWriter().print(json);
            } catch (Exception e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene el parametro del equipo", false, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Equipo emp;
        if (request.getParameter("id") != null) {
            emp = new Equipo();
            emp.setIdEquipo(Integer.parseInt(request.getParameter("id")));
            try {
                EquipoDAO dao = new EquipoDAO();
                dao.eliminar(emp);
                this.printMessage("Equipo eliminado correctamente", true, response);
            } catch (IOException e) {
                this.printMessage(e.getMessage(), false, response);
            }
        } else {
            this.printMessage("No se tiene parametro del equipo", false, response);
        }
    }

}
