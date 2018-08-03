package all.model;

import java.io.Serializable;

public class Ingresos implements Serializable {

    private String descripcion;
    private double valor;
    private String wallet;

    public Ingresos(String descripcion, double valor, String wallet) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.wallet = wallet;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValor() {
        return valor;
    }

    public String getWallet() {
        return wallet;
    }

}
