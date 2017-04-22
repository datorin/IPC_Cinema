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
import javafx.scene.Node;
import javafx.scene.control.Label;
import modelo.Pelicula;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FXMLReservarController implements Initializable, MiVentana {
    
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

    @Override
    public void refrescar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cerrar() {
        Node a = (Node) nombrePeli.getParent();
        a.getScene().getWindow().hide();
    }
    
}
