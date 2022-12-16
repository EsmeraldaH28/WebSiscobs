package modelo;

import config.Conexion;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends Conexion {

    public Usuario identificar(Usuario usuario) throws Exception {
        Usuario usu = null;
        ResultSet rs = null;
        String sql = "SELECT R.ROL, U.IDUSUARIO, P.IDPERSONA, P.NOMBRES, P.APELLIDOS FROM persona P "
                + "INNER JOIN usuario U ON U.IDPERSONA = P.IDPERSONA INNER JOIN rol R ON R.IDROL = P.IDROL "
                + "WHERE u.usuario = '" + usuario.getUsuario() + "' AND "
                + "u.clave = '" + usuario.getClave() + "' AND U.ESTADO = 1";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next()) {
                usu = new Usuario();
                usu.setIdUsuario(rs.getInt("IDUSUARIO"));
                usu.setPersona(new Persona());
                usu.getPersona().setIdPersona(rs.getInt("IDPERSONA"));
                usu.getPersona().setNombres(rs.getString("NOMBRES"));
                usu.getPersona().setApellidos(rs.getString("APELLIDOS"));
                usu.getPersona().setRol(new Rol());
                usu.getPersona().getRol().setRol(rs.getString("ROL"));
                usu.setEstado(true);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usu;
    }

    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios;
        Usuario usu;
        ResultSet rs;
        String sql = "SELECT U.IDUSUARIO, U.USUARIO, U.CLAVE, U.ESTADO, "
                + "P.NOMBRES, P.APELLIDOS FROM usuario U INNER JOIN persona P "
                + "ON U.IDPERSONA = P.IDPERSONA";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            usuarios = new ArrayList<>();
            while (rs.next() == true) {
                usu = new Usuario();
                usu.setIdUsuario(rs.getInt("IDUSUARIO"));
                usu.setUsuario(rs.getString("USUARIO"));
                usu.setClave(rs.getString("CLAVE"));
                usu.setEstado(rs.getBoolean("ESTADO"));
                usu.setNombresCompletos(rs.getString("NOMBRES") + " " + (rs.getString("APELLIDOS")));
                usu.setPersona(new Persona());
                usu.getPersona().setNombres((rs.getString("NOMBRES")));
                usu.getPersona().setApellidos((rs.getString("APELLIDOS")));
                usuarios.add(usu);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usuarios;
    }

    public List<Persona> listarPersonasSinLogin() throws Exception {
        List<Persona> empleado;
        Persona emp;
        ResultSet rs;
        String sql = "SELECT p.IDPERSONA, p.NOMBRES, p.APELLIDOS "
                + "FROM persona P WHERE NOT EXISTS(SELECT * FROM USUARIO u WHERE u.IDPERSONA = p.IDPERSONA)";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            empleado = new ArrayList<>();
            while (rs.next() == true) {
                emp = new Persona();
                emp.setIdPersona(rs.getInt("IDPERSONA"));
                emp.setNombres(rs.getString("NOMBRES"));
                emp.setApellidos(rs.getString("APELLIDOS"));
                empleado.add(emp);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return empleado;
    }

    public void registrarUsuarios(Usuario usu) throws Exception {
        String sql;
        sql = "INSERT INTO usuario (USUARIO, CLAVE, IDPERSONA, ESTADO)"
                + "VALUES('" + usu.getUsuario() + "', '"
                + usu.getClave() + "', "
                + usu.getPersona().getIdPersona() + ", 1)";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Usuario leerUsuario(Usuario usu) throws Exception {
        Usuario usus = null;
        ResultSet rs = null;
        String sql = "SELECT U.IDUSUARIO, U.IDPERSONA, U.USUARIO, U.CLAVE, U.ESTADO, "
                + "P.NOMBRES, P.APELLIDOS FROM usuario U INNER JOIN persona P "
                + "ON U.IDPERSONA = P.IDPERSONA WHERE U.IDUSUARIO = " + usu.getIdUsuario();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usus = new Usuario();
                usus.setIdUsuario(usu.getIdUsuario());
                usus.setUsuario(rs.getString("USUARIO"));
                usus.setClave(rs.getString("CLAVE"));
                usus.setEstado(rs.getBoolean("ESTADO"));
                usus.setPersona(new Persona());
                usus.getPersona().setIdPersona(rs.getInt("IDPERSONA"));
                usus.getPersona().setNombres(rs.getString("NOMBRES"));
                usus.getPersona().setApellidos(rs.getString("APELLIDOS"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usus;
    }

    public void actualizarUsuario(Usuario usuario) throws Exception {
        String sql;
        sql = "UPDATE usuario SET USUARIO = '" + usuario.getUsuario()
                + "', CLAVE = '" + usuario.getClave()
                + "', ESTADO = " + (usuario.isEstado() == true ? "1" : "0")
                + ", IDPERSONA = " + usuario.getPersona().getIdPersona()
                + " WHERE IDUSUARIO = " + usuario.getIdUsuario();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void desactivarUsuarios(Usuario usu) throws Exception {
        String sql = "UPDATE usuario U SET U.ESTADO = 0 WHERE IDUSUARIO = " + usu.getIdUsuario();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    /*--------------------------RECUPERAR CONTRASEÑA-------------------------*/
    public Usuario solicitarCambioContrasenia(Usuario usu) throws Exception {
        Usuario usus = null;
        ResultSet rs = null;
        String sql = "SELECT U.USUARIO, P.NOMBRES, P.APELLIDOS FROM usuario U "
                + "INNER JOIN persona P ON P.IDPERSONA = U.IDPERSONA "
                + "WHERE U.USUARIO = '" + usu.getUsuario() + "'"; //El dato que envia es string por eso '""'
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                usus = new Usuario();
                usus.setUsuario(rs.getString("USUARIO"));
                usus.setNombresCompletos(rs.getString("NOMBRES") + " " + (rs.getString("APELLIDOS")));
            }
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            throw e;
        } finally {
            this.cerrar(false);
        }
        return usus;
    }

    public void cambiarContrasenia(Usuario usuario) throws Exception {
        String sql;
        sql = "UPDATE usuario SET clave = '" + usuario.getClave()
                + "' WHERE USUARIO = '" + usuario.getUsuario() + "'";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }
}
