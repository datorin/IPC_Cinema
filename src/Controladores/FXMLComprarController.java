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
import javafx.scene.control.Button;
import modelo.Proyeccion;

/**
 * FXML Controller class
 *
 * @author Dani
 */
public class FXMLComprarController implements Initializable, MiVentana {

    @FXML
    private Button btnComprar;
    
    private Proyeccion p;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void init(Proyeccion p) {
        this.p = p;
    }

    @Override
    public void refrescar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cerrar() {
        Node a = (Node) btnComprar.getParent();
        a.getScene().getWindow().hide();
    }
}
