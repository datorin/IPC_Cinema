/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.CreadorVentanas;
import static Util.CreadorVentanas.cerrarTodas;
import Util.Singleton;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Reserva;
import modelo.Sala;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
public class FXMLReservarController implements Initializable, MiVentana {

    private Pelicula pelicula;
    @FXML
    private Label nombrePeli;
    @FXML
    private ComboBox<String> comboDia;
    @FXML
    private ComboBox<String> comboProyecciones;
    @FXML
    private TextField fieldCliente = null;
    @FXML
    private TextField fieldTelefono = null;
    @FXML
    private TextField fieldLocalidades = null;
    @FXML
    private Button btnReservar2;
    @FXML
    private Label labelCapacidad;

    public void init(Pelicula p) {
        this.pelicula = p;
        nombrePeli.setText(p.getTitulo());
        diaPelicula();
        horaPelicula(comboDia.getSelectionModel().getSelectedItem());
        refrescar();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboDia.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                horaPelicula(newValue);
                System.out.println("He cambiao");
            }
        });
        comboProyecciones.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                refrescar();
            }
        });
    }

    @Override
    public void refrescar() {
        capacidadProyeccion(comboDia.getSelectionModel().getSelectedItem(), comboProyecciones.getSelectionModel().getSelectedItem());
    }

    @Override
    public void cerrar() {
        Node a = (Node) nombrePeli.getParent();
        ((Stage) a.getScene().getWindow()).close();
    }

    private void diaPelicula() {
        ArrayList<Proyeccion> proyecciones = new ArrayList<>();
        for (Proyeccion pro : Singleton.getDataBase().getProyecciones()) {
            if (pro.getPelicula().getTitulo().equals(pelicula.getTitulo())) {
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
        refrescar();
    }

    private void horaPelicula(String dia) {
        ArrayList<String> horas = new ArrayList<>();
        for (Proyeccion pr : Singleton.getDataBase().getProyecciones()) {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String st_dt = dt.format(pr.getDia());
            if (pr.getPelicula().getTitulo().equals(pelicula.getTitulo()) && st_dt.equals(dia)) {
                horas.add(pr.getHoraInicio());
            }
        }
        ObservableList<String> ob = FXCollections.observableArrayList(horas);
        comboProyecciones.setItems(ob);
        comboProyecciones.getSelectionModel().select(0);
        refrescar();
    }

    private void capacidadProyeccion(String dia, String hora) {
        if (dia == null || hora == null) {
            return;
        }
        Proyeccion proyeccion = null;
        for (Proyeccion p : Singleton.getDataBase().getProyecciones()) {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String st_dt = dt.format(p.getDia());
            if (p.getPelicula().getTitulo().equals(this.pelicula.getTitulo()) && st_dt.equals(dia) && p.getHoraInicio().equals(hora)) {
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

    @FXML
    private void onReservar(ActionEvent event) {
        if (fieldCliente.getText().equals("") || fieldTelefono.getText().equals("") || fieldLocalidades.getText().equals("")) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERROR");
            al.setHeaderText("NO SE HA PODIDO COMPLETAR LA RESERVA ");
            al.setContentText("FALTAN CAMPOS DE RESERVA POR RELLENAR.");
            al.showAndWait();
        } else {
            ArrayList<Proyeccion> proyecciones = new ArrayList<>();
            for (Proyeccion pro : Singleton.getDataBase().getProyecciones()) {
                if (pro.getPelicula().getTitulo().equals(pelicula.getTitulo())) {
                    proyecciones.add(pro);
                }
            }
            Proyeccion proyeccionAReservar = null;
            for (Proyeccion pro : proyecciones) {
                DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String st_dt = dt.format(pro.getDia());
                if (st_dt.equals(comboDia.getSelectionModel().getSelectedItem()) && pro.getHoraInicio().equals(comboProyecciones.getSelectionModel().getSelectedItem())) {
                    proyeccionAReservar = pro;
                }
            }
            proyeccionAReservar.addReserva(new Reserva(fieldCliente.getText(), fieldTelefono.getText(), Integer.parseInt(fieldLocalidades.getText())));
            cerrar();
            CreadorVentanas.refrescarTodas();
        }
    }

}
