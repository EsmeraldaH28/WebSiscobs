package modelo;

import config.Conexion;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EquipoDAO extends Conexion {

    public List<Equipo> listar() throws Exception {
        List<Equipo> equipos;
        Equipo equi;
        ResultSet rs;
        String sql = "SELECT EQ.IDEQUIPO, EQ.CODIGO_PATRIMONIO, EQ.ORDEN_COMPRA, "
                + "EQ.SERIE_NUMERO, EQ.NOMBRE_BIEN, EQ.FECHA_OC, M.MARCA, E.ESTADO "
                + "FROM equipo EQ INNER JOIN estado E ON EQ.IDESTADO = E.IDESTADO "
                + "INNER JOIN marca M ON EQ.IDMARCA = M.IDMARCA";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            equipos = new ArrayList<>();
            while (rs.next() == true) {
                equi = new Equipo();
                equi.setIdEquipo(rs.getInt("IDEQUIPO"));
                equi.setCodigoPatrimonio(rs.getString("CODIGO_PATRIMONIO"));
                equi.setOrdenCompra(rs.getString("ORDEN_COMPRA"));
                equi.setSerieNumero(rs.getString("SERIE_NUMERO"));
                equi.setNombreBien(rs.getString("NOMBRE_BIEN"));
                equi.setFechaOc(rs.getDate("FECHA_OC"));
                equi.setMarca(new Marca());
                equi.getMarca().setMarca((rs.getString("MARCA")));
                equi.setEstado(new Estado());
                equi.getEstado().setEstado((rs.getString("ESTADO")));
                equipos.add(equi);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return equipos;
    }

    public List<Estado> listarEstados() throws Exception {
        List<Estado> estados;
        Estado est;
        ResultSet rs;
        String sql = "SELECT IDESTADO, ESTADO FROM ESTADO";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            estados = new ArrayList<>();
            while (rs.next() == true) {
                est = new Estado();
                est.setIdEstado(rs.getInt("IDESTADO"));
                est.setEstado(rs.getString("ESTADO"));
                estados.add(est);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return estados;
    }

    public List<Marca> listarMarcas() throws Exception {
        List<Marca> marcas;
        Marca est;
        ResultSet rs;
        String sql = "SELECT IDMARCA, MARCA FROM MARCA";
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            marcas = new ArrayList<>();
            while (rs.next() == true) {
                est = new Marca();
                est.setIdMarca(rs.getInt("IDMARCA"));
                est.setMarca(rs.getString("MARCA"));
                marcas.add(est);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return marcas;
    }

    public void registrar(Equipo equi) throws Exception {
        String sql;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaOc = sdf.format(equi.getFechaOc());
        sql = "INSERT INTO equipo (CODIGO_PATRIMONIO, ORDEN_COMPRA, SERIE_NUMERO, "
                + "NOMBRE_BIEN, FECHA_OC, IDMARCA, IDESTADO)"
                + "VALUES('" + equi.getCodigoPatrimonio() + "', '"
                + equi.getOrdenCompra() + "', '"
                + equi.getSerieNumero() + "', '"
                + equi.getNombreBien() + "', '"
                + fechaOc + "', "
                + equi.getMarca().getIdMarca() + ", "
                + equi.getEstado().getIdEstado() + ")";
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public Equipo leer(Equipo equipo) throws Exception {
        Equipo equi = null;
        ResultSet rs = null;
        String sql = "SELECT EQ.IDEQUIPO, EQ.CODIGO_PATRIMONIO, EQ.ORDEN_COMPRA, "
                + "EQ.SERIE_NUMERO, EQ.NOMBRE_BIEN, EQ.FECHA_OC, EQ.IDESTADO, EQ.IDMARCA "
                + "FROM equipo EQ WHERE EQ.IDEQUIPO = " + equipo.getIdEquipo();
        try {
            this.conectar(false);
            rs = this.ejecutarOrdenDatos(sql);
            if (rs.next() == true) {
                equi = new Equipo();
                equi.setIdEquipo(equipo.getIdEquipo());
                equi.setCodigoPatrimonio(rs.getString("CODIGO_PATRIMONIO"));
                equi.setOrdenCompra(rs.getString("ORDEN_COMPRA"));
                equi.setSerieNumero(rs.getString("SERIE_NUMERO"));
                equi.setNombreBien(rs.getString("NOMBRE_BIEN"));
                equi.setFechaOc(rs.getDate("FECHA_OC"));
                equi.setMarca(new Marca());
                equi.getMarca().setIdMarca((rs.getInt("IDMARCA")));
                equi.setEstado(new Estado());
                equi.getEstado().setIdEstado((rs.getInt("IDESTADO")));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
        return equi;
    }

    public void actualizar(Equipo equipo) throws Exception {
        String sql;
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaOc = sdf.format(equipo.getFechaOc());
        sql = "UPDATE equipo SET CODIGO_PATRIMONIO = '" + equipo.getCodigoPatrimonio()
                + "', ORDEN_COMPRA = '" + equipo.getOrdenCompra()
                + "', SERIE_NUMERO = '" + equipo.getSerieNumero()
                + "', NOMBRE_BIEN = '" + equipo.getNombreBien()
                + "', FECHA_OC = '" + fechaOc
                + "', IDESTADO = " + equipo.getEstado().getIdEstado()
                + ", IDMARCA = " + equipo.getMarca().getIdMarca()
                + " WHERE IDEQUIPO = " + equipo.getIdEquipo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar(false);
        }
    }

    public void eliminar(Equipo equipo) {
        String sql = "DELETE FROM equipo WHERE IDEQUIPO = " + equipo.getIdEquipo();
        try {
            this.conectar(false);
            this.ejecutarOrden(sql);
            this.cerrar(false);
        } catch (Exception e) {
            System.out.println("Error al eliminar el equipo" + e.getMessage());
        }
    }
}
