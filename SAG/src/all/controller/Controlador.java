package all.controller;

import all.model.CenterLogic;
import all.model.Deuda;
import all.model.FileLogic;
import all.model.Gasto;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Controlador implements Initializable {

    // StackPane principal
    @FXML
    private StackPane rootStackPane;

    // AnchorPane principal
    @FXML
    private AnchorPane ancPaneMain;

    //TEXTFIELD para mostrar informacion
    @FXML //TXT para mostrar calculos de gastos y de deudas
    private JFXTextField txtPresuInicial, txtGastosFaltantes, txtDineroExistente, txtDineroLibre, txtTotalDeudas, txtDeudasFaltantes, txtDeudasPagas;
    @FXML   //TextFields para añadir nuevo Gasto
    private JFXTextField txtLlenarGasto, txtLlenarValorGasto, txtLlenarDescripcion;
    @FXML     //TextFields para agregar una deuda
    private JFXTextField txtLlenarValorDeuda, txtLlenarPersonaDeuda, txtLlenarDescripcionDeuda;
    @FXML  //Text Fiel to add a new Wallet & add a Ingreso
            JFXTextField txtAddWallet, txtAddIngDescripcion, txtAddIngValor;
    //-----------------------------------------------------------------------------------------------------------------

    //TABLES VIEWS & TABLES COLUMN
    @FXML //Tabla para Aproximacion de GASTOS
    private TableView<Gasto> tableViewGastos;
    @FXML   //Columnas para la tabla de gastos
    private TableColumn<Gasto, String> columGasto, columDescripcion, columFechaI, columFechaF, columnEsPlaneado, columEsRealizado, columBilletera;
    @FXML   //Columnas para la tabla de gastos
    private TableColumn<Gasto, Double> columValor;

    @FXML //Tabla para control de DEUDAS
    private TableView<Deuda> tableViewDeudas;
    @FXML   //Columnas para la tabla de deudas
            TableColumn<Deuda, String> personaColumn, descripcionColumn, isPaidColumn, isLendColumn, fInicialColumn, fPagoColumn;
    @FXML   //Columna para la tabla de deudas
            TableColumn<Deuda, Double> valorColumn;
    //-----------------------------------------------------------------------------------------------------------------

    //BUTTONS
    @FXML //btn to add several things || completar ejecucion de un gasto o deuda
            JFXButton btnAddGasto, btnAddIngreso, btnAddDeuda, btnAddBill, btnPagarDeuda, btnRealGast, btnRemoverDeuda;
    @FXML //btns to menuControl (FileChooser)
    private JFXButton btnFileNew, btnFileOpen, btnFileSave, btnFileSaveAs;
    @FXML //Show panels whit buttons-(the expresion "Me" is relationed with menu)
            JFXButton btnMeCalGastos, btnMeAddGastos, btnMeCalDeuda, btnMeAddDeuda, btnMeAddIngres, btnMeAddBill, btnMeInfoIngresos;
    //---------------------------------------------------------------------------------------------------------------

    //COMBOBOX To select pays and gastos ||
    @FXML
    JFXComboBox<String> cbxIsPlaned, cbxWallets, cbxEsDeuda, cbxAddIngBilletera, cbxFilterCalculations;
    //---------------------------------------------------------------------------------------------------------------

    //ANCHORPANES
    @FXML //panels of tables
            AnchorPane ancPaneTableGas, ancPaneTableDeudas;
    @FXML
    AnchorPane achPaneShowCal, achPaneAddIngreso, achPaneAddGasto, achPaneShowDeu, achPaneAddDeu, achPaneAddBill, achPaneInfoIngres, ancPaneTitleTables;
    //---------------------------------------------------------------------------------------------------------------

    //LABELS
    @FXML
    Label lbConDeudas, lbConGastos;
    //---------------------------------------------------------------------------------------------------------------

    //TOOL TIPS
    Tooltip toolTipBtn1;
    //---------------------------------------------------------------------------------------------------------------

    //CONTEXT MENU
    ContextMenu contextMenu;
    MenuItem menuItem1, menuItem2;
    //---------------------------------------------------------------------------------------------------------------

    private CenterLogic cl;
    public static Path ultimaRuta;
    private FileChooser fileChooser;
    private Stage stage;
    //lista Observable de los gastos, la cual se llena con el array de la clase centerLogic
    ObservableList<Gasto> lista;
    ObservableList<Deuda> listaDeudas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cl = new CenterLogic();
//        pruebaCosola();
        try {
            ultimaRuta = FileLogic.lastPathOpen();
            cl = FileLogic.readObjeto(ultimaRuta);
            if (cl != null) {
                actualizarInfo();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        addEventKeyPress();
        filterTextNumbers();
    }

    public void pruebaCosola() {
        ejectarCode();
        ejecutarDeudas();
        actualizarInfo();
    }

    public static void alCerrar() throws IOException {
        FileLogic.setLastPath(ultimaRuta);
    }

    public void ejecutarDeudas() {
        cl.anadirDeuda(new Deuda(50000, "Mama", "Examen medico", true));
        cl.anadirDeuda(new Deuda(50000, "Nelcy", "Examen medico", true));
        cl.anadirDeuda(new Deuda(20000, "Tia Rosa", "Sostenimiento intersemestral", true));
        cl.anadirDeuda(new Deuda(150000, "Mama", "Mitad arriendo julio", true));
        cl.anadirDeuda(new Deuda(30000, "mundoAccesorios", "reparacion telefono", false));

        System.out.println("****Mostrar Deudas******\n");
        for (int i = 0; i < cl.getListaDeudas().size(); i++) {
            System.out.println(cl.getListaDeudas().get(i).getValor() + " persona: " + cl.getListaDeudas().get(i).getNombrePersona() + " descrip: " + cl.getListaDeudas().get(i).getDescripcion() + " isPaid: " + cl.getListaDeudas().get(i).isPaid() + " isLend: " + cl.getListaDeudas().get(i).isLend());
        }
        System.out.println();
/////////////////////
        System.out.println("***Se paga una deuda****");
        cl.pagarDeuda(0);
        cl.pagarDeuda(3);
/////////
        System.out.println("****Mostrar Deudas******\n");
        for (int i = 0; i < cl.getListaDeudas().size(); i++) {
            System.out.println(cl.getListaDeudas().get(i).getValor() + " persona: " + cl.getListaDeudas().get(i).getNombrePersona() + " descrip: " + cl.getListaDeudas().get(i).getDescripcion() + " isPaid: " + cl.getListaDeudas().get(i).isPaid());
        }
        System.out.println();
        /////////
        System.out.println("deudas en total: " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(cl.allDeudas()));
        System.out.println("deudas pagas: " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(cl.deudasPagas()));
        System.out.println("deudas por pagar: " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(cl.deudasPorPagar()));
    }

    public void ejectarCode() {
        //Ingresos
        cl.anadirIngreso("ictex", 1700000, "I");
        cl.anadirIngreso("papas", 300000, "P");
        cl.anadirIngreso("limosnas", 1000000, "I");

        //Aproximación de gastos
        cl.anadirGasto(new Gasto("arriendo", 500000, "mes de agosto", "I"));
        cl.anadirGasto(new Gasto("puntales", 50000, "", "P"));
        cl.anadirGasto(new Gasto("fotocopias", 10000, "de cada una de las materias", "I"));
        cl.anadirGasto(new Gasto("bicicleta", 800000, "bicicleta para entrenar", "I", false));


        //Calculos
        double calculos[] = cl.doCalculationsAll("p");
        System.out.println("Dinero inicial " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[0]));
        System.out.println("Dinero existente físico " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[1]));
        System.out.println("dinero libre: " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[2]));
        System.out.println("Dinero para gastos " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[3]));
        System.out.println("Dinero emergido " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[4]));


        // Marcar gastos llevados a cabo
        System.out.println("\nSe han marcado como realizados algunos gastos");
        cl.registrarGasto(1);
        System.out.println(cl.getListaGastos().get(1).isPerformed());
        System.out.println(System.getProperty("line.separator"));

        //mostrar planeación de los gastos
        for (int i = 0; i < cl.getListaGastos().size(); i++) {
            if (cl.getListaGastos().get(i).isPerformed() == false) {
                System.out.println(cl.getListaGastos().get(i).getNombreG() + " $" + cl.getListaGastos().get(i).getValorGasto() + " Des: " + cl.getListaGastos().get(i).getDescripcion() + " Realizado: " + cl.getListaGastos().get(i).isPerformed() + " Plane: " + cl.getListaGastos().get(i).isPlanned() + " Wall: " + cl.getListaGastos().get(i).getWallet() + " DatS: " + cl.getListaGastos().get(i).getFechaInicio());
            } else {
                System.out.println(cl.getListaGastos().get(i).getNombreG() + " $" + cl.getListaGastos().get(i).getValorGasto() + " Des: " + cl.getListaGastos().get(i).getDescripcion() + " Realizado: " + cl.getListaGastos().get(i).isPerformed() + " Plane: " + cl.getListaGastos().get(i).isPlanned() + " Wall: " + cl.getListaGastos().get(i).getWallet() + " DatS: " + cl.getListaGastos().get(i).getFechaInicio() + " DateE " + cl.getListaGastos().get(i).getFechaFin());
            }
        }
        System.out.println("\n***NUEVOS CALCULOS***");
        calculos = cl.doCalculationsAll("p");
        System.out.println("Dinero inicial " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[0]));
        System.out.println("Dinero existente físico " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[1]));
        System.out.println("dinero libre: " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[2]));
        System.out.println("Dinero para gastos " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[3]));
        System.out.println("Dinero emergido " + NumberFormat.getNumberInstance(Locale.ITALIAN).format(calculos[4]));

        cl.anadirBilletera("Icetex");
        cl.anadirBilletera("Familia");
    }

    public void showCbxMains() {
        //llenar Billeteras
        cbxWallets.setItems(cl.getListaBilleteras());
        cbxFilterCalculations.setItems(cl.getListaBilleteras());
        cbxAddIngBilletera.setItems(cl.getListaBilleteras());
    }

    public void showTextFieldsMain() {
        //Limitar modificación de los textFields
        txtPresuInicial.setEditable(false);
        txtGastosFaltantes.setEditable(false);
        txtDineroExistente.setEditable(false);
        txtDineroLibre.setEditable(false);
        //*****
        txtTotalDeudas.setEditable(false);
        txtDeudasPagas.setEditable(false);
        txtDeudasFaltantes.setEditable(false);

        //Llenar los campos de texto con la información
        txtPresuInicial.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.dineroInicial("")));
        txtGastosFaltantes.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.dineroParaGastos()));
        txtDineroExistente.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.dineroExistente("")));
        txtDineroLibre.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.dineroLibre("")));
        //***
        txtTotalDeudas.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.allDeudas()));
        txtDeudasPagas.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.deudasPagas()));
        txtDeudasFaltantes.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(cl.deudasPorPagar()));

        //habilitar opciones de si es planeado o no un gasto (por si se desea crear uno)
        ObservableList<String> opciones = FXCollections.observableArrayList();
        opciones.add("Sí");
        opciones.add("No");
        cbxIsPlaned.setItems(opciones);
        cbxEsDeuda.setItems(opciones);
    }


    public void tableAproxGastos() {
        if (lista != null) {
            lista.remove(0, lista.size());
        }

        lista = FXCollections.observableArrayList(cl.getListaGastos());
        //Columns
        columGasto.setCellValueFactory(new PropertyValueFactory<>("nombreG"));
        columValor.setCellValueFactory(new PropertyValueFactory<>("valorGasto"));
        columDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columFechaI.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        columFechaF.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        columEsRealizado.setCellValueFactory(new PropertyValueFactory<>("performed"));
        columnEsPlaneado.setCellValueFactory(new PropertyValueFactory<>("planned"));
        columBilletera.setCellValueFactory(new PropertyValueFactory<>("wallet"));

        tableViewGastos.setItems(lista);
    }

    public void tableControlDeudas() {
        if (listaDeudas != null) {
            listaDeudas.remove(0, listaDeudas.size());
        }

        listaDeudas = FXCollections.observableArrayList(cl.getListaDeudas());
        //Columns
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        personaColumn.setCellValueFactory(new PropertyValueFactory<>("nombrePersona"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        isLendColumn.setCellValueFactory(new PropertyValueFactory<>("lend"));
        fInicialColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPreste"));
        fPagoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));

        tableViewDeudas.setItems(listaDeudas);
    }

    public void pressButtonActionPanels(ActionEvent event) {
        if (event.getSource() == btnMeCalGastos) {
            achPaneShowCal.setVisible(true);
            achPaneAddGasto.setVisible(false);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(false);
        } else if (event.getSource() == btnMeAddGastos) {
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(true);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(false);
        } else if (event.getSource() == btnMeCalDeuda) {
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(false);
            achPaneShowDeu.setVisible(true);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(false);
        } else if (event.getSource() == btnMeAddDeuda) {
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(false);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(true);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(false);
        } else if (event.getSource() == btnMeAddIngres) {
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(false);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(true);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(false);
        } else if (event.getSource() == btnMeAddBill) {
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(false);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(true);
            achPaneInfoIngres.setVisible(false);
        } else if (event.getSource() == btnMeInfoIngresos) {
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(false);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(true);
        }
    }

    public void pressButtonAction(ActionEvent event) {
        if (event.getSource() == btnAddGasto) {
            añadirGasto();
            ancPaneTableDeudas.setVisible(false);
            ancPaneTableGas.setVisible(true);
        } else if (event.getSource() == btnRealGast) {
            cl.registrarGasto(tableViewGastos.getSelectionModel().getFocusedIndex());
            actualizarInfo();
        } else if (event.getSource() == btnAddBill) {
            if (txtAddWallet.getText() != null) {
                System.out.println("Se ha obtenido la billetera " + txtAddWallet.getText() + " a Agregar");
                cl.anadirBilletera(txtAddWallet.getText());
                showCbxMains();
            }
        } else if (event.getSource() == btnAddDeuda) {
            boolean isDeuda;
            if (cbxEsDeuda.getSelectionModel().getSelectedItem().equalsIgnoreCase("si")) {
                isDeuda = true;
            } else {
                isDeuda = false;
            }
            cl.anadirDeuda(new Deuda(Double.parseDouble(txtLlenarValorDeuda.getText()), txtLlenarPersonaDeuda.getText(), txtLlenarDescripcionDeuda.getText(), isDeuda));
            actualizarInfo();
            ancPaneTableGas.setVisible(false);
            ancPaneTableDeudas.setVisible(true);
        } else if (event.getSource() == btnPagarDeuda) {
            cl.pagarDeuda(tableViewDeudas.getSelectionModel().getFocusedIndex());
            actualizarInfo();
        } else if (event.getSource() == btnAddIngreso) {
            cl.anadirIngreso(txtAddIngDescripcion.getText(), Double.parseDouble(txtAddIngValor.getText()), cbxAddIngBilletera.getSelectionModel().getSelectedItem());
            actualizarInfo();
        } else if (event.getSource() == btnRemoverDeuda) {
            cl.removerDeuda(tableViewDeudas.getSelectionModel().getFocusedIndex());
            actualizarInfo();
        }
    }

    public void pressButtonActionFile(ActionEvent event) throws IOException, ClassNotFoundException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo SAG", "*.sag"));
        fileChooser.setInitialFileName("misGastos.sag");
        File file;

        if (event.getSource() == btnFileOpen) {
            file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                cl = FileLogic.readObjeto(file.toPath());
                ultimaRuta = file.toPath();
                actualizarInfo();
                achPaneShowCal.setVisible(true);
                achPaneAddGasto.setVisible(false);
                achPaneShowDeu.setVisible(false);
                achPaneAddDeu.setVisible(false);
                achPaneAddIngreso.setVisible(false);
                achPaneAddBill.setVisible(false);
                achPaneInfoIngres.setVisible(false);
            }
        } else if (event.getSource() == btnFileSave) {
            if (ultimaRuta != null) {
                FileLogic.writeObjeto(ultimaRuta.toFile(), cl);
            }
        } else if (event.getSource() == btnFileSaveAs) {
            file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                FileLogic.writeObjeto(file, cl);
            }
            if (ultimaRuta != null || cl != null) {
                file = fileChooser.showSaveDialog(stage);
                if (file != null) {
                    FileLogic.writeObjeto(file, cl);
                }
            } else {
                mostrarAlerta("No se puede guardar", "error, no tienes información para guardar");
            }
        } else if (event.getSource() == btnFileNew) {
            cl = new CenterLogic();
            actualizarInfo();
            achPaneShowCal.setVisible(false);
            achPaneAddGasto.setVisible(true);
            achPaneShowDeu.setVisible(false);
            achPaneAddDeu.setVisible(false);
            achPaneAddIngreso.setVisible(false);
            achPaneAddBill.setVisible(false);
            achPaneInfoIngres.setVisible(false);
        }
    }

    @FXML
    public void pressComboBoxAction() {
        double w[] = cl.doCalculationsAll(cbxFilterCalculations.getSelectionModel().getSelectedItem());
        txtPresuInicial.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(w[0]));
        txtGastosFaltantes.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(w[1]));
        txtDineroExistente.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(w[2]));
        txtDineroLibre.setText(NumberFormat.getNumberInstance(Locale.ENGLISH).format(w[3]));
    }

    public void actualizarInfo() {
        showTextFieldsMain();
        showCbxMains();
        tableAproxGastos();
        tableControlDeudas();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void mostrarAlerta(String titulo, String mensaje) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setId("dialogAlerts");
        JFXButton dialogButton = new JFXButton("Cerrar");
        dialogButton.getStyleClass().add("dialog-button");
        JFXDialog dialog = new JFXDialog(rootStackPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialogButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            ancPaneMain.setEffect(null);
            dialog.close();
        });
        dialogLayout.setHeading(new Label(titulo));
        dialogLayout.setBody(new Label(mensaje));
        dialogLayout.setActions(dialogButton);
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            ancPaneMain.setEffect(null);
        });
        ancPaneMain.setEffect(blur);
    }

    /**
     * metodo para agregar desde la UI un nuevo gasto en la tabla para que sea in cluido en los calculos
     */
    public void añadirGasto() {
        cl.anadirGasto(new Gasto(txtLlenarGasto.getText(), Double.parseDouble(txtLlenarValorGasto.getText()), txtLlenarDescripcion.getText(), cbxWallets.getSelectionModel().getSelectedItem()));
        limpiarCamposAgregarGasto();
        actualizarInfo();
    }

    /**
     * metodo para registrar un gasto desde la UI. Consiste en marcar que determinado gasto ya esta realizado y que por
     * tanto se puede descontar de los calculos para gastos que aun no se han realizado
     */


    public void limpiarCamposAgregarGasto() {
        txtLlenarGasto.setText("");
        txtLlenarValorGasto.setText("");
        txtLlenarDescripcion.setText("");
    }

    @FXML
    public void showOtherTable() {
        if (ancPaneTableGas.isVisible()) {
            ancPaneTableGas.setVisible(false);
            ancPaneTableDeudas.setVisible(true);
            lbConDeudas.setVisible(true);
            lbConGastos.setVisible(false);
            btnRealGast.setVisible(false);
            btnRemoverDeuda.setVisible(false);
        } else {
            ancPaneTableGas.setVisible(true);
            ancPaneTableDeudas.setVisible(false);
            lbConGastos.setVisible(true);
            lbConDeudas.setVisible(false);
            btnPagarDeuda.setVisible(false);
            btnRemoverDeuda.setVisible(false);
        }
    }

    @FXML
    public void tableMouseEntered() {
        ancPaneTitleTables.setStyle("-fx-background-color: #232d2b");
    }

    @FXML
    public void tableMouseExited() {
        ancPaneTitleTables.setStyle("-fx-background-color:  rgba(54, 64, 62, 0.89)");
    }

    @FXML
    public void tableMouseClicked() {
        btnRealGast.setVisible(true);
        btnPagarDeuda.setVisible(false);
        btnRemoverDeuda.setVisible(false);
    }

    @FXML
    public void table2MouseClicked() {
        btnPagarDeuda.setVisible(true);
        btnRealGast.setVisible(false);
        btnRemoverDeuda.setVisible(true);
    }

    public void addEventKeyPress() {
        ancPaneMain.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                btnAddGasto.fire();
                evt.consume();
            }
        });
    }

    /**
     * Formatea el texto ingresado en un campo de texto para permitir solo caracteres alfanum&eacute;ricos en may&uacute;sculas o
     * min&uacute;sculas, el espacio y los signos <strong>()/-</strong>.
     *
     * @param textField Campo de texto a validar.
     */
    public void textFormatted(JFXTextField textField) {
        UnaryOperator<TextFormatter.Change> filter = txt -> {
            if (txt.isAdded()) {
                // La expresión "[a-zA-Z0-9\s()/-]" Permite caracteres alfanuméricos, el espacio y los signos ()/-
                if (!txt.getText().matches("[0-9]")) txt.setText("");
            }
            return txt;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }

    public void filterTextNumbers() {
        textFormatted(txtAddIngValor);
        textFormatted(txtLlenarValorGasto);
        textFormatted(txtLlenarValorDeuda);
    }
}
