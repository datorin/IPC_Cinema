/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import accesoaBD.AccesoaBD;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.Pelicula;
import modelo.Proyeccion;
import modelo.Reserva;
/**
 *
 * @author Daniel
 */
public class DataBase {

    private ArrayList<Proyeccion> proyecciones;

    private ArrayList<Pelicula> peliculas;
    
    private final AccesoaBD accesoaDB = new AccesoaBD();

    public DataBase() {
        AccesoaBD a = new AccesoaBD();
        
        proyecciones = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            LocalDate ld = LocalDate.of(2017, Month.APRIL, i);
            proyecciones.addAll(a.getProyeccionesDia(ld));
        }
        peliculas = (ArrayList)a.getTodasPeliculas();
    }

    public ArrayList<Proyeccion> getProyecciones() {
        return proyecciones;
    }

    public void setProyecciones(ArrayList<Proyeccion> proyecciones) {
        this.proyecciones = proyecciones;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
    
    public ArrayList<String> getDias(){
        ArrayList<String> al = new ArrayList<>();
        for (Proyeccion p : proyecciones) {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String st_dt = dt.format(p.getDia());
            if(!al.contains(st_dt)) al.add(st_dt);
        }
        return al;
    }
    public void guardarProyecciones(){
        for(Proyeccion p : proyecciones){
            accesoaDB.salvarProyeccion(p);
        }
    }
public ArrayList<Reserva> getTodasReservas(){
        ArrayList<Reserva> lista = new ArrayList<>();
        for(Proyeccion p : proyecciones){
            for(Reserva r : p.getReservas()){
                lista.add(r);
            }
        }
        return lista;
    }
}
