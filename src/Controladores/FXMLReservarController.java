/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.Singleton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML
    private ComboBox<String> comboDia;
    @FXML
    private ComboBox<String> comboProyecciones;
    @FXML
    private TextField fieldAdvertencia;
    @FXML
    private TextField fieldCliente;
    @FXML
    private TextField fieldTelefono;
    @FXML
    private TextField fieldLocalidades;
    @FXML
    private TextField fieldCapacidadReservas;
    @FXML
    private Button btnReservar2;
    
    public void init(Pelicula p) {
        this.p = p;
        nombrePeli.setText(p.getTitulo());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DiaPelicula();
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
    
    private void DiaPelicula(){
        ArrayList<String> a = Singleton.getDataBase().getDias();
        ObservableList<String> ob = FXCollections.observableArrayList(a);
        comboDia.setItems(ob);
        comboDia.getSelectionModel().select(0);
    }
    
    private void HoraPelicula(){
        ArrayList<String> a = Singleton.getDataBase().getDias();
        ObservableList<String> ob = FXCollections.observableArrayList(a);
        comboProyecciones.setItems(ob);
    }
    
}
