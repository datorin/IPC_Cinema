/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.InputStream;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private String maximo;

    public ImagenLocalidad(ImageView iv, int i, int j, ArrayList<Tupla> seleccionados, ArrayList<Tupla> ocupadas, TextField tf) {
        this.seleccionados = seleccionados;
        this.ocupadas = ocupadas;
        this.maximo = tf.getText();
        asientos(iv, i, j);
    }

    public ImagenLocalidad(ImageView iv, int i, int j, ArrayList<Tupla> seleccionados, ArrayList<Tupla> ocupadas, Label l) {
        this.seleccionados = seleccionados;
        this.ocupadas = ocupadas;
        this.maximo = l.getText();
        asientos(iv, i, j);
    }

    private void asientos(ImageView iv, int i, int j) {
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
                        if (Integer.parseInt(maximo) > seleccionados.size()) {
                            seleccionados.add(new Tupla(i, j));
                            InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaSeleccionada.png");
                            Image image = new Image(is, 38, 45.5, true, true);
                            iv.setImage(image);
                        }
                    } else {
                        seleccionados.remove(seleccionada);
                        InputStream is = this.getClass().getResourceAsStream("/Imagenes/butacaVacia.png");
                        Image image = new Image(is, 38, 45.5, true, true);
                        iv.setImage(image);
                    }
                }
            });
        }
    }

}
