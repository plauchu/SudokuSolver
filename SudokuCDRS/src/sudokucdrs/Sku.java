/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokucdrs;
import java.util.Iterator;

/**
 * @author Constanza Ybarra Trapote
 * @author Daniel Alejandro Nieto Barreto
 * @author Rodrigo Plauchú Rodríguez
 * @author Santiago Fernandez Gutiérrez Zamora
 */
public class Sku {

    private int[][] sudoku;// matriz 
    private ConjuntoADT<Integer>[][] conjuntos;//matriz de la que cada renglón representa renglones, columnas y regiones, respectivamente
    private final ConjuntoADT<Integer> auxiliar;//conjunto que contiene los dígitos de 1 a 9 

    public Sku() {
        sudoku = new int[9][9];
       
        conjuntos=new ConjuntoA[3][9];
        auxiliar = new ConjuntoA();
        int i;
        for (i = 0; i <= 9; i++) {//llenado del conjunto auxiliar con los nueve dígitos 
            auxiliar.Inserta(i);
        }
        for(i=0;i<3;i++)
            for(int j=0;j<9;j++){
                conjuntos[i][j]=new ConjuntoA();//instanciación de todos los conjuntos
                for(int k=1;k<10;k++)
                    conjuntos[i][j].Inserta(k);//llenado de los conjuntos de la matriz con los nueve dígitos
            }
        
    }
    /**
     * 
     * @return La matriz de la clase
     * Este método es llamado para vertir los datos del sudoku resuelto en la interfaz gráfica
     */
    public int[][] getSudoku() {
        return sudoku;
    }
    
    /**
     * 
     * @return Impresión de la matriz que representa al sudoku 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sudoku\n");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append("[" + sudoku[i][j] + "] ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param r: renglón de la casilla de la que se quiere averiguar la región
     * @param c: columna de la casilla de la que se quiere averiguar la región
     * @return la región corresppondiente al renglón y la columna con un valor
     * entre 0 y 8
     */
    private int ObtenRegion(int r, int c) {
        return (3 * (r / 3)) + (c / 3);
    }

    /**
     *
     * @param r: renglón en el que se encuentra la casilla
     * @param c: columna en el que se encuentra la casilla
     * @param digito: valor que se pretende insertar en la casilla
     * @return un valor boolean que indica si es posible insertar o no el dígito
     * en la casilla determinada por r,c
     */
    private boolean sePuede(int r, int c, int reg,int digito) {
        boolean resp;
        if (conjuntos[0][r].Contiene(digito) && conjuntos[1][c].Contiene(digito) && conjuntos[2][reg].Contiene(digito) && sudoku[r][c] == 0) {
            resp = true;
        } 
        else {
            resp = false;
        }
        return resp;
    }

    /**
     *
     * @param digito: representa el dígito que se quiere agregar a la matriz
     * @param r: el renglín dónde se quiere insertar el dígito
     * @param c: columna donde se quiere insertar el dígito
     * @return true si el alta fue exitosa, de lo contrario false
     */
    public boolean alta(int digito, int r, int c) {
        boolean resp;
        if (auxiliar.Contiene(digito)) {
            int reg = ObtenRegion(r, c);
            if (sePuede(r, c,reg, digito)) {
                sudoku[r][c] = digito;
                conjuntos[0][r].Elimina(digito);
                conjuntos[1][c].Elimina(digito);
                conjuntos[2][reg].Elimina(digito);
                resp = true;
            } else {
                resp = false;
            }
        } else {

            resp = false;
        }
        return resp;
    }


    /**
     *
     * @param r: renglón de la casilla de la que se quieren averiguar los
     * dígitos posibles
     * @param c: columna de la casilla de la que se quieren averiguar los
     * dígitos posibles
     * @return un conjunto resultado de la intersecciona los conjuntos del
     * renglón, columna y region correspondientes a la casilla determinada por
     * r,c
     */
    private ConjuntoADT<Integer> digitosPosibles(int r, int c) {
        int reg = ObtenRegion(r, c);
        ConjuntoADT<Integer> opc = new ConjuntoA(),resp=new ConjuntoA();
        opc = conjuntos[0][r].Interseccion(conjuntos[2][reg]);
        resp=opc.Interseccion(conjuntos[1][c]);
        return resp;
    }

    /**
     *
     * @param r: representa el renglon actual
     * @param c: representa la columna actual
     * @return un arreglo en el que la primera casilla guarda el renglón y la
     * segunda casilla la columna de la posición siguiente
     */
    private int[] posicionSig(int r, int c) {
        int[] pos = new int[2];
        if (c < 8) {
            pos[0] = r;
            pos[1] = c + 1;
        } else {
            pos[0] = r + 1;
            pos[1] = 0;
        }
        return pos;
    }

    /**
     *
     * @return true si se pudo resolver la matriz, de lo contrario, false
     */
    public boolean resuelve() {
        return resuelve1(0, 0);
    }

    /**
     *
     * @param posR indica la posición en la que se encuentra del avance en los
     * renglones
     * @param posC indica la posición en la que se encuentra del avance en las
     * columnas
     * @return true si terminó la solución, false si se topó con algún caso
     * inlograble La primera condición checa el estado base del método La
     * segunda condición verifica que la casilla en la que se está situado esté
     * vacía La tercera condición verifica si existe alguna posibilidad en dicha
     * casilla, en caso de no haberla, regresa false Las condiciones
     * subsecuentes son para continuar los diferentes casos cuando hay error
     */
    private boolean resuelve1(int posR, int posC) {
        boolean resp = false;
        if (posR == 9 && posC == 0) {//estado base
            return true;
        } 
        else {
            if (sudoku[posR][posC] == 0) {//checa si la casilla está vacía, o tiene un valor dado por el usuario            
                ConjuntoADT<Integer> posibilidad = digitosPosibles(posR, posC);
                if (!posibilidad.estaVacio()) {//verifica que haya opciones para la casilla en la que se esté situado
                    Iterator<Integer> it=posibilidad.iterator();
                    while(it.hasNext()){
                        int reg = ObtenRegion(posR, posC);
                        int digitoEntrada=it.next();
                        sudoku[posR][posC] = digitoEntrada;
                        conjuntos[2][reg].Elimina(digitoEntrada);
                        conjuntos[1][posC].Elimina(digitoEntrada);
                        conjuntos[0][posR].Elimina(digitoEntrada);
                        int[] posSig = posicionSig(posR, posC);
                        if(resuelve1(posSig[0], posSig[1]))//llamada recursiva
                            return true;
                        else{
                            sudoku[posR][posC] = 0;
                            conjuntos[0][posR].Inserta(digitoEntrada);
                            conjuntos[1][posC].Inserta(digitoEntrada);
                            conjuntos[2][reg].Inserta(digitoEntrada);
                        }      
                    }
                    return resp;
                } 
                else {
                    return resp;
                }
            } 
            else {
                int[] posSig = posicionSig(posR, posC);
                return resuelve1(posSig[0], posSig[1]);
            }
        }
    }

    /*public static void main(String[] args) {
        Sku miSudok = new Sku();
        int[][] panel = {{5,3,0,0,7,0,0,0,0},
                         {6,0,0,1,9,5,0,0,0},
                         {0,9,8,0,0,0,0,6,0},
                         {8,0,0,0,6,0,0,0,3},
                         {4,0,0,8,0,3,0,0,1},
                         {7,0,0,0,2,0,0,0,6},
                         {0,6,0,0,0,0,2,8,0},
                         {0,0,0,4,1,9,0,0,5},
                         {0,0,0,0,8,0,0,7,9}};
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                miSudok.alta(panel[i][j], i, j);
            }
        }
        System.out.println(miSudok.toString());
        System.out.println(miSudok.resuelve());
        System.out.println(miSudok.toString());
        
        
    }*/

   
}