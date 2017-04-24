/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.CreadorVentanas;
import Util.ImagenLocalidad;
import Util.Singleton;
import Util.Tupla;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FXMLComprarReservarController implements Initializable, MiVentana {

    private Reserva r;
    @FXML
    private Button btnComprar;
    @FXML
    private Label labelLocalidades;
    @FXML
    private Label labelPrecio;

    private Proyeccion p;

    private ArrayList<Tupla> seleccionados;

    private ArrayList<Tupla> ocupadas;
    @FXML
    private BorderPane borderPaneComprarReservar;

    public void init(Reserva r) {
        this.r = r;
        for (Proyeccion pro : Singleton.getDataBase().getProyecciones()) {
            for (Reserva re : pro.getReservas()) {
                if (r == re) {
                    p = pro;
                    break;
                }
            }
        }
        localidadesProyeccion();
        seleccionados = new ArrayList<>();
        ocupadas = new ArrayList<>();
        labelPrecio();
        makeGridAsientos();
        refrescar();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onComprarEntradas(ActionEvent event) {
        if (seleccionados.size() == Integer.parseInt(labelLocalidades.getText())) {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("COMPRANDO");
            al.setHeaderText("Va a comprar " + labelLocalidades.getText() + " entradas con un coste de " + labelPrecio.getText());
            al.setContentText("¿Está usted seguro?");
            al.showAndWait();
            if (al.resultProperty().get() == ButtonType.OK) {
                for (Tupla t : seleccionados) {
                    p.getSala().updateLocalidad(t.getI(), t.getJ(), Sala.localidad.vendida);
                    p.getSala().setEntradasVendidas(p.getSala().getEntradasVendidas()+1);
                }
                r.setNumLocalidades(0);
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

    public void refrescar() {
        System.out.println("Hola");
    }

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

                new ImagenLocalidad(peliImage, i, j, seleccionados, ocupadas, labelLocalidades);
            }
        }
        borderPaneComprarReservar.setCenter(gp);

    }

    private void localidadesProyeccion() {
        labelLocalidades.setText(Integer.toString(r.getNumLocalidades()));
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
        return Integer.parseInt(labelLocalidades.getText()) * precio;
    }

    private void labelPrecio() {
        labelPrecio.setText(Integer.toString(precioLocalidades()) + " €");
    }
}
