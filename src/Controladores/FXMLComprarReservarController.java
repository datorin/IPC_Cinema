/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.Reserva;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FXMLComprarReservarController implements Initializable {

    private Reserva r;
    @FXML
    private Button btnComprar;
    @FXML
    private Label labelLocalidades;
    @FXML
    private Label labelPrecio;

    public void init(Reserva r) {
        this.r = r;
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
        System.out.println("Gofre");
    }

    public void refrescar() {
        System.out.println("Hola");
    }

    public void cerrar() {
        Node a = (Node) btnComprar.getParent();
        ((Stage) a.getScene().getWindow()).close();
    }
}
