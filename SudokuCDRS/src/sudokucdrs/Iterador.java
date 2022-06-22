/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokucdrs;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author aleba
 */
public class Iterador<T> implements Iterator<T> {
    private T[] coleccion;
    private int total;
    private int actual;
    
    public Iterador(int tot, T[] arre){
        coleccion=arre;
        total=tot;
        actual=0;
    }
    
    public boolean hasNext(){
        return actual<total;
    }
    
    public T next(){
        if(hasNext()){
            T res;
            res=coleccion[actual];
            actual++;
            return res;
        }
        else
            throw new NoSuchElementException();
    }
    
    public void remove(){
        throw new UnsupportedOperationException("Operacion no lograda");
    }
    
    
}

