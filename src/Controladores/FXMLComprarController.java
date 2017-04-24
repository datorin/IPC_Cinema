/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.CreadorVentanas;
import Util.ImagenLocalidad;
import Util.Tupla;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLComprarController implements Initializable, MiVentana {

    @FXML
    private Button btnComprar;

    private String s;

    private Proyeccion p;
    @FXML
    private BorderPane borderPaneComprar;
    @FXML
    private TextField fieldLocalidades;
    @FXML
    private Label labelCapacidad;
    @FXML
    private Button brnRestarLocalidades;
    @FXML
    private Button btnSumarLocalidades;

    private ArrayList<Tupla> seleccionados;

    private ArrayList<Tupla> ocupadas;
    @FXML
    private Label labelPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(Proyeccion p, String s) {
        this.p = p;
        this.s = s;
        fieldLocalidades.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelPrecio();
            }
        });
        seleccionados = new ArrayList<>();
        ocupadas = new ArrayList<>();
        makeGridAsientos();
        refrescar();
    }

    @Override
    public void refrescar() {
        capacidadProyeccion();
        numLocalidades(s);
        labelPrecio();
    }

    @Override
    public void cerrar() {
        Node a = (Node) btnComprar.getParent();
        ((Stage) a.getScene().getWindow()).close();
    }

    private void makeGridAsientos() {
        GridPane gp = new GridPane();
        gp.setGridLinesVisible(false);
        gp.setHgap(3);
        gp.setVgap(5);
        gp.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 12; j++) {
                ImageView peliImage = new ImageView();
                if (i == 0) {
                    Label l = new Label(Integer.toString(j + 1));
                    l.setPadding(new Insets(10, 10, 10, 15));
                    l.setStyle("-fx-font: 15 arial;");
                    gp.add(l, j, i);
                } else {
                    if (j == 0) {
                        Label l = new Label(Integer.toString(i + 1));
                        l.setPadding(new Insets(10, 10, 10, 15));
                        l.setStyle("-fx-font: 15 arial;");
                        gp.add(l, j, i);
                    } else {
                        // Creando Asientos
                        if (p.getSala().getLocalidades()[i][j] == Sala.localidad.vendida) {
                            ocupadas.add(new Tupla(i, j));
                            InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaOcupada.png");
                            Image image = new Image(is, 38, 45.5, true, true);
                            peliImage.setImage(image);
                        } else {
                            InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaVacia.png");
                            Image image = new Image(is, 38, 45.5, true, true);
                            peliImage.setImage(image);
                        }
                    }
                }

                gp.add(peliImage, j, i);

                new ImagenLocalidad(peliImage, i, j, seleccionados, ocupadas, fieldLocalidades);
            }
        }
        borderPaneComprar.setCenter(gp);
    }

    @FXML
    private void justLocalidades(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
            return;
        }
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
            return;
        }
        int i = 0;
        if (Integer.parseInt(fieldLocalidades.getText() + event.getCharacter()) > p.getSala().getCapacidad()
                - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
            event.consume();
            int valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
            String s = Integer.toString(valorActual);
            fieldLocalidades.setText(s);
        }
        if (Integer.parseInt(fieldLocalidades.getText() + event.getCharacter()) == 0) {
            event.consume();
            fieldLocalidades.setText(Integer.toString(1));
        }
    }

    private void capacidadProyeccion() {
        int reservas = 0;
        for (Reserva r : p.getReservas()) {
            reservas += r.getNumLocalidades();
        }
        labelCapacidad.setText(Integer.toString(reservas + p.getSala().getEntradasVendidas()) + " / " + p.getSala().getCapacidad());
    }

    private void numLocalidades(String s) {
        fieldLocalidades.setText(s);
    }

    @FXML
    private void onRestarLocalidades(ActionEvent event) {
        try {
            int valorActual = Integer.parseInt(fieldLocalidades.getText());
            valorActual--;
            int i = 0;
            if (valorActual > p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
            }
            if (valorActual <= 1) {
                valorActual = 1;
            }
            String s = Integer.toString(valorActual);
            fieldLocalidades.setText(s);
            labelPrecio();
        } catch (NumberFormatException e) {
            fieldLocalidades.setText(Integer.toString(1));
        }
    }

    @FXML
    private void onSumarLocalidades(ActionEvent event) {
        try {
            int valorActual = Integer.parseInt(fieldLocalidades.getText());
            valorActual++;
            int i = 0;
            if (valorActual > p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
            }
            if (valorActual <= 1) {
                valorActual = 1;
            }
            String s = Integer.toString(valorActual);
            fieldLocalidades.setText(s);
            labelPrecio();
        } catch (NumberFormatException e) {
            fieldLocalidades.setText(Integer.toString(1));
        }
    }

    private int precioLocalidades() {
        int precio = 0;
        switch (p.getDia().getDayOfWeek()) {
            case WEDNESDAY:
                precio = 5;
                break;
            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
                precio = 8;
                break;
            default:
                precio = 6;
                break;
        }
        return Integer.parseInt(fieldLocalidades.getText()) * precio;
    }

    private void labelPrecio() {
        labelPrecio.setText(Integer.toString(precioLocalidades()) + " €");
    }

    @FXML
    private void onComprarEntradas(ActionEvent event) {
        if (seleccionados.size() == Integer.parseInt(fieldLocalidades.getText())) {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("COMPRANDO");
            al.setHeaderText("Va a comprar " + fieldLocalidades.getText() + " entradas con un coste de " + labelPrecio.getText());
            al.setContentText("¿Está usted seguro?");
            al.showAndWait();
            if (al.resultProperty().get() == ButtonType.OK) {
                for (Tupla t : seleccionados) {
                    p.getSala().updateLocalidad(t.getI(), t.getJ(), Sala.localidad.vendida);
                    p.getSala().setEntradasVendidas(p.getSala().getEntradasVendidas()+1);
                }
                cerrar();
                CreadorVentanas.refrescarTodas();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText("NO SE HA PODIDO COMPLETAR LA RESERVA ");
            al.setContentText("FALTAN LOCALIDADES DE COMPRA POR RELLENAR.");
            al.showAndWait();
        }
    }
}
