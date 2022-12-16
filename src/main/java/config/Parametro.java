
package config;

public class Parametro {

    private String nombre;
    private Object valor;
    private boolean entrada;
    private int tipo;

    public Parametro() {
        this.entrada = true;
    }

    public Parametro(String nombre, Object valor) {
        this.nombre = nombre;
        this.valor = valor;
        this.entrada = true;
    }

    public Parametro(String nombre, boolean entrada, int tipo) {
        this.nombre = nombre;
        this.valor = null;
        this.entrada = entrada;
        this.tipo = tipo;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}
