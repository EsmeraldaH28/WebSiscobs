package modelo;
import config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO extends Conexion {

    public List<Persona> listar() throws Exception {
        List<Persona> personas;
        Persona person;
        ResultSet rs;
        String sql = "SELECT P.IDPERSONA, P.NOMBRES, P.APELLIDOS, R.ROL "
                + "FROM persona P INNER JOIN rol R ON P.IDROL = R.IDROL";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            personas = new ArrayList<>();
            while (rs.next() == true) {
                person = new Persona();
                person.setIdPersona(rs.getInt("IDPERSONA"));
                person.setNombres(rs.getString("NOMBRES"));
                person.setApellidos(rs.getString("APELLIDOS"));
                person.setRol(new Rol());
                person.getRol().setRol((rs.getString("ROL")));
                personas.add(person);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return personas;
    }
    
    public List<Rol> listarRoles() throws Exception {
        List<Rol> roles;
        Rol rol;
        ResultSet rs;
        String sql = "SELECT R.IDROL, R.ROL FROM ROL R";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            roles = new ArrayList<>();
            while (rs.next() == true) {
                rol = new Rol();
                rol.setIdRol(rs.getInt("IDROL"));
                rol.setRol(rs.getString("ROL"));
                roles.add(rol);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return roles;
    }

    public void registrar(Persona per) throws Exception {
        String sql;
        sql = "INSERT INTO persona (NOMBRES, APELLIDOS, IDROL)"
                + "VALUES('" + per.getNombres()+ "', '"
                + per.getApellidos()+ "', "
                + per.getRol().getIdRol()+ ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Persona leer(Persona persona) throws Exception {
        Persona per = null;
        ResultSet rs = null;
        String sql = "SELECT P.IDPERSONA, P.NOMBRES, P.APELLIDOS, P.IDROL FROM persona P WHERE P.IDPERSONA = " + persona.getIdPersona();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                per = new Persona();
                per.setIdPersona(persona.getIdPersona());
                per.setNombres(rs.getString("NOMBRES"));
                per.setApellidos(rs.getString("APELLIDOS"));
                per.setRol(new Rol());
                per.getRol().setIdRol((rs.getInt("IDROL")));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return per;
    }

    public void actualizar(Persona persona) throws Exception {
        String sql;
        sql = "UPDATE persona SET NOMBRES = '" + persona.getNombres()
                + "', APELLIDOS = '" + persona.getApellidos()
                + "', IDROL = " + persona.getRol().getIdRol()
                + " WHERE IDPERSONA = " + persona.getIdPersona();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }
    
    public void eliminar(Persona persona) {
        String sql = "DELETE FROM persona WHERE IDPERSONA = " + persona.getIdPersona();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("Error al eliminar la persona" + e.getMessage());
        }
    }
}
