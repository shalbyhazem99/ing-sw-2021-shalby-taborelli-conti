package it.polimi.ingsw.model.market;

import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.utils.Utils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class MarketBoardTest extends TestCase {
    public MarketBoardTest() {
    }

    public MarketBoardTest(String name) {
        super(name);
    }

    /**
     * Class used to test the {@link MarketBoard}
     */

    /**
     * Test for constructor
     * Check:
     * - Not exists any null element
     * - There is a correct number of marble for each color
     */
    @Test
    public void testMarketBoard()
    {
        MarketBoard marketBoard = new MarketBoard();
        for (Marble marble : linearizeStructure(marketBoard)) {
            assertNotNull(marble);
        }
        checkForNumberColor(linearizeStructure(marketBoard));
    }

    /**
     * Method used to test the generate {@link Resource} method selecting a Column
     * CHECK:
     * - Number of {@link Marble} for each {@link MarbleColor} after the method's call
     * - That the size of the {@link ArrayList} of {@link Resource} is equal to 3 - number of white {@link Marble}
     * - Correct "movement" of the {@link MarketBoard}'s {@link Marble}
     */
    @Test
    public void testGenerateResourcesColumn()
    {
        final int selectedColumn = 0;
        MarketBoard marketBoard = new MarketBoard();
        MarketBoard temp = marketBoard;
        ArrayList<Resource> resources = marketBoard.getResources(MoveType.COLUMN,selectedColumn);
        assertEquals(resources.size(), Utils.MARKET_ROW_NUMBER-Collections.frequency(temp.getColumn(selectedColumn),
                Marble.getInstance(MarbleColor.WHITE)));
        //CHECK ALL THE FOUR MARBLES
        assertEquals(temp.getColumn(selectedColumn).get(0),
                marketBoard.getColumn(selectedColumn).get(1));
        assertEquals(temp.getColumn(selectedColumn).get(1),
                marketBoard.getColumn(selectedColumn).get(2));
        assertEquals(temp.getColumn(selectedColumn).get(2),
                marketBoard.getAdditionalMarble());
        assertEquals(temp.getAdditionalMarble(),
                marketBoard.getColumn(selectedColumn).get(0));
        //CHECK FOR MARBLE COLOUR NUMBER
        checkForNumberColor(linearizeStructure(marketBoard));
    }

    /**
     * Method used to test the generate {@link Resource} method selecting a Row
     * CHECK:
     * - Number of {@link Marble} for each {@link MarbleColor} after the method's call
     * - That the size of the {@link ArrayList} of {@link Resource} is equal to 4 - number of white {@link Marble}
     * - Correct "movement" of the {@link MarketBoard}'s {@link Marble}
     */
    @Test
    public void testGenerateResourcesRow()
    {
        final int selectedRow = 0;
        MarketBoard marketBoard = new MarketBoard();
        MarketBoard temp = marketBoard;
        ArrayList<Resource> resources = marketBoard.getResources(MoveType.COLUMN,selectedRow);
        assertEquals(resources.size(), Utils.MARKET_COL_NUMBER-Collections.frequency(temp.getColumn(selectedRow),
                Marble.getInstance(MarbleColor.WHITE)));
        //CHECK ALL THE FIVE MARBLES
        assertEquals(temp.getRow(selectedRow).get(0),
                marketBoard.getColumn(selectedRow).get(1));
        assertEquals(temp.getRow(selectedRow).get(1),
                marketBoard.getColumn(selectedRow).get(2));
        assertEquals(temp.getRow(selectedRow).get(2),
                marketBoard.getColumn(selectedRow).get(3));
        assertEquals(temp.getRow(selectedRow).get(3),
                marketBoard.getAdditionalMarble());
        assertEquals(temp.getAdditionalMarble(),
                marketBoard.getColumn(selectedRow).get(0));

        //CHECK FOR MARBLE COLOUR NUMBER
        checkForNumberColor(linearizeStructure(marketBoard));
    }

    /**
     * Method to check that the number of {@link Resource} returned are 0 for an invalid index
     */
    public void testGenerateResourcesFail()
    {
        MarketBoard marketBoard = new MarketBoard();
        assertEquals(marketBoard.getResources(MoveType.COLUMN,5).size(),0);
    }

    //TODO: THE INDICATIONS SAY THAT IT'S NOT SAFE TO CALL A TEST FROM ANOTHER TEST
    /**
     * Support method to check if there is a correct number of marble for each color
     */
    @Test
    private void checkForNumberColor(ArrayList<Marble> elements)
    {
        assertEquals(4,Collections.frequency(elements,Marble.getInstance(MarbleColor.WHITE)));
        assertEquals(2,Collections.frequency(elements,Marble.getInstance(MarbleColor.BLUE)));
        assertEquals(2,Collections.frequency(elements,Marble.getInstance(MarbleColor.GREY)));
        assertEquals(2,Collections.frequency(elements,Marble.getInstance(MarbleColor.YELLOW)));
        assertEquals(2,Collections.frequency(elements,Marble.getInstance(MarbleColor.PURPLE)));
        assertEquals(1,Collections.frequency(elements,Marble.getInstance(MarbleColor.RED)));
    }

    /**
     * Support method to convert a {@link MarketBoard} of {@link Marble} to a {@link ArrayList} of {@link Marble}
     * @param marketBoard the {@link MarketBoard} to linearize
     * @return an {@link ArrayList} containing all the {@link Marble} stored inside it
     */
    private ArrayList<Marble> linearizeStructure(MarketBoard marketBoard)
    {
        return (ArrayList<Marble>)Stream.concat(
                Arrays.stream(marketBoard.getMarketMatrix()),
                Arrays.asList(marketBoard.getAdditionalMarble()).stream()
        );
    }
}