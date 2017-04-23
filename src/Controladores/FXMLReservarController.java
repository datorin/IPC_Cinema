/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.Singleton;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Reserva;

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
    private TextField fieldCliente;
    @FXML
    private TextField fieldTelefono;
    @FXML
    private TextField fieldLocalidades;
    @FXML
    private Button btnReservar2;
    @FXML
    private Label labelCapacidad;

    public void init(Pelicula p) {
        this.p = p;
        nombrePeli.setText(p.getTitulo());
        DiaPelicula();
        HoraPelicula(comboDia.getSelectionModel().getSelectedItem());
        CapacidadProyeccion(comboDia.getSelectionModel().getSelectedItem(), comboProyecciones.getSelectionModel().getSelectedItem());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboDia.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                HoraPelicula(newValue);
                System.out.println("He cambiao");
            }
        });
    }

    @Override
    public void refrescar() {
        init(p);
    }

    @Override
    public void cerrar() {
        Node a = (Node) nombrePeli.getParent();
        a.getScene().getWindow().hide();
    }

    private void DiaPelicula() {
        ArrayList<Proyeccion> proyecciones = new ArrayList<>();
        for (Proyeccion pro : Singleton.getDataBase().getProyecciones()) {
            if (pro.getPelicula().getTitulo().equals(p.getTitulo())) {
                proyecciones.add(pro);
            }
        }
        ArrayList<String> dias = new ArrayList<>();
        for (Proyeccion pro : proyecciones) {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String st_dt = dt.format(pro.getDia());
            if (!dias.contains(st_dt)) {
                dias.add(st_dt);
            }
        }
        ObservableList<String> ob = FXCollections.observableArrayList(dias);
        comboDia.setItems(ob);
        comboDia.getSelectionModel().select(0);
    }

    private void HoraPelicula(String dia) {
        ArrayList<String> horas = new ArrayList<>();
        for (Proyeccion pr : Singleton.getDataBase().getProyecciones()) {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String st_dt = dt.format(pr.getDia());
            if (pr.getPelicula().getTitulo().equals(p.getTitulo()) && st_dt.equals(dia)) {
                horas.add(pr.getHoraInicio());
            }
        }
        ObservableList<String> ob = FXCollections.observableArrayList(horas);
        comboProyecciones.setItems(ob);
        comboProyecciones.getSelectionModel().select(0);
    }

    private void CapacidadProyeccion(String dia, String hora) {
        Proyeccion proyeccion = null;
        for (Proyeccion p : Singleton.getDataBase().getProyecciones()) {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String st_dt = dt.format(p.getDia());
            if (p.getPelicula().getTitulo().equals(this.p.getTitulo()) && st_dt.equals(dia) && p.getHoraInicio().equals(hora)) {
                proyeccion = p;
                break;
            }
        }
        int reservas = 0;
        for (Reserva r : proyeccion.getReservas()) {
            reservas += r.getNumLocalidades();
        }
        labelCapacidad.setText(Integer.toString(reservas + proyeccion.getSala().getEntradasVendidas()) + " / " + proyeccion.getSala().getCapacidad());
    }

    @FXML
    private void justLocalidades(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
            return;
        }
        if (Integer.parseInt(fieldLocalidades.getText() + event.getCharacter()) > 216) {
            event.consume();
        }
        if (Integer.parseInt(fieldLocalidades.getText() + event.getCharacter()) == 0) {
            event.consume();
        }
    }
    
    @FXML
    private void justInteger(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
        }
    }

    @FXML
    private void justCharacters(KeyEvent event) {
        if (!"qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNM ".contains(event.getCharacter())) {
            event.consume();
        }
    }
}
