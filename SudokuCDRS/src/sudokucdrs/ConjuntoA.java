/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokucdrs;

import java.util.Iterator;

/**
 *
 * @author aleba
 */
public class ConjuntoA<T> implements ConjuntoADT<T> {
    private T[] Conjunto;
    private int cardinalidad;
    private final int MAX=9;
    
    public ConjuntoA(){
        Conjunto=(T[])new Object[MAX];
        cardinalidad=0;
    }
    
    public ConjuntoA(int max){
        Conjunto=(T[])new Object[max];
        cardinalidad=0;
    }

    public int getCardinalidad() {
        return cardinalidad;
    }
    
    public boolean estaVacio(){
        return cardinalidad==0;
    }
    
    public Iterator<T> iterator(){
        return new Iterador(cardinalidad, Conjunto);
    }
    
    //hay que hacer el Contiene con buúsqueda secuencial
    //con iteración
    public boolean Contiene(T elem){
        boolean resp;
        resp=false;
        Iterator<T> it=iterator();
        while(it.hasNext()&&!resp){
            resp=elem.equals(it.next());
        }
        return resp;
    }
    
    
    private void expande(){
        T[] nuevo = (T[])new Object[Conjunto.length*2];
        int i;
        for(i=0;i<cardinalidad; i++)
            nuevo[i] = Conjunto[i];
        Conjunto = nuevo;
    }
    
    public boolean Inserta(T elem){
        boolean resp;
        resp=false;
        if(!Contiene(elem)){
            resp=true;
            if(cardinalidad==Conjunto.length)
                expande();
            Conjunto[cardinalidad]=elem;
            cardinalidad++;
        }
        return resp;
    }
    
    public int buscaPosic(T elem){
        int i;
        i=0;
        while(i<cardinalidad&&!Conjunto[i].equals(elem))
            i++;
        if(i==cardinalidad)
            i=-1;
        return i;
    }
    
    public T Elimina(T dato){
        T res;
        int pos;
        pos=buscaPosic(dato);
        if(pos>=0){
            res=Conjunto[pos];
            Conjunto[pos]=Conjunto[cardinalidad-1];
            Conjunto[cardinalidad-1]=null;
            cardinalidad--;
        }
        else
            res=null;
        return res;
    }
    
public T quitaAleatorio() {
        T resul = null;
        if (!estaVacio()) {
            int elegido;
            elegido = (int) Math.floor(Math.random() * cardinalidad);
            if (elegido >= 0) {
                resul = Conjunto[elegido];
                Conjunto[elegido] = Conjunto[cardinalidad - 1];
                Conjunto[cardinalidad - 1] = null;
                cardinalidad--;
            }
        }
        return resul;
    }

    public ConjuntoADT<T> Union(ConjuntoADT<T> conj) {
        ConjuntoA<T> res = new ConjuntoA();
        if(conj != null){         
            Iterator<T> it1 = iterator();
            Iterator<T> it2 = conj.iterator();

            while(it1.hasNext())
                res.Inserta(it1.next());
            while(it2.hasNext())
                res.Inserta(it2.next());    
        }
        return res;
    }

    public ConjuntoADT<T> Interseccion(ConjuntoADT<T> conj) {
        ConjuntoA res = new ConjuntoA();
        Iterator<T> it = iterator();
        T aux;
        
        if(conj != null){  
            while(it.hasNext()){
                aux = it.next();
                if(conj.Contiene(aux))
                    res.Inserta(aux);
            } 
        }
        return res;
    }

    public ConjuntoADT<T> Diferencia(ConjuntoADT<T> conj) {       
        ConjuntoA res = new ConjuntoA();
        Iterator<T> it = iterator();
        T aux;
        
        if(conj != null){
            while(it.hasNext()){
                aux = it.next();
                if(!conj.Contiene(aux)){
                    res.Inserta(aux);
                }
            } 
        }
        return res;
    }
    
    public String toString(){
        StringBuilder sb=new StringBuilder();
        int i;
        sb.append("Conjunto\n");
        for(i=0;i<cardinalidad;i++){
            sb.append(Conjunto[i].toString()+"\n");
        }
        return sb.toString();
    }
}

