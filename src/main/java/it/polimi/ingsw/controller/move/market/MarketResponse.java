package it.polimi.ingsw.controller.move.market;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.resourcePositioning.PositioningResourcesPlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.market.MoveType;

import java.util.ArrayList;
import java.util.Scanner;

public class MarketResponse extends MoveResponse {
    /**
     * Class used to represent the response of the system when the {@link it.polimi.ingsw.model.Player} interacts with the {@link it.polimi.ingsw.model.market.MarketBoard}
     * This class can represent 2 different responses based on the kind of {@link it.polimi.ingsw.controller.move.PlayerMove}:
     * - When responding to a {@link MarketInteractionPlayerMove} the resources parameter will contain the {@link Resource} got by the conversion
     * (ignoring the white {@link it.polimi.ingsw.model.market.Marble}) and the numOfMarbleToBeCoverted will contain how many white {@link it.polimi.ingsw.model.market.Marble}
     * that were part of the row/column selected we can convert, numOfMarbleToBeCoverted >=0
     * (! If the {@link it.polimi.ingsw.model.Player}
     * - When responding to a {@link MarketMarbleConversionMove} the resources parameter will contain the {@link Resource} got by the conversion
     * of the white {@link it.polimi.ingsw.model.market.Marble} based on the {@link ArrayList} of {@link it.polimi.ingsw.model.ResourceType} previously
     * specified and the
     */
    private ArrayList<Resource> resources;
    int numOfMarbleToBeCoverted;
    private MoveType moveType;
    private int pos;
    private int first;
    private int second;

    /**
     * Default constructor
     *
     * @param resources               the {@link ArrayList} containing the {@link Resource} got by the conversion
     * @param numOfMarbleToBeCoverted the int which specifies how many Conversion Strategy the {@link it.polimi.ingsw.model.Player} has to communicate
     *                                to the system
     */
    public MarketResponse(ArrayList<Resource> resources, int numOfMarbleToBeCoverted, ArrayList<Player> players, int executePlayerPos, MoveType moveType, int pos) {
        super(players,executePlayerPos);
        this.resources = resources;
        this.numOfMarbleToBeCoverted = numOfMarbleToBeCoverted;
        this.moveType = moveType;
        this.pos = pos;
    }

    //when only convertion is done
    public MarketResponse(ArrayList<Resource> resources, int numOfMarbleToBeCoverted, ArrayList<Player> players, int executePlayerPos,int first, int second) {
        super(players,executePlayerPos);
        this.resources = resources;
        this.numOfMarbleToBeCoverted = numOfMarbleToBeCoverted;
        this.first = first;
        this.second = second;
    }

    /**
     * Default get instance method
     *
     * @param resources               the {@link ArrayList} containing the {@link Resource} got by the conversion
     * @param numOfMarbleToBeCoverted the int which specifies how many Conversion Strategy the {@link it.polimi.ingsw.model.Player} has to communicate
     *                                to the system
     * @return an instance of {@link MarketResponse}
     */
    public static MarketResponse getInstance(ArrayList<Resource> resources, int numOfMarbleToBeCoverted, ArrayList<Player> players, int executePlayerPos, MoveType moveType, int pos) {
        return new MarketResponse(resources,numOfMarbleToBeCoverted,players,executePlayerPos,moveType,pos);
    }

    /**
     * Default get instance method
     *
     * @param resources               the {@link ArrayList} containing the {@link Resource} got by the conversion
     * @param numOfMarbleToBeCoverted the int which specifies how many Conversion Strategy the {@link it.polimi.ingsw.model.Player} has to communicate
     *                                to the system
     * @return an instance of {@link MarketResponse}
     */
    public static MarketResponse getInstance(ArrayList<Resource> resources, int numOfMarbleToBeCoverted, ArrayList<Player> players, int executePlayerPos,int first, int second) {
        return new MarketResponse(resources,numOfMarbleToBeCoverted,players,executePlayerPos,first,second);
    }

    @Override
    public PlayerMove elaborateCliInput(Scanner stdin, Match match) {
        System.out.print("Resource gained: ");
        resources.stream().forEach(elem -> System.out.print(elem.getType().toString() + " "));
        System.out.println("\n");
        if (numOfMarbleToBeCoverted == 0) {
            ArrayList<Integer> whereToPlaceResources = new ArrayList<>();
            for (int i = 0; i < resources.size(); i++) {
                Resource resource = resources.get(i);
                if (!resource.getType().equals(ResourceType.FAITH)) {
                    System.out.println("Where do you want to add the " + resource.getType() + " resource (0,1,2,3,4 or 6 to discard)");
                    int chosen = stdin.nextInt();
                    if(chosen>=6) {
                        whereToPlaceResources.add(null); // will be discard in match
                    }else {
                        whereToPlaceResources.add(chosen);
                    }
                }
            }

            try {
                return PositioningResourcesPlayerMove.getInstance(whereToPlaceResources);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You have "+ numOfMarbleToBeCoverted+" white marble to be converted");
            System.out.println("How many using First conversion strategy");
            int first = stdin.nextInt();
            System.out.println("How many using Second conversion strategy");
            int second = stdin.nextInt();
            return MarketMarbleConversionMove.getInstance(first,second);
        }
        return null;

    }
}
