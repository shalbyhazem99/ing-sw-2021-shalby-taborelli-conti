package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class MarketBoard implements Serializable {
    private static final int COLUMNNUMBER = 4;
    private static final int ROWNUMBER = 3;

    private Marble[][] MarketMatrix; //[row][column]
    private Marble marbleAggiuntiva;

    private void generateMarblesMatrix() {
        ArrayList<Marble> lista = new ArrayList<>();
        //Marbles : 4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red
        for (int i = 0; i < 4; i++)
            lista.add(Marble.getInstance(MarbleColor.WHITE));
        for (int i = 0; i < 2; i++)
            lista.add(Marble.getInstance(MarbleColor.BLUE));
        for (int i = 0; i < 2; i++)
            lista.add(Marble.getInstance(MarbleColor.GREY));
        for (int i = 0; i < 2; i++)
            lista.add(Marble.getInstance(MarbleColor.YELLOW));
        for (int i = 0; i < 2; i++)
            lista.add(Marble.getInstance(MarbleColor.PURPLE));
        for (int i = 0; i < 1; i++)
            lista.add(Marble.getInstance(MarbleColor.RED));
        Collections.shuffle(lista);
        //fill the matrix
        int count = 0;
        for (int row = 0; row < ROWNUMBER; row++) {
            for (int column = 0; column < COLUMNNUMBER; column++) {
                MarketMatrix[row][column] = lista.get(count);
                count++;
            }
        }
        marbleAggiuntiva = lista.get(count);
    }

    public MarketBoard() {
        generateMarblesMatrix();
    }

    public static MarketBoard getInstance() {
        return new MarketBoard();
    }

    /**
     *
     * @param moveType
     * @param pos
     * @return the number of white Marbles is 4 - count of the returned ArrayList
     */
    public ArrayList<Resource> getResources(MoveType moveType, int pos) {
        ArrayList<Marble> temp;
        if (moveType == MoveType.RIGA) {
            temp = getRow(pos);
            slideRow(pos);
        } else {
            temp = getColumn(pos);
            slideColumn(pos);
        }
        return (ArrayList<Resource>) temp
                .stream()
                .filter(elem -> elem.getColor() != MarbleColor.WHITE)
                .map(elem -> Resource.getInstance(elem.getColor().equivalent))
                .collect(Collectors.toList());

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
    public ArrayList<Marble> getColumn(int column) {
        ArrayList<Marble> temp = new ArrayList<>();
        for (int r = 0; r < ROWNUMBER; r++) {
            temp.add(MarketMatrix[r][column]);
        }
        return temp;
    }

    /*
        @requires 0<=row<=2
        @ensures /result = selected row
     */
    public ArrayList<Marble> getRow(int row) {
        ArrayList<Marble> temp = new ArrayList<>();
        for (int c = 0; c < COLUMNNUMBER; c++) {
            temp.add(MarketMatrix[row][c]);
        }
        return temp;
    }

    public void print() {
        for (int r = 0; r < ROWNUMBER; r++) {
            for (int c = 0; c < COLUMNNUMBER; c++) {
                System.out.print("|" + MarketMatrix[r][c].toString() + "|");
            }
            System.out.println();
        }
        System.out.println(marbleAggiuntiva.toString());
        System.out.println("----------------------------------------------");
    }

    /*
    @requires 0<=row<=2
     */
    private void slideRow(int row) {
        Marble oldAdditionalMurble = marbleAggiuntiva;
        marbleAggiuntiva = MarketMatrix[row][0]; //the murble in the left position of the row will be the next additionalmurble
        MarketMatrix[row][0] = MarketMatrix[row][1]; //slide to left
        MarketMatrix[row][1] = MarketMatrix[row][2]; //slide to left
        MarketMatrix[row][2] = MarketMatrix[row][3]; //slide to left
        MarketMatrix[row][3] = oldAdditionalMurble; //the old additional murble will be the murble in the right position of the row
    }

    /*
    @requires 0<=column<=3
     */
    private void slideColumn(int column) {
        Marble oldAdditionalMurble = marbleAggiuntiva;
        marbleAggiuntiva = MarketMatrix[0][column]; //the murble in the top position of the column will be the next additionalmurble
        MarketMatrix[0][column] = MarketMatrix[1][column]; //slide top
        MarketMatrix[1][column] = MarketMatrix[2][column]; //slide top
        MarketMatrix[2][column] = oldAdditionalMurble;  //the old additional murble will be the murble in the bottom position of the column
    }

   /* public static void main(String[] args) {
        StrutturaMercato s = new StrutturaMercato();
        s.print();
        ArrayList<Risorsa> a = s.getResources(1, null);
        s.print();
        a = s.getResources(null, 2);
        s.print();
    }*/

    public enum MoveType implements Serializable{
        RIGA,
        COLONNA;
    }

}
