/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controladores.FXMLComprarController;
import Controladores.FXMLDocumentController;
import Controladores.FXMLReservarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Pelicula;

/**
 *
 * @author Daniel
 */
public class CreadorVentanas {
    
    private CreadorVentanas(){
        
    }
    
    public static FXMLDocumentController crearOrigen() {
        FXMLDocumentController fdc = null;
        try {
            FXMLLoader myLoader = new FXMLLoader(Object.class.getResource("/Controladores/FXMLDocument.fxml"));
            Parent root = (Parent) myLoader.load();
            fdc = myLoader.<FXMLDocumentController>getController();
            Scene scene = new Scene(root);
            Stage stageComprar = new Stage();
            stageComprar.setScene(scene);
            stageComprar.initModality(Modality.APPLICATION_MODAL);
            stageComprar.show();
        } catch(Exception e) {e.printStackTrace();}
        return fdc;
    }
    
    public static FXMLComprarController crearComprar() {
        FXMLComprarController fdc = null;
        try {
            FXMLLoader myLoader = new FXMLLoader(Object.class.getResource("/Controladores/FXMLComprar.fxml"));
            Parent root = (Parent) myLoader.load();
            fdc = myLoader.<FXMLComprarController>getController();
            Scene scene = new Scene(root);
            Stage stageComprar = new Stage();
            stageComprar.setScene(scene);
            stageComprar.initModality(Modality.APPLICATION_MODAL);
            stageComprar.show();
        } catch(Exception e) {e.printStackTrace();}
        return fdc;
    }
    
    public static FXMLReservarController crearReservar(Pelicula p) {
        FXMLReservarController fdc = null;
        try {
            FXMLLoader myLoader = new FXMLLoader(Object.class.getResource("/Controladores/FXMLReservar.fxml"));
            Parent root = (Parent) myLoader.load();
            fdc = myLoader.<FXMLReservarController>getController();
            fdc.init(p);
            Scene scene = new Scene(root);
            Stage stageReservar = new Stage();
            stageReservar.setScene(scene);
            stageReservar.initModality(Modality.APPLICATION_MODAL);
            stageReservar.show();
        } catch(Exception e) {e.printStackTrace();}
        return fdc;
    }
    
    
}
