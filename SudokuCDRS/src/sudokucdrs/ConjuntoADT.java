package sudokucdrs;

import java.util.Iterator;

/**
 *
 * @author aleba
 * @param <T>
 */
public interface ConjuntoADT<T> extends Iterable<T>{
    
    @Override
    public Iterator<T> iterator();
    public boolean Inserta(T elem);
    public T Elimina(T elem);
    public boolean Contiene(T elem);
    public int getCardinalidad();
    public boolean estaVacio();
    public ConjuntoADT<T> Union(ConjuntoADT<T> b);
    public ConjuntoADT<T> Interseccion(ConjuntoADT<T> b);
    public ConjuntoADT<T> Diferencia(ConjuntoADT<T> b);
    
}
