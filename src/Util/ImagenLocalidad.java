/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.InputStream;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Daniel
 */
public class ImagenLocalidad {
    
    private ArrayList<Tupla> seleccionados;
    
    private ArrayList<Tupla> ocupadas;

    public ImagenLocalidad(ImageView iv, int i, int j, ArrayList<Tupla> seleccionados, ArrayList<Tupla> ocupadas) {
        this.seleccionados = seleccionados;
        this.ocupadas = ocupadas;
        asientos(iv, i, j);
    }
    
    private void asientos(ImageView iv, int i, int j){
        Tupla ocupada = null;
        for (Tupla t : ocupadas) {
            if (t.equals(new Tupla(i, j))) {
                ocupada = t;
                break;
            }
        }
        if (ocupada == null) {
            iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Tupla seleccionada = null;
                    for (Tupla t : seleccionados) {
                        if (t.equals(new Tupla(i, j))) {
                            seleccionada = t;
                            break;
                        }
                    }
                    if (seleccionada == null) {
                        seleccionados.add(seleccionada);
                        InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaSeleccionada.png");
                        Image image = new Image(is, 56.25, 68.25, true, true);
                        iv.setImage(image);
                    } else {
                        seleccionados.remove(seleccionada);
                        InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaVacia.png");
                        Image image = new Image(is, 56.25, 68.25, true, true);
                        iv.setImage(image);
                    }
                }
            });
        }
    }

}
