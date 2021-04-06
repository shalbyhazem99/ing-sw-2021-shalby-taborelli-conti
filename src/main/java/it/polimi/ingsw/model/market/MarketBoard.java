package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class MarketBoard implements Serializable {
    private Marble[][] marketMatrix; //[row][column]
    private Marble additionalMarble;

    public MarketBoard() {
        marketMatrix = new Marble[Utils.MARKET_ROW_NUMBER][Utils.MARKET_COL_NUMBER];
        generateMarblesMatrix();
    }

    public static MarketBoard getInstance() {
        return new MarketBoard();
    }

    /**
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

    private ArrayList<Marble> getColumn(int column) {
        ArrayList<Marble> temp = new ArrayList<>();
        for (int r = 0; r < Utils.MARKET_ROW_NUMBER; r++) {
            temp.add(marketMatrix[r][column]);
        }
        return temp;
    }

    private ArrayList<Marble> getRow(int row) {
        ArrayList<Marble> temp = new ArrayList<>();
        for (int c = 0; c < Utils.MARKET_COL_NUMBER; c++) {
            temp.add(marketMatrix[row][c]);
        }
        return temp;
    }

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
        for (int row = 0; row < Utils.MARKET_ROW_NUMBER; row++) {
            for (int column = 0; column < Utils.MARKET_COL_NUMBER; column++) {
                marketMatrix[row][column] = lista.get(count);
                count++;
            }
        }
        additionalMarble = lista.get(count);
    }

    private void slideRow(int row) {
        Marble oldAdditionalMurble = additionalMarble;
        additionalMarble = marketMatrix[row][0]; //the murble in the left position of the row will be the next additionalmurble
        marketMatrix[row][0] = marketMatrix[row][1]; //slide to left
        marketMatrix[row][1] = marketMatrix[row][2]; //slide to left
        marketMatrix[row][2] = marketMatrix[row][3]; //slide to left
        marketMatrix[row][3] = oldAdditionalMurble; //the old additional murble will be the murble in the right position of the row
    }

    private void slideColumn(int column) {
        Marble oldAdditionalMurble = additionalMarble;
        additionalMarble = marketMatrix[0][column]; //the murble in the top position of the column will be the next additionalmurble
        marketMatrix[0][column] = marketMatrix[1][column]; //slide top
        marketMatrix[1][column] = marketMatrix[2][column]; //slide top
        marketMatrix[2][column] = oldAdditionalMurble;  //the old additional murble will be the murble in the bottom position of the column
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        Arrays.toString(marketMatrix);
        return "Market Board: \n" +
                Arrays.toString(marketMatrix[0]) + "\n" +
                Arrays.toString(marketMatrix[1]) + "\n" +
                Arrays.toString(marketMatrix[2]) + "\n" +
                "\nFree Marble: " + additionalMarble.toString();
    }
}
