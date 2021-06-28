package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.resource.Resource;
import it.polimi.ingsw.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class that represent the common Market Board
 */
public class MarketBoard implements Serializable, Cloneable {
    private final Marble[][] marketMatrix; //[row][column]
    private Marble additionalMarble;

    /**
     * Default Constructor that generate the Market board in a random way
     */
    public MarketBoard() {
        marketMatrix = new Marble[Utils.MARKET_ROW_NUMBER][Utils.MARKET_COL_NUMBER];
        generateMarblesMatrix();
    }

    /**
     * Constructor that generate the Market board based on an existing structure
     *
     * @param matrix     The [MARKET_ROW_NUMBER][MARKET_COL_NUMBER] MATRIX
     * @param additional The Additional {@link Marble} used to play in the {@link MarketBoard}
     */
    public MarketBoard(Marble[][] matrix, Marble additional) {
        marketMatrix = matrix;
        additionalMarble = additional;
    }

    /**
     * Default Instance generator
     *
     * @return an instance of the {@link MarketBoard}
     */
    public static MarketBoard getInstance() {
        return new MarketBoard();
    }

    /**
     * Method to make a Move in the {@link MarketBoard}
     *
     * @param moveType The {@link MoveType} to move in the {@link MarketBoard}
     * @param pos      the column or the row to move
     * @return an {@link ArrayList} of the obtained {@link Resource}
     */
    public ArrayList<Resource> getResources(MoveType moveType, int pos) {
        if (moveType.equals(MoveType.ROW)) {
            if (pos < 0 || pos > 2) {
                return new ArrayList<>();
            }
        } else {
            if (pos < 0 || pos > 3) {
                return new ArrayList<>();
            }
        }
        ArrayList<Marble> temp;
        if (moveType == MoveType.ROW) {
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

    /**
     * Getter of a given column of the {@link MarketBoard}
     *
     * @param column the column to extract
     * @return an {@link ArrayList} of Marbles
     */
    public ArrayList<Marble> getColumn(int column) {
        ArrayList<Marble> temp = new ArrayList<>();
        for (int r = 0; r < Utils.MARKET_ROW_NUMBER; r++) {
            temp.add(marketMatrix[r][column]);
        }
        return temp;
    }

    /**
     * Getter of a given row of the {@link MarketBoard}
     *
     * @param row the column to extract
     * @return an {@link ArrayList} of Marbles
     */
    public ArrayList<Marble> getRow(int row) {
        return new ArrayList<>(Arrays.asList(marketMatrix[row]).subList(0, Utils.MARKET_COL_NUMBER));
    }

    /**
     * Extract a marble from the {@link MarketBoard}
     *
     * @param row row of the {@link MarketBoard}
     * @param col column of the {@link MarketBoard}
     * @return the {@link Marble} in the given row and column
     */
    public Marble getMarbleAt(int row, int col) {
        if ((row < 0 || row >= Utils.MARKET_ROW_NUMBER) || (col < 0 || col >= Utils.MARKET_COL_NUMBER)) {
            return null;
        }
        try {
            return (Marble) marketMatrix[row][col].clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return the {@link Marble} the is not in the marketBoard and it must be inserted
     */
    public Marble getAdditionalMarble() {
        return additionalMarble;
    }

    /**
     * Insert the {@link Marble} in the MarketBoard and then randomize them
     */
    private void generateMarblesMatrix() {
        ArrayList<Marble> list = new ArrayList<>();
        //Marbles : 4 white, 2 blue, 2 grey, 2 yellow, 2 purple, 1 red
        for (int i = 0; i < 4; i++)
            list.add(Marble.getInstance(MarbleColor.WHITE));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(MarbleColor.BLUE));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(MarbleColor.GREY));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(MarbleColor.YELLOW));
        for (int i = 0; i < 2; i++)
            list.add(Marble.getInstance(MarbleColor.PURPLE));
        for (int i = 0; i < 1; i++)
            list.add(Marble.getInstance(MarbleColor.RED));
        Collections.shuffle(list);
        //fill the matrix
        int count = 0;
        for (int row = 0; row < Utils.MARKET_ROW_NUMBER; row++) {
            for (int column = 0; column < Utils.MARKET_COL_NUMBER; column++) {
                marketMatrix[row][column] = list.get(count);
                count++;
            }
        }
        additionalMarble = list.get(count);
    }

    /**
     * Insert the additional Marble in the selected row and then shift all the {@link Marble} in the row
     * @param row where to insert the additional Marble
     */
    private void slideRow(int row) {
        Marble additionalMarble = this.additionalMarble;
        this.additionalMarble = marketMatrix[row][0]; //the marble in the left position of the row will be the next additionalMarble
        marketMatrix[row][0] = marketMatrix[row][1]; //slide to left
        marketMatrix[row][1] = marketMatrix[row][2]; //slide to left
        marketMatrix[row][2] = marketMatrix[row][3]; //slide to left
        marketMatrix[row][3] = additionalMarble; //the old additional marble will be the marble in the right position of the row
    }

    /**
     * nsert the additional Marble in the selected column and then shift all the {@link Marble} in the column
     * @param column where to insert the additional Marble
     */
    private void slideColumn(int column) {
        Marble additionalMarble = this.additionalMarble;
        this.additionalMarble = marketMatrix[0][column]; //the marble in the top position of the column will be the next additionalMarble
        marketMatrix[0][column] = marketMatrix[1][column]; //slide top
        marketMatrix[1][column] = marketMatrix[2][column]; //slide top
        marketMatrix[2][column] = additionalMarble;  //the old additional marble will be the marble in the bottom position of the column
    }

    @Override
    protected MarketBoard clone() throws CloneNotSupportedException {
        super.clone();
        Marble[][] matrix = new Marble[Utils.MARKET_ROW_NUMBER][Utils.MARKET_COL_NUMBER];
        for (int i = 0; i < Utils.MARKET_ROW_NUMBER; i++) {
            for (int j = 0; j < Utils.MARKET_COL_NUMBER; j++) {
                matrix[i][j] = getMarbleAt(i, j);
            }
        }
        Marble additional;
        try {
            additional = (Marble) getAdditionalMarble().clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new MarketBoard(matrix, additional);

    }

    @Override
    public String toString() {
        return "Market Board: \n" +
                Arrays.toString(marketMatrix[0]) + "\n" +
                Arrays.toString(marketMatrix[1]) + "\n" +
                Arrays.toString(marketMatrix[2]) + "\n" +
                "\nFree Marble: " + additionalMarble.toString();
    }
}