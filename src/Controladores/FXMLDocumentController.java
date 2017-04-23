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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modelo.Pelicula;

/**
 *
 * @author Daniel
 */
public class FXMLDocumentController implements Initializable, MiVentana {

    @FXML
    private ScrollPane contenedorPeliculas;
    @FXML
    private Tab tabActualidad;

    /*private void sumarCantidad(ActionEvent event) {
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
    }*/

    private void comprarVentana(ActionEvent event) throws IOException {
        CreadorVentanas.crearComprar();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPeliculas();
        initActualidad();
    }
    
    private void initPeliculas() {
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
    
    private void initActualidad(){
        TabPane root = new TabPane();
        tabActualidad.setContent(root);
        for(String s : Singleton.getDataBase().getDias()) {
            Tab tb = new Tab(s);
            tb.setClosable(false);
            root.getTabs().add(tb);
            VBox vb = new VBox();
            tb.setContent(vb);
            GridPane gd1 = new GridPane();
            gd1.setGridLinesVisible(true);
            ColumnConstraints c0 = new ColumnConstraints();
            c0.setPercentWidth(200/7);
            c0.setHalignment(HPos.CENTER);
            ColumnConstraints c1 = new ColumnConstraints();
            c1.setPercentWidth(100/7);
            c1.setHalignment(HPos.CENTER);
            ColumnConstraints c2 = new ColumnConstraints();
            c2.setPercentWidth(200/7);
            c2.setHalignment(HPos.CENTER);
            ColumnConstraints c3 = new ColumnConstraints();
            c3.setPercentWidth(100/7);
            c3.setHalignment(HPos.CENTER);
            ColumnConstraints c4 = new ColumnConstraints();
            c4.setPercentWidth(100/7);
            c4.setHalignment(HPos.CENTER);
            gd1.getColumnConstraints().addAll(c0,c1,c2,c3,c4);
            Label l = new Label("TÍTULO DE LAS PELÍCULAS");
            l.setPadding(new Insets(15,15,15,15));
            l.setStyle("-fx-font: 15 arial;");
            gd1.add(l, 0, 0);
            l = new Label("HORAS");
            l.setPadding(new Insets(15,15,15,15));
            l.setStyle("-fx-font: 15 arial;");
            gd1.add(l, 1, 0);
            l = new Label("CANTIDAD");
            l.setPadding(new Insets(15,15,15,15));
            l.setStyle("-fx-font: 15 arial;");
            gd1.add(l, 2, 0);
            l = new Label("CAPACIDAD");
            l.setPadding(new Insets(15,15,15,15));
            l.setStyle("-fx-font: 15 arial;");
            gd1.add(l, 3, 0);
            l = new Label("COMPRAR");
            l.setPadding(new Insets(15,15,15,15));
            l.setStyle("-fx-font: 15 arial;");
            gd1.add(l, 4, 0);
            vb.getChildren().add(gd1);
        }
    }
            
    private void justInteger(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
        }
    }

    @Override
    public void refrescar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cerrar() {
        /*Node a = (Node) btnComprar.getParent();
        a.getScene().getWindow().hide();*/
    }

}
