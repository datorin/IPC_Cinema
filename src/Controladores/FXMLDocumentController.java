/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Util.CreadorVentanas;
import Util.Singleton;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Reserva;

/**
 *
 * @author Daniel
 */
public class FXMLDocumentController implements Initializable, MiVentana {

    @FXML
    private ScrollPane contenedorPeliculas;
    @FXML
    private Tab tabActualidad;
    @FXML
    private Tab tabVerReservas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initPeliculas();
        initActualidad();
        initVerReservas();
    }

    private void initPeliculas() {
        GridPane peliGrid = new GridPane();
        peliGrid.setVgap(10);
        int contador = 0;
        for (Pelicula p : Singleton.getDataBase().getPeliculas()) {
            try {
                ColumnConstraints c = new ColumnConstraints();
                c.setPercentWidth(100 / 5);
                c.setHalignment(HPos.CENTER);

                ImageView peliImage = new ImageView();
                InputStream is = this.getClass().getResourceAsStream(p.getPathImage());
                Image image = new Image(is, 330, 220, true, true);
                peliImage.setImage(image);

                peliGrid.add(peliImage, contador, 0);
                peliGrid.getColumnConstraints().add(contador, c);

                Button btnReservar = new Button("RESERVAR");
                btnReservar.setPadding(new Insets(10, 45, 10, 45));

                btnReservar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        CreadorVentanas.crearReservar(p);
                    }
                });
                peliGrid.add(btnReservar, contador, 1);

                contador++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        contenedorPeliculas.setContent(peliGrid);
        contenedorPeliculas.setFitToWidth(true);
        contenedorPeliculas.setPadding(new Insets(20, 0, 0, 0));
    }

    private void initActualidad() {
        TabPane root = new TabPane();
        tabActualidad.setContent(root);
        for (String s : Singleton.getDataBase().getDias()) {
            Tab tb = new Tab(s);
            tb.setClosable(false);
            root.getTabs().add(tb);
            VBox vb = new VBox();
            tb.setContent(vb);
            GridPane gp1 = new GridPane();
            gp1.setPadding(new Insets(0, 13.5, 0, 0));
            ColumnConstraints c0 = new ColumnConstraints();
            c0.setPercentWidth(200 / 6);
            c0.setHalignment(HPos.CENTER);
            ColumnConstraints c1 = new ColumnConstraints();
            c1.setPercentWidth(100 / 6);
            c1.setHalignment(HPos.CENTER);
            ColumnConstraints c2 = new ColumnConstraints();
            c2.setPercentWidth(200 / 6);
            c2.setHalignment(HPos.CENTER);
            ColumnConstraints c3 = new ColumnConstraints();
            c3.setPercentWidth(100 / 6);
            c3.setHalignment(HPos.CENTER);
            ColumnConstraints c4 = new ColumnConstraints();
            c4.setPercentWidth(100 / 6);
            c4.setHalignment(HPos.CENTER);
            gp1.getColumnConstraints().addAll(c0, c1, c2, c3, c4);
            Label l = new Label("TÍTULO DE LAS PELÍCULAS");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 0, 0);
            l = new Label("HORAS");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 1, 0);
            l = new Label("NUM LOCALIDADES");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 2, 0);
            l = new Label("CAPACIDAD");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 3, 0);
            l = new Label("COMPRAR");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 4, 0);
            vb.getChildren().add(gp1);
            // Creando GridPane proyecciones
            GridPane gp2 = new GridPane();
            gp2.setVgap(10);
            int contador = 0;
            for (Proyeccion p : Singleton.getDataBase().getProyecciones()) {
                DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String st_dt = dt.format(p.getDia());
                if (st_dt.equals(s)) {
                    // Creando las columna de la GridPane
                    c0 = new ColumnConstraints();
                    c0.setPercentWidth(200 / 6);
                    c0.setHalignment(HPos.LEFT);
                    c1 = new ColumnConstraints();
                    c1.setPercentWidth(100 / 6);
                    c1.setHalignment(HPos.CENTER);
                    c2 = new ColumnConstraints();
                    c2.setPercentWidth(200 / 6);
                    c2.setHalignment(HPos.CENTER);
                    c3 = new ColumnConstraints();
                    c3.setPercentWidth(100 / 6);
                    c3.setHalignment(HPos.CENTER);
                    c4 = new ColumnConstraints();
                    c4.setPercentWidth(100 / 6);
                    c4.setHalignment(HPos.CENTER);
                    gp2.getColumnConstraints().setAll(c0, c1, c2, c3, c4);

                    // Colocando TITULO de las proyecciones
                    l = new Label(p.getPelicula().getTitulo());
                    l.setPadding(new Insets(15, 15, 15, 15));
                    l.setStyle("-fx-font: 15 arial;");
                    gp2.add(l, 0, contador);

                    // Colocando HORA de las proyecciones
                    l = new Label(p.getHoraInicio());
                    l.setPadding(new Insets(15, 15, 15, 15));
                    l.setStyle("-fx-font: 15 arial;");
                    gp2.add(l, 1, contador);

                    // Colocando y Creando CANDTIDAD de las proyecciones
                    HBox hb = new HBox(40);
                    hb.setAlignment(Pos.CENTER);
                    Button btnRestarCantidad = new Button("-");
                    btnRestarCantidad.setPadding(new Insets(11, 22, 11, 22));
                    TextField fieldCantidad = new TextField("1");
                    fieldCantidad.setPrefWidth(50);
                    fieldCantidad.setAlignment(Pos.CENTER);
                    Button btnSumarCantidad = new Button("+");
                    btnSumarCantidad.setPadding(new Insets(10, 20, 10, 20));
                    btnRestarCantidad.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                int valorActual = Integer.parseInt(fieldCantidad.getText());
                                valorActual--;
                                int i = 0;
                                if (valorActual > p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                                    valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
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
                    });
                    fieldCantidad.setOnKeyTyped(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if (!"0123456789".contains(event.getCharacter())) {
                                event.consume();
                                return;
                            }
                            int i = 0;
                            if (Integer.parseInt(fieldCantidad.getText() + event.getCharacter()) > p.getSala().getCapacidad()
                                    - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                                event.consume();
                                int valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
                                String s = Integer.toString(valorActual);
                                fieldCantidad.setText(s);
                            }
                            if (Integer.parseInt(fieldCantidad.getText() + event.getCharacter()) == 0) {
                                event.consume();
                                fieldCantidad.setText(Integer.toString(1));
                            }
                        }
                    });
                    btnSumarCantidad.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                int valorActual = Integer.parseInt(fieldCantidad.getText());
                                valorActual++;
                                int i = 0;
                                if (valorActual > p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum)) {
                                    valorActual = p.getSala().getCapacidad() - p.getReservas().stream().map((r) -> r.getNumLocalidades()).reduce(i, Integer::sum);
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
                    });
                    hb.getChildren().addAll(btnRestarCantidad, fieldCantidad, btnSumarCantidad);
                    gp2.add(hb, 2, contador);

                    // Colocando CAPACIDAD de las proyecciones
                    int reservas = 0;
                    for (Reserva r : p.getReservas()) {
                        reservas += r.getNumLocalidades();
                    }
                    l = new Label(Integer.toString(reservas + p.getSala().getEntradasVendidas()) + " / " + p.getSala().getCapacidad());
                    gp2.add(l, 3, contador);

                    // Colocando y Creando el boton COMPRAR de las proyecciones
                    Button btnComprar = new Button("COMPRAR");
                    btnComprar.setPadding(new Insets(10, 10, 10, 10));
                    HBox.setMargin(btnComprar, new Insets(5, 5, 5, 5));
                    btnComprar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (fieldCantidad.getText().isEmpty()) {
                                fieldCantidad.setText(Integer.toString(1));
                            }
                            CreadorVentanas.crearComprar(p, fieldCantidad.getText());
                            fieldCantidad.setText(Integer.toString(1));
                        }
                    });
                    gp2.add(btnComprar, 4, contador);
                    contador++;
                }
            }
            ScrollPane sp = new ScrollPane();
            sp.setContent(gp2);
            sp.setFitToWidth(true);
            vb.getChildren().add(sp);
        }
    }

    private void initVerReservas() {
        TabPane root = new TabPane();
        tabVerReservas.setContent(root);
        for (String s : Singleton.getDataBase().getDias()) {
            Tab tb = new Tab(s);
            tb.setClosable(false);
            root.getTabs().add(tb);
            VBox vb = new VBox();
            tb.setContent(vb);
            GridPane gp1 = new GridPane();
            gp1.setPadding(new Insets(0, 13.5, 0, 0));
            ColumnConstraints c0 = new ColumnConstraints();
            c0.setPercentWidth(200 / 6);
            c0.setHalignment(HPos.CENTER);
            ColumnConstraints c1 = new ColumnConstraints();
            c1.setPercentWidth(100 / 6);
            c1.setHalignment(HPos.CENTER);
            ColumnConstraints c2 = new ColumnConstraints();
            c2.setPercentWidth(100 / 6);
            c2.setHalignment(HPos.CENTER);
            ColumnConstraints c3 = new ColumnConstraints();
            c3.setPercentWidth(200 / 6);
            c3.setHalignment(HPos.CENTER);
            ColumnConstraints c4 = new ColumnConstraints();
            c4.setPercentWidth(100 / 6);
            c4.setHalignment(HPos.CENTER);
            ColumnConstraints c5 = new ColumnConstraints();
            c5.setPercentWidth(100 / 6);
            c5.setHalignment(HPos.CENTER);
            gp1.getColumnConstraints().addAll(c0, c1, c2, c3, c4, c5);
            Label l = new Label("NOMBRE");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 0, 0);
            l = new Label("TELEFONO");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 1, 0);
            l = new Label("Nº LOCALID.");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 2, 0);
            l = new Label("TÍTULO DE LAS PELÍCULAS");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 3, 0);
            l = new Label("CANCELAR");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 4, 0);
            l = new Label("COMPRAR");
            l.setPadding(new Insets(15, 15, 15, 15));
            l.setStyle("-fx-font: 15 arial;");
            gp1.add(l, 5, 0);
            vb.getChildren().add(gp1);
            // Creando GridPane reservas
            GridPane gp2 = new GridPane();
            gp2.setVgap(10);
            int contador = 0;
            for (Reserva r : Singleton.getDataBase().getTodasReservas()) {

                // Creando las columna de la GridPane
                c0 = new ColumnConstraints();
                c0.setPercentWidth(200 / 7);
                c0.setHalignment(HPos.LEFT);
                c1 = new ColumnConstraints();
                c1.setPercentWidth(100 / 7);
                c1.setHalignment(HPos.CENTER);
                c2 = new ColumnConstraints();
                c2.setPercentWidth(100 / 7);
                c2.setHalignment(HPos.CENTER);
                c3 = new ColumnConstraints();
                c3.setPercentWidth(200 / 7);
                c3.setHalignment(HPos.CENTER);
                c4 = new ColumnConstraints();
                c4.setPercentWidth(100 / 7);
                c4.setHalignment(HPos.CENTER);
                c5 = new ColumnConstraints();
                c5.setPercentWidth(100 / 7);
                c5.setHalignment(HPos.CENTER);
                gp2.getColumnConstraints().setAll(c0, c1, c2, c3, c4, c5);

                // Colocando nombres reservas
                l = new Label(r.getNombre());
                l.setPadding(new Insets(15, 15, 15, 15));
                l.setStyle("-fx-font: 15 arial;");
                gp2.add(l, 0, contador);

                // Colocando telefonos reservas
                l = new Label(r.getTelefono());
                l.setPadding(new Insets(15, 15, 15, 15));
                l.setStyle("-fx-font: 15 arial;");
                gp2.add(l, 1, contador);

                // Colocando localidades reservas
                l = new Label(Integer.toString(r.getNumLocalidades()));
                l.setPadding(new Insets(15, 15, 15, 15));
                l.setStyle("-fx-font: 15 arial;");
                gp2.add(l, 2, contador);

                // Colocando nombre peliculas reservas
                Proyeccion pp = new Proyeccion();
                for (Proyeccion pro : Singleton.getDataBase().getProyecciones()) {
                    for (Reserva re : pro.getReservas()) {
                        if (r == re) {
                            pp = pro;
                            break;
                        }
                    }
                }
                l = new Label(pp.getPelicula().getTitulo());
                l.setPadding(new Insets(15, 15, 15, 15));
                l.setStyle("-fx-font: 15 arial;");
                gp2.add(l, 3, contador);

                // Colocando y Creando el boton CANCELAR de las reservas
                Button btnReservar = new Button("CANCELAR");
                btnReservar.setPadding(new Insets(10, 10, 10, 10));
                HBox.setMargin(btnReservar, new Insets(5, 5, 5, 5));
                btnReservar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("CANCELANDO");
                        al.setHeaderText("Usted va a eliminar una reserva.");
                        al.setContentText("¿Está usted seguro?");
                        al.showAndWait();
                        if (al.resultProperty().get() == ButtonType.OK) {
                        }
                    }
                });
                gp2.add(btnReservar, 4, contador);

                // Colocando y Creando el boton COMPRAR de las reservas
                Button btnComprar = new Button("COMPRAR");
                btnComprar.setPadding(new Insets(10, 10, 10, 10));
                HBox.setMargin(btnComprar, new Insets(5, 5, 5, 5));
                btnComprar.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        CreadorVentanas.crearComprarReservar(r);
                    }
                });
                gp2.add(btnComprar, 5, contador);
                contador++;

            }
            ScrollPane sp = new ScrollPane();
            sp.setContent(gp2);
            sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            gp1.setPadding(new Insets(0, 13.5, 0, 0));
            sp.setFitToWidth(true);
            vb.getChildren().add(sp);
        }

    }

    @Override
    public void refrescar() {
        initActualidad();
        initVerReservas();
    }

    @Override
    public void cerrar() {
        Node a = (Node) contenedorPeliculas.getParent();
        ((Stage) a.getScene().getWindow()).close();
    }

}
