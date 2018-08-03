package all.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CenterLogic implements Serializable {

    private ArrayList<Ingresos> listaIngresos; //el inicial, listaIngresos
    private ArrayList<Gasto> listaGastos; //arreglo de los gastos previstos
    private ArrayList<Deuda> listaDeudas; //arreglo de los gastos previstos
    private ArrayList<String> listaBilleteras; //arreglo de las billeteras
    private double dineroEmergido;

    public CenterLogic() { //inicialización de los atributos <-
        listaIngresos = new ArrayList<>();
        listaGastos = new ArrayList<>();
        listaDeudas = new ArrayList<>();
        listaBilleteras = new ArrayList<>();
    }

    //Listas
    public ArrayList<Ingresos> getListaIngresos() {
        return listaIngresos;
    }

    public ArrayList<Deuda> getListaDeudas() {
        return listaDeudas;
    }

    public ArrayList<Gasto> getListaGastos() {
        return listaGastos;
    }

    public ObservableList<String> getListaBilleteras() {
        ObservableList<String> listaV = FXCollections.observableArrayList();
        listaV.setAll(listaBilleteras);
        return listaV;
    }

    //Añadiendo en las listas
    public double[] doCalculationsAll(String wallet) {
        double arreglo[] = new double[5];
        arreglo[0] = dineroInicial(wallet);
        arreglo[1] = dineroParaGastos();
        arreglo[2] = dineroExistente(wallet);
        arreglo[3] = dineroLibre(wallet);
        arreglo[4] = getDineroEmergido(wallet);
        return arreglo;
    }

    public void anadirIngreso(String descripcion, double valor, String wallet) {
        listaIngresos.add(new Ingresos(descripcion, valor, wallet));
    }

    public void anadirGasto(Gasto g) {
        listaGastos.add(g);
    }

    public void anadirDeuda(Deuda deuda) {
        listaDeudas.add(deuda);
    }

    public void anadirBilletera(String nomBilletera) {
        listaBilleteras.add(nomBilletera);
    }

    public void registrarGasto(int posicion) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (!listaGastos.isEmpty()) {
            if (listaGastos.size() >= posicion) {
                if (listaGastos.get(posicion).isPlanned()) {
                    listaGastos.get(posicion).setPerform(true);
                    listaGastos.get(posicion).setFechaFin(dateFormat.format(date));
                    dineroEmergido += listaGastos.get(posicion).getValorGasto();
                } else {
                    listaGastos.get(posicion).setPerform(true);
                    dineroEmergido += listaGastos.get(posicion).getValorGasto();
                }
            }
        }
    }

    public void removerDeuda(int posicion) {
        listaDeudas.remove(posicion);
    }

    //Calculos de dinero
    public double dineroInicial(String wallet) { //metodo para mostrar el dinero que ha ingresado
        double dineroT;
        dineroT = 0;
        for (int i = 0; i < listaIngresos.size(); i++) {
            if (wallet.equalsIgnoreCase(listaIngresos.get(i).getWallet()) || wallet == null) {
                dineroT += listaIngresos.get(i).getValor();
            }
        }
        return dineroT;
    }

    public double dineroExistente(String wallet) { //dinero que se debe tener de alguan forma, efectivo o targeta pero que existe
        double sumatoria;
        sumatoria = dineroInicial(wallet) - dineroEmergido;
        return sumatoria;
    }

    public double dineroLibre(String wallet) { //dinero en total que se tiene sean o no desinados para los gastos <-
        double dineroIni = dineroInicial(wallet);

        for (int i = 0; i < listaGastos.size(); i++) {
            if (listaGastos.get(i).isPlanned()) {
                dineroIni -= listaGastos.get(i).getValorGasto();
            }
        }
        return dineroIni;
    }

    public double dineroParaGastos() { //dinero necesario para cubrir los gastos <-
        double dineroGastos = 0;
        if (!listaGastos.isEmpty()) {
            for (int i = 0; i < listaGastos.size(); i++) {
                if (!listaGastos.get(i).isPerformed()) {
                    dineroGastos += listaGastos.get(i).getValorGasto();
                }
            }
        }
        return dineroGastos;
    }

    public double getDineroEmergido(String wallet) {
        double sumatoria = 0;
        for (int i = 0; i < listaGastos.size(); i++) {
            if (listaGastos.get(i).isPerformed()) {
                if (listaGastos.get(i).getWallet().equalsIgnoreCase(wallet) || wallet == null) {
                    sumatoria += listaGastos.get(i).getValorGasto();
                }
            }
        }
        return sumatoria;
    }

    //Deudas
    public void pagarDeuda(int posicion) {
        listaDeudas.get(posicion).setPaid(true);
    }

    public double allDeudas() {
        double sumatoria = 0;
        for (int i = 0; i < listaDeudas.size(); i++) {
            sumatoria += listaDeudas.get(i).getValor();
        }
        return sumatoria;
    }

    public double deudasPagas() {
        double sumatoria = 0;
        for (int i = 0; i < listaDeudas.size(); i++) {
            if (listaDeudas.get(i).isPaid())
                sumatoria += listaDeudas.get(i).getValor();
        }
        return sumatoria;
    }

    public double deudasPorPagar() {
        double sumatoria = 0;
        for (int i = 0; i < listaDeudas.size(); i++) {
            if (!listaDeudas.get(i).isPaid())
                sumatoria += listaDeudas.get(i).getValor();
        }
        return sumatoria;
    }
}
