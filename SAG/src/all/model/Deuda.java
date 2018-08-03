package all.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deuda implements Serializable {
    private double valor;
    private String nombrePersona;
    private String descripcion;
    private boolean paid;
    private boolean lend;
    private String fechaPreste;
    private String fechaPago;

    public Deuda(double valor, String nombrePersona, String descripcion, boolean isLend) {
        this.valor = valor;
        this.nombrePersona = nombrePersona;
        this.descripcion = descripcion;
        this.paid = false;
        this.lend = isLend;
        this.fechaPreste = getDate();
        this.fechaPago = null;
    }

    public String getDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public double getValor() {
        return valor;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaPreste() {
        return fechaPreste;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isLend() {
        return this.lend;
    }

    public void setPaid(boolean isPaid) {
        this.paid = isPaid;
    }


}
