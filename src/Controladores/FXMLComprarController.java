/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.ImagenLocalidad;
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
    private TextField fielLocalidades;
    @FXML
    private Label labelCapacidad;
    @FXML
    private Button brnRestarLocalidades;
    @FXML
    private Button btnSumarLocalidades;
    
    private ArrayList<Tupla> seleccionados;
    
    private ArrayList<Tupla> ocupadas;

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
        seleccionados = new ArrayList<>();
        ocupadas = new ArrayList<>();
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
                // Creando Asientos
                ImageView peliImage = new ImageView();
                if (p.getSala().getLocalidades()[j][i] == Sala.localidad.vendida) {
                    ocupadas.add(new Tupla(i, j));
                    InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaOcupada.png");
                    Image image = new Image(is, 56.25, 68.25, true, true);
                    peliImage.setImage(image);
                } else {
                    InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaVacia.png");
                    Image image = new Image(is, 56.25, 68.25, true, true);
                    peliImage.setImage(image);
                }
                
                gp.add(peliImage, j, i);
                
                new ImagenLocalidad(peliImage, i, j, seleccionados, ocupadas);
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
        if (Integer.parseInt(fielLocalidades.getText() + event.getCharacter()) > p.getSala().getCapacidad()
                - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
            event.consume();
            int valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
            String s = Integer.toString(valorActual);
            fielLocalidades.setText(s);
        }
        if (Integer.parseInt(fielLocalidades.getText() + event.getCharacter()) == 0) {
            event.consume();
            fielLocalidades.setText(Integer.toString(1));
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
        fielLocalidades.setText(s);
    }

    @FXML
    private void onRestarLocalidades(ActionEvent event) {
        try {
            int valorActual = Integer.parseInt(fielLocalidades.getText());
            valorActual--;
            int i = 0;
            if (valorActual > p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
            }
            if (valorActual <= 1) {
                valorActual = 1;
            }
            String s = Integer.toString(valorActual);
            fielLocalidades.setText(s);
        } catch (NumberFormatException e) {
            fielLocalidades.setText(Integer.toString(1));
        }
    }

    @FXML
    private void onSumarLocalidades(ActionEvent event) {
        try {
            int valorActual = Integer.parseInt(fielLocalidades.getText());
            valorActual++;
            int i = 0;
            if (valorActual > p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
            }
            if (valorActual <= 1) {
                valorActual = 1;
            }
            String s = Integer.toString(valorActual);
            fielLocalidades.setText(s);
        } catch (NumberFormatException e) {
            fielLocalidades.setText(Integer.toString(1));
        }
    }
}
