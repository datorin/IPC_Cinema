/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controladores.MiVentana;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Daniel
 */
public class ListaVentanas {
    private final ArrayList<MiVentana> ventanasAbiertas = new ArrayList<>();
    
    public ListaVentanas(){
        
    }
    
    public void addVentana(MiVentana mv, Stage stg) {
        ventanasAbiertas.add(mv);
        stg.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ventanasAbiertas.remove(mv);
            }
        });
    }

    public ArrayList<MiVentana> getVentanasAbiertas() {
        return ventanasAbiertas;
    }
    
    
}
