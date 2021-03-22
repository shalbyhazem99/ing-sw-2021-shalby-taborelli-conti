package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class MarketStructure {
    private static final int COLUMNNUMBER = 4;
    private static final int ROWNUMBER = 3;

    private Biglia[][] matrixMarket; //[row][column]
    private Biglia addedMarble;

    private void generateMarblesPos() {
        ArrayList<Marble> list = new ArrayList<>();
        //Marbles : 4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red
        for (int i = 0; i < 4; i++)
            list.add(Marble.getInstance(ColorMarble.WHITE));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(ColorMarble.BLUE));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(ColorMarble.GREY));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(ColorMarble.YALLOW));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(ColorMarble.PURPLE));
        for (int i = 0; i < 1; i++)
            list.add(Marble.getInstance(ColorMarble.RED));
        Collections.shuffle(list);
        //fill the matrix
        int count = 0;
        for (int row = 0; row < ROWNUMBER; row++) {
            for (int column = 0; column < COLUMNNUMBER; column++) {
                matrixMarket[row][column] = list.get(count);
                count++;
            }
        }
        addedMarble = list.get(count);
    }

    public MarketStructure() {
        generateMarblesPos();
    }

    public static MarketStructure getInstance() {
        return new MarketStructure();
    }

    \\TODO: wtf is MOVE!?!?!!?
    public ArrayList<Resource> getResources(MOVE move, int pos) {
        ArrayList<Marble> temp;
        if (move == move.ROW) {
            temp = getRow(pos);
            slideRow(pos);
        } else {
            temp = getColumn(pos);
            slideColumn(pos);
        }
        return (ArrayList<Resource>) temp
                .stream()
                .filter(elem -> elem.getColor() != ColorMarble.WHITE)
                .map(elem -> Resource.getInstance(elem.getColor().equivalent))
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
    public ArrayList<Marble> getColumn(int column) {
        ArrayList<Marble> temp = new ArrayList<>();
        for (int r = 0; r < ROWNUMBER; r++) {
            temp.add(matrixMarket[r][column]);
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
            temp.add(matrixMarket[row][c]);
        }
        return temp;
    }

    public void print() {
        for (int r = 0; r < ROWNUMBER; r++) {
            for (int c = 0; c < COLUMNNUMBER; c++) {
                System.out.print("|" + matrixMarket[r][c].toString() + "|");
            }
            System.out.println();
        }
        System.out.println(addedMarble.toString());
        System.out.println("----------------------------------------------");
    }

    /*
    @requires 0<=row<=2
     */
    private void slideRow(int row) {
        Biglia oldAdditionalMurble = addedMarble;
        addedMarble = matrixMarket[row][0]; //the murble in the left position of the row will be the next additionalmurble
        matrixMarket[row][0] = matrixMarket[row][1]; //slide to left
        matrixMarket[row][1] = matrixMarket[row][2]; //slide to left
        matrixMarket[row][2] = matrixMarket[row][3]; //slide to left
        matrixMarket[row][3] = oldAdditionalMurble; //the old additional murble will be the murble in the right position of the row
    }

    /*
    @requires 0<=column<=3
     */
    private void slideColumn(int column) {
        Biglia oldAdditionalMurble = addedMarble;
        addedMarble = matrixMarket[0][column]; //the murble in the top position of the column will be the next additionalmurble
        matrixMarket[0][column] = matrixMarket[1][column]; //slide top
        matrixMarket[1][column] = matrixMarket[2][column]; //slide top
        matrixMarket[2][column] = oldAdditionalMurble;  //the old additional murble will be the murble in the bottom position of the column
    }

   /* public static void main(String[] args) {
        MarketStructure s = new MarketStructure();
        s.print();
        ArrayList<Risorsa> a = s.getResources(1, null);
        s.print();
        a = s.getResources(null, 2);
        s.print();
    }*/

    public enum MOVE {
        ROW,
        COL;
    }

}
