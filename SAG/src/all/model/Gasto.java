package all.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gasto implements Serializable {

    private String nombreG;
    private Double valorGasto;
    private String descripcion;
    private boolean performed;
    private String fechaInicio;
    private String fechaFin;
    private boolean planned;
    private String wallet;

    /**
     * Construtor para gasto PLANEADO
     * @param nombreG
     * @param valorGasto
     * @param descripcion
     */
    public Gasto(String nombreG, double valorGasto, String descripcion, String wallet) {
        this.nombreG = nombreG;
        this.valorGasto = valorGasto;
        this.descripcion = descripcion;
        this.fechaInicio = getDate();
        this.performed = false;
        this.fechaFin = null;
        this.planned = true;
        this.wallet = wallet;
    }

    /**
     * Constructor para gastos NO PLANEADOS, PERO REALIZADOS
     * @param nombreG
     * @param valorGasto
     * @param descripcion
     * @param isPlanned
     */
    public Gasto(String nombreG, double valorGasto, String descripcion,String wallet, boolean isPlanned) {
        this.nombreG = nombreG;
        this.valorGasto = valorGasto;
        this.descripcion = descripcion;
        this.performed = true;
        this.fechaFin = getDate();
        this.fechaInicio = null;
        this.planned = isPlanned;
        this.wallet = wallet;
    }

    public String getNombreG() {
        return nombreG;
    }

    public Double getValorGasto() {
        return valorGasto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isPerformed() {
        return performed;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public boolean isPlanned() {
        return planned;
    }

    public String getWallet() {
        return wallet;
    }

    public String getDate(){
        Date date= new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public void setFechaFin (String fecha){
        fechaFin = fecha;
    }

    public void setPerform(boolean perform){
        this.performed = perform;
    }
}
