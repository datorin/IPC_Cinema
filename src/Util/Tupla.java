/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author Daniel
 */
public class Tupla {
    
    private int i;
    
    private int j;

    public Tupla(int i, int j) {
        this.i = i;
        this.j = j;
    }
    
    public boolean equals(Tupla t){
        return this.i == t.i && this.j == t.j;
    }
}
