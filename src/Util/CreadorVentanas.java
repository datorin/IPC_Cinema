/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controladores.FXMLComprarController;
import Controladores.FXMLDocumentController;
import Controladores.FXMLReservarController;
import Controladores.MiVentana;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modelo.Pelicula;
import modelo.Proyeccion;

/**
 *
 * @author Daniel
 */
public class CreadorVentanas {

    private static final ListaVentanas listVent = new ListaVentanas();

    private CreadorVentanas() {

    }

    public static FXMLDocumentController crearOrigen() {
        FXMLDocumentController fdc = null;
        try {
            FXMLLoader myLoader = new FXMLLoader(Object.class.getResource("/Controladores/FXMLDocument.fxml"));
            Parent root = (Parent) myLoader.load();
            fdc = myLoader.<FXMLDocumentController>getController();
            Scene scene = new Scene(root);
            Stage stageDocument = new Stage();
            listVent.addVentana((MiVentana) fdc, stageDocument);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();
            stageDocument.setMaxHeight(height);
            stageDocument.setMaxWidth(width);
            stageDocument.setMinHeight(500);
            stageDocument.setMinWidth(1000);
            stageDocument.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                    al.setTitle("CERRANDO VENTANAS");
                    al.setHeaderText("Se van a cerrar todas las ventanas.");
                    al.setContentText("¿Está usted seguro?");
                    al.showAndWait();
                    if (al.resultProperty().get() == ButtonType.OK) {
                        cerrarTodas();
                    } else {
                        event.consume();
                    }
                }
            });
            stageDocument.setScene(scene);
            stageDocument.initModality(Modality.WINDOW_MODAL);
            stageDocument.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fdc;
    }

    public static FXMLComprarController crearComprar(Proyeccion p) {
        FXMLComprarController fdc = null;
        try {
            FXMLLoader myLoader = new FXMLLoader(Object.class.getResource("/Controladores/FXMLComprar.fxml"));
            Parent root = (Parent) myLoader.load();
            fdc = myLoader.<FXMLComprarController>getController();
            fdc.init(p);
            Scene scene = new Scene(root);
            Stage stageComprar = new Stage();
            listVent.addVentana((MiVentana) fdc, stageComprar);
            stageComprar.setScene(scene);
            stageComprar.initModality(Modality.WINDOW_MODAL);
            stageComprar.show();
            stageComprar.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            listVent.addVentana((MiVentana) fdc, stageReservar);
            stageReservar.setScene(scene);
            stageReservar.initModality(Modality.WINDOW_MODAL);
            stageReservar.show();
            stageReservar.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fdc;
    }

    public static void cerrarTodas() {
        for (MiVentana mv : listVent.getVentanasAbiertas()) {
            mv.cerrar();
        }
    }

    public static void refrescarTodas() {
        for (MiVentana mv : listVent.getVentanasAbiertas()) {
            mv.refrescar();
        }
    }
}
