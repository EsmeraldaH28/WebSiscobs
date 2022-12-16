package config;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    Connection con;
    String url = "jdbc:mysql://localhost:3306/bdsiscobs";
    String user = "root";
    String pass = "";

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
        }
        return con;
    }
    
    //public Connection conexion;
    private boolean transaccionIniciada;

    protected Connection getConexion() {
        return con;
    }
    //Conectar a la Base de datos

    public void conectar() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, pass);
    }

    //Desconectar a la Base de datos
    public void desconectar() throws SQLException, ClassNotFoundException {
        con.close();
    }

    protected void conectar(boolean wTransaccion) throws Exception {
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setServerName("localhost");
        ds.setPort(3306);
        ds.setDatabaseName("bdsiscobs");
        con = ds.getConnection("root", "");

        if (wTransaccion == true) {
            this.con.setAutoCommit(false);
            this.transaccionIniciada = true;
        } else {
            this.con.setAutoCommit(true);
            this.transaccionIniciada = false;
        }
    }

    protected void cerrar(boolean wEstado) throws Exception {
        if (this.con != null) {
            if (this.transaccionIniciada == true) {
                try {
                    if (wEstado == true) {
                        this.con.commit();
                    } else {
                        this.con.rollback();
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
            try {
                this.con.close();
            } catch (Exception e) {
            }
        }
        this.con = null;
    }

    protected void ejecutarOrden(String wSQL) throws Exception {
        Statement st;

        if (this.con != null) {
            st = this.con.createStatement();
            st.executeUpdate(wSQL);
        }
    }

    protected ResultSet ejecutarOrdenDatos(String wSQL) throws Exception {
        Statement st;
        ResultSet rs = null;

        if (this.con != null) {
            st = this.con.createStatement();
            rs = st.executeQuery(wSQL);
        }

        return rs;
    }

}
