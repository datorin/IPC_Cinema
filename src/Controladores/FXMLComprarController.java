/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.Singleton;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private TextField fielLocalidades;
    @FXML
    private Label labelCapacidad;
    @FXML
    private Button brnRestarLocalidades;
    @FXML
    private Button btnSumarLocalidades;

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
        makeGridAsientos();
        refrescar();
    }

    @Override
    public void refrescar() {
        capacidadProyeccion();
        numLocalidades(s);
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
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 18; j++) {
                ImageView peliImage = new ImageView();
                InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaVacia.png");
                Image image = new Image(is, 56.25, 68.25, true, true);
                peliImage.setImage(image);

                gp.add(peliImage, j, i);
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
        if (Integer.parseInt(fielLocalidades.getText() + event.getCharacter()) > 216) {
            event.consume();
        }
        if (Integer.parseInt(fielLocalidades.getText() + event.getCharacter()) == 0) {
            event.consume();
        }
    }

    private void capacidadProyeccion() {
        int reservas = 0;
        for (Reserva r : p.getReservas()) {
            reservas += r.getNumLocalidades();
        }
         labelCapacidad.setText(Integer.toString(reservas + p.getSala().getEntradasVendidas()) + " / " + p.getSala().getCapacidad());
    }
    
    private void numLocalidades(String s){
        fielLocalidades.setText(s);
    }

    @FXML
    private void onRestarLocalidades(ActionEvent event) {
    }

    @FXML
    private void onSumarLocalidades(ActionEvent event) {
    }
}
