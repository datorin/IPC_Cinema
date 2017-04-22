/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modelo.Pelicula;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FXMLReservarController implements Initializable {
    
    private Pelicula p;
    @FXML
    private Label nombrePeli;
    
    public void init(Pelicula p) {
        this.p = p;
        nombrePeli.setText(p.getTitulo());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
