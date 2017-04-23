/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    @FXML
    private BorderPane borderPaneComprar;
    @FXML
    private TextField fielLocalidades;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void init(Proyeccion p) {
        this.p = p;
        makeGridAsientos();
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

    private void makeGridAsientos() {
        GridPane gp = new GridPane();
        gp.setGridLinesVisible(false);
        gp.setHgap(1);
        gp.setVgap(1);
        gp.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 18; j++) {
                ImageView peliImage = new ImageView();
                InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaVacia.png");
                Image image = new Image(is, 56.25, 68.25, true, true);
                peliImage.setImage(image);

                gp.add(peliImage, j, i);
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
        if (Integer.parseInt(fielLocalidades.getText() + event.getCharacter()) > 216) {
            event.consume();
        }
        if (Integer.parseInt(fielLocalidades.getText() + event.getCharacter()) == 0) {
            event.consume();
        }
    }
}
