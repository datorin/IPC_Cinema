/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.CreadorVentanas;
import Util.Singleton;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelo.LocalDateAdapter;
import modelo.Pelicula;

/**
 *
 * @author hectorizquierdofernandez
 */
public class FXMLDocumentController implements Initializable {

    private Stage stageO;
    @FXML
    private ScrollPane contenedorPeliculas;

    public void stageOrigen(Stage stage) throws Exception {
        stageO = stage;
        LocalDateAdapter lda = new LocalDateAdapter();
        for (int i = 1; i < 10; i++) {
            String a = "2017-04-0";
            a += 1;
            fechaCombo.getItems().add(lda.unmarshal(a));
            System.out.println(a);
        }

    }

    @FXML
    private Button sumCantidad;

    @FXML
    private Button resCantidad;

    @FXML
    private TextField fieldCantidad;

    @FXML
    private Button btnComprar;

    @FXML
    private ComboBox fechaCombo;

    @FXML
    private ComboBox peliculasCombo;

    @FXML
    private void sumarCantidad(ActionEvent event) {
        try {
            int valorActual = parseInt(fieldCantidad.getText());
            valorActual++;
            if (valorActual >= 216) {
                valorActual = 216;
            }
            if (valorActual <= 1) {
                valorActual = 1;
            }
            String s = Integer.toString(valorActual);
            fieldCantidad.setText(s);
        } catch (NumberFormatException e) {
            fieldCantidad.setText(Integer.toString(1));
        }
    }

    @FXML
    private void restarCantidad(ActionEvent event) {
        try {
            int valorActual = parseInt(fieldCantidad.getText());
            valorActual--;
            if (valorActual >= 216) {
                valorActual = 216;
            }
            if (valorActual <= 1) {
                valorActual = 1;
            }
            String s = Integer.toString(valorActual);
            fieldCantidad.setText(s);
        } catch (NumberFormatException e) {
            fieldCantidad.setText(Integer.toString(1));
        }
    }

    @FXML
    private void comprarVentana(ActionEvent event) throws IOException {
        CreadorVentanas.crearComprar();
    }

    /*@FXML
    private void womboCombo(ActionEvent event) {
        dia = (String) fechaCombo.getValue();
        peliculasCombo.getItems().clear();
        lda = new LocalDateAdapter().unmarshal(s);
        List<Pelicula> lpel = (new AccesoaBD()).getPeliculas(lda);
        for(int i = 0; i < lp.size(); i++){
            peliculas.getItems().add
        }
    }*/
 /*@FXML
    private void comprarVentana(ActionEvent event) throws IOException {
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("FXMLComprar.fxml"));
        Parent root = (Parent) myLoader.load();
        myLoader.<FXMLComprarController>getController().stageOrigen(stageO);
        
        Scene scene = new Scene(root);
        stageO.setTitle("Dogo busca doga");
        stageO.setScene(scene);
    }*/
 /*@FXML
    private Button btn_reservation;
    @FXML
    private Button btn_sales;
    @FXML
    private Button btn_statistics;

    @FXML
    private void onGoToReservation(ActionEvent event) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Reservas.fxml"));
            Parent root = (Parent) myLoader.load();
            myLoader.<ReservasController>getController().initStage(primaryStage);
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Movie Tickets");
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToSales(ActionEvent event) {
        try {
            
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("SellOrConfirm.fxml"));
            Parent root = (Parent) myLoader.load();
            myLoader.<SellOrConfirmController>getController().initStage(primaryStage);
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Movie Tickets");
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGoToStats(ActionEvent event) {
        try {
            
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Stats.fxml"));
            Parent root = (Parent) myLoader.load();
            myLoader.<StatsController>getController().initStage(primaryStage);
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Movie Tickets");
            primaryStage.setScene(scene);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onExit(ActionEvent event) {
        primaryStage.hide();
    }*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GridPane peliGrid = new GridPane();
        int contador = 0;
        for(Pelicula p : Singleton.getDataBase().getPeliculas()) {
            ImageView peliImage = new ImageView();
            try{
                InputStream is = this.getClass().getResourceAsStream(p.getPathImage());
                Image image = new Image(is,330,220,true,true);
                peliImage.setImage(image);
            } catch (Exception e){e.printStackTrace();}
            peliGrid.add(peliImage, contador, 0);
            Button btnReservar = new Button("RESERVAR");
            btnReservar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    CreadorVentanas.crearReservar(p);
                }
            });
            peliGrid.add(btnReservar, contador, 1);
            contador++;
        }
        contenedorPeliculas.setContent(peliGrid);
    }

    @FXML
    private void justInteger(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
        }
    }

}
