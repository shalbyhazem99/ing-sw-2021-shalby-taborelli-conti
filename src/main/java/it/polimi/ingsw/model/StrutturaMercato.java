package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class StrutturaMercato {
    private static final int COLUMNNUMBER = 4;
    private static final int ROWNUMBER = 3;

    private Biglia[][] matriceMercato; //[row][column]
    private Biglia bigliaAggiuntiva;

    private void generateMarblesPos() {
        ArrayList<Biglia> lista = new ArrayList<>();
        //Marbles : 4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red
        for (int i = 0; i < 4; i++)
            lista.add(Biglia.getInstance(ColoreBiglia.BIANCHE));
        for (int i = 0; i < 2; i++)
            lista.add(Biglia.getInstance(ColoreBiglia.BLU));
        for (int i = 0; i < 2; i++)
            lista.add(Biglia.getInstance(ColoreBiglia.GRIGIE));
        for (int i = 0; i < 2; i++)
            lista.add(Biglia.getInstance(ColoreBiglia.GIALLE));
        for (int i = 0; i < 2; i++)
            lista.add(Biglia.getInstance(ColoreBiglia.VIOLA));
        for (int i = 0; i < 1; i++)
            lista.add(Biglia.getInstance(ColoreBiglia.ROSSA));
        Collections.shuffle(lista);
        //fill the matrix
        int count = 0;
        for (int row = 0; row < ROWNUMBER; row++) {
            for (int column = 0; column < COLUMNNUMBER; column++) {
                matriceMercato[row][column] = lista.get(count);
                count++;
            }
        }
        bigliaAggiuntiva = lista.get(count);
    }

    public StrutturaMercato() {
        generateMarblesPos();
    }

    public static StrutturaMercato getInstance() {
        return new StrutturaMercato();
    }

    public ArrayList<Risorsa> getResources(MOSSA mossa, int pos) {
        ArrayList<Biglia> temp;
        if (mossa == MOSSA.RIGA) {
            temp = getRow(pos);
            slideRow(pos);
        } else {
            temp = getColumn(pos);
            slideColumn(pos);
        }
        return (ArrayList<Risorsa>) temp
                .stream()
                .filter(elem -> elem.getColore() != ColoreBiglia.BIANCHE)
                .map(elem -> Risorsa.getInstance(elem.getColore().equivalent))
                .collect(Collectors.toList());
        //the number of white Marbles is 4 - count of the returned ArrayList
    }

    /*public ArrayList<Risorsa> getResources(Integer row, Integer column) {
        ArrayList<Risorsa> temp;
        if (row != null) {
            System.out.println("Select row n: " + row);
            temp = converter.convertMarbleToResources(getRow(row));
            slideRow(row);
        } else {
            System.out.println("Select row n: " + column);
            slideColumn(column);
            temp = converter.convertMarbleToResources(getColumn(column));
        }
        return temp;
    }*/

    /*
        @requires 0<=column<=3
        @ensures /result = selected column
     */
    public ArrayList<Biglia> getColumn(int column) {
        ArrayList<Biglia> temp = new ArrayList<>();
        for (int r = 0; r < ROWNUMBER; r++) {
            temp.add(matriceMercato[r][column]);
        }
        return temp;
    }

    /*
        @requires 0<=row<=2
        @ensures /result = selected row
     */
    public ArrayList<Biglia> getRow(int row) {
        ArrayList<Biglia> temp = new ArrayList<>();
        for (int c = 0; c < COLUMNNUMBER; c++) {
            temp.add(matriceMercato[row][c]);
        }
        return temp;
    }

    public void print() {
        for (int r = 0; r < ROWNUMBER; r++) {
            for (int c = 0; c < COLUMNNUMBER; c++) {
                System.out.print("|" + matriceMercato[r][c].toString() + "|");
            }
            System.out.println();
        }
        System.out.println(bigliaAggiuntiva.toString());
        System.out.println("----------------------------------------------");
    }

    /*
    @requires 0<=row<=2
     */
    private void slideRow(int row) {
        Biglia oldAdditionalMurble = bigliaAggiuntiva;
        bigliaAggiuntiva = matriceMercato[row][0]; //the murble in the left position of the row will be the next additionalmurble
        matriceMercato[row][0] = matriceMercato[row][1]; //slide to left
        matriceMercato[row][1] = matriceMercato[row][2]; //slide to left
        matriceMercato[row][2] = matriceMercato[row][3]; //slide to left
        matriceMercato[row][3] = oldAdditionalMurble; //the old additional murble will be the murble in the right position of the row
    }

    /*
    @requires 0<=column<=3
     */
    private void slideColumn(int column) {
        Biglia oldAdditionalMurble = bigliaAggiuntiva;
        bigliaAggiuntiva = matriceMercato[0][column]; //the murble in the top position of the column will be the next additionalmurble
        matriceMercato[0][column] = matriceMercato[1][column]; //slide top
        matriceMercato[1][column] = matriceMercato[2][column]; //slide top
        matriceMercato[2][column] = oldAdditionalMurble;  //the old additional murble will be the murble in the bottom position of the column
    }

   /* public static void main(String[] args) {
        StrutturaMercato s = new StrutturaMercato();
        s.print();
        ArrayList<Risorsa> a = s.getResources(1, null);
        s.print();
        a = s.getResources(null, 2);
        s.print();
    }*/

    public enum MOSSA {
        RIGA,
        COLONNA;
    }

}
