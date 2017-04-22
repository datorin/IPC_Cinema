/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Persistencia.DataBase;

/**
 *
 * @author Daniel
 */
public class Singleton {
    
    private static final DataBase db = new DataBase();
    
    private Singleton() {
    
    }
    
    public static DataBase getDataBase(){
        return db;
    }
}
