/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.CreadorVentanas;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author hectorizquierdofernandez
 */
public class TestLibreria extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        CreadorVentanas.crearOrigen();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}