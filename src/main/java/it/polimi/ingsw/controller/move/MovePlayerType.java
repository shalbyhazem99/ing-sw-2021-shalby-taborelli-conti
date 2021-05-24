package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.controller.move.leaderCard.DiscardLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.EnableLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.*;
import it.polimi.ingsw.controller.move.moveResources.MoveResourcesPlayerMove;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.model.resource.ResourceType;
import it.polimi.ingsw.model.resource.ResourcesCount;
import it.polimi.ingsw.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
//player è sempre in posizione 0, quyello che fa la mosssa.

//todo: modificare elaborate for cli

public enum MovePlayerType {
    BUY_DEVELOPMENT_CARD("Buy Development Card") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            BuyDevelopmentCardPlayerMove developmentCardPlayerMove = null;
            try {
                //type
                DevelopmentCardType[] developmentCardTypes = DevelopmentCardType.values();
                int row;
                boolean parameters_valid;
                do {
                    System.out.print("insert development card type ( ");
                    Arrays.stream(developmentCardTypes).forEach(elem -> System.out.print(elem.label + "->" + elem + " "));
                    System.out.println(" )"+Utils.DISABLE_MOVE);
                    parameters_valid = true;
                    row = stdin.nextInt();
                    if(row==-1)
                    {
                        return null;
                    }
                    if(row<0||row>3)
                    {
                        parameters_valid = false;
                        System.err.println("Error insert valid parameters!");
                    }
                } while (!parameters_valid);
                DevelopmentCardType type = developmentCardTypes[row];
                //level
                DevelopmentCardLevel[] developmentCardLevels = DevelopmentCardLevel.values();
                int column;
                do {
                    System.out.print("insert development card level ( ");
                    Arrays.stream(developmentCardLevels).forEach(elem -> System.out.print(elem.label + "->" + elem + " "));
                    System.out.println(" )"+Utils.DISABLE_MOVE);
                    parameters_valid = true;
                    column = stdin.nextInt();
                    if(column==-1)
                    {
                        return null;
                    }
                    if(column<0||column>2)
                    {
                        parameters_valid = false;
                        System.err.println("Error insert valid parameters!");
                    }
                } while (!parameters_valid);
                DevelopmentCardLevel level = developmentCardLevels[column];
                //pos
                int pos;
                do {
                    System.out.println("insert development card position( 0, 1, 2 )"+Utils.DISABLE_MOVE);
                    parameters_valid = true;
                    pos = stdin.nextInt();
                    if(pos==-1)
                    {
                        return null;
                    }
                    if(pos<0||pos>2)
                    {
                        parameters_valid = false;
                        System.err.println("Error insert valid parameters!");
                    }
                } while (!parameters_valid);
                //choose where ro get resources
                ArrayList<ResourcePick> resourceToUse=Utils.getRequiredResourceFrom(match.getDevelopmentCardOnTop(type,level).getCosts(match.getCurrentPlayer()),stdin,match);
                developmentCardPlayerMove = BuyDevelopmentCardPlayerMove.getInstance(type, level, pos, resourceToUse);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return developmentCardPlayerMove;
        }
    },
    END_TURN("End Turn") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            return EndRoundPlayerMove.getInstance();
        }
    },
    ENABLE_LEADER_CARD("Enable Leader Card") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            EnableLeaderCardPlayerMove enableLeaderCardPlayerMove = null;
            try {
                //position
                Player player = match.getCurrentPlayer();
                int position = 0;
                boolean parameters_valid = true;
                do {
                    int num_of_leader_card_can_be_enabled = (int)player.getLeaderCards().stream().filter(p->!p.isActive()).count();
                    if(num_of_leader_card_can_be_enabled==0)
                    {
                        System.err.println("No leader cards that can be enabled left!");
                        return null; //another move will be asked
                    }
                    else if(num_of_leader_card_can_be_enabled==1)
                    {
                        System.out.print("Automatically selected the leader card which was not active yet");
                        for(int i = 0;i<player.getLeaderCards().size();i++)
                        {
                            if(!player.getLeaderCards().get(i).isActive())
                            {
                                position = i;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Insert the pos of the leader card to enable (0,1)"+Utils.DISABLE_MOVE);
                        position = stdin.nextInt();
                        if(position==-1)
                        {
                            return null;
                        }
                        if(position<0 || position>1)
                        {
                            parameters_valid = false;
                        }
                    }
                }while(!parameters_valid);

                enableLeaderCardPlayerMove = EnableLeaderCardPlayerMove.getInstance(position);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return enableLeaderCardPlayerMove;
        }
    },
    DISCARD_LEADER_CARD("Discard Leader Card") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            DiscardLeaderCardPlayerMove discardLeaderCardPlayerMove = null;
            try {
                //position
                Player player = match.getCurrentPlayer();
                int position = 0;
                boolean parameters_valid = true;
                do {
                    int num_of_leader_card_enabled = (int)player.getLeaderCards().stream().filter(p-> !p.isActive()).count();
                    if(num_of_leader_card_enabled==0)
                    {
                        System.err.println("All your leader cards are enabled, you can't discard them!");
                        return null; //another move will be asked
                    }
                    else if(num_of_leader_card_enabled==1)
                    {
                        System.out.print("Automatically selected the leader card which was not active yet");
                        for(int i = 0;i<player.getLeaderCards().size();i++)
                        {
                            if(!player.getLeaderCards().get(i).isActive())
                            {
                                position = i;
                            }
                        }
                    }
                    else
                    {
                        System.out.print("Insert the pos of the leader card to discard (0,1)"+Utils.DISABLE_MOVE);
                        position = stdin.nextInt();
                        if(position==-1)
                        {
                            return null;
                        }
                        if(position<0 || position>1)
                        {
                            parameters_valid = false;
                        }
                    }
                }while(!parameters_valid);
                discardLeaderCardPlayerMove = DiscardLeaderCardPlayerMove.getInstance(position);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return discardLeaderCardPlayerMove;
        }
    },
    MARKET_INTERACTION("Market Interaction") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            MarketInteractionPlayerMove marketInteractionPlayerMove = null;
            try {
                //type
                MoveType[] moveTypes = MoveType.values();
                int move;
                boolean parameters_valid;
                do{
                parameters_valid = true;
                System.out.print("insert interaction type ( ");
                for (int i = 0; i < moveTypes.length; i++) {
                    System.out.print(i + "->" + moveTypes[i] + " ");
                }
                System.out.println(" )"+Utils.DISABLE_MOVE);
                move = stdin.nextInt();
                if(move==-1) {
                    return null;
                }
                if(move!=0 && move!=1)
                {
                    System.err.println("Error, insert a valid parameter!");
                    parameters_valid = false;
                }
                }while(!parameters_valid);
                MoveType type = moveTypes[move];
                int pos = 0;
                //pos
                do {
                    parameters_valid = true;
                    if (move == 0) //ROW
                    {
                        System.out.println("insert ROW position(0,1,2)"+Utils.DISABLE_MOVE);
                        pos = stdin.nextInt();
                        if(pos==-1)
                        {
                            return null;
                        }
                        if(pos<0||pos>2)
                        {
                            System.err.println("Error, insert a valid parameter!");
                            parameters_valid = false;
                        }

                    } else //COLUMN
                    {
                        System.out.println("insert COLUMN position(0,1,2,3)"+Utils.DISABLE_MOVE);
                        pos = stdin.nextInt();
                        if(pos==-1)
                        {
                            return null;
                        }
                        if(pos<0||pos>3)
                        {
                            System.err.println("Error, insert a valid parameter!");
                            parameters_valid = false;
                        }

                    }
                }while (!parameters_valid);
                marketInteractionPlayerMove = MarketInteractionPlayerMove.getInstance(type, pos);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return marketInteractionPlayerMove;
        }
    },
    ENABLE_PRODUCTION("Enable A Production") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            EnableProductionPlayerMove enableProductionPlayerMove = null;
            try {
                //pos
                boolean parameters_valid;
                do
                {parameters_valid=true;
                System.out.println("insert which type of power you want to active( 0-> base production [ 2 A ▶ 1A ], 1-> Leader Card, 2-> Development Card)"+Utils.DISABLE_MOVE);
                int type = stdin.nextInt();
                ArrayList<ResourcePick> resourceToUse;
                int index;
                ResourceType[] resourceTypes = Utils.getUsableResourcesType();
                switch (type) {
                    case 0: //base production from two get one
                        resourceToUse = Utils.getRequiredResourceFrom(new ArrayList<>(Arrays.asList(ResourcesCount.getInstance(2, ResourceType.ANY))), stdin,match);
                        System.out.print("insert interaction type ( ");
                        for (int i = 0; i < resourceTypes.length; i++) {
                            System.out.print(i + "->" +  Utils.resourceTypeToString(resourceTypes[i]) + " ");
                        }
                        System.out.println(" )");
                        index = stdin.nextInt();
                        enableProductionPlayerMove = EnableProductionPlayerMoveBase.getInstance(resourceToUse, resourceTypes[index]);
                        break;
                    case 1: //Leader Card
                        System.out.println("insert the index of the Leader Card power to activate (0,...)");
                        index = stdin.nextInt();
                        int type_index;
                        resourceToUse = Utils.getRequiredResourceFrom(match.getCurrentPlayer().getAddedPower().get(index).getFrom(), stdin,match);
                        System.out.print("insert type ( ");
                        for (int i = 0; i < resourceTypes.length; i++) {
                            System.out.print(i + "->" +  Utils.resourceTypeToString(resourceTypes[i]) + " ");
                        }
                        System.out.println(" )");
                        type_index = stdin.nextInt();
                        if(type_index<0||type_index>=resourceTypes.length)
                        {
                            parameters_valid = false;
                            break;
                        }
                        enableProductionPlayerMove = EnableProductionPlayerMoveLeaderCard.getInstance(resourceToUse, index,resourceTypes[type_index]);
                        break;
                    case 2: //Development Card
                        System.out.println("insert the index of the Development Card power to activate (0,1,2)");
                        index = stdin.nextInt();
                        resourceToUse = Utils.getRequiredResourceFrom(match.getCurrentPlayer().getDevelopmentCardSpaces().get(index).pickTopCard().getPowers().getFrom(), stdin,match);
                        enableProductionPlayerMove = EnableProductionPlayerMoveDevelopmentCard.getInstance(resourceToUse, index);
                        break;
                    case -1:
                        return null;
                    default:
                        System.err.println("Error, insert correct parameter!");
                        parameters_valid = false;
                        break;
                }
                }while (!parameters_valid);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return enableProductionPlayerMove;
        }
    },
    MOVE_RESOURCES("Move Resources") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            MoveResourcesPlayerMove moveResourcesPlayerMove = null;
            boolean both_std_warehouse = true;
            try {
                //position
                Player player = match.getCurrentPlayer();
                int first,second;
                int how_many_first = 0,how_many_second = 0;
                Warehouse first_w = null,second_w = null;
                boolean parameters_valid = true;
                do {
                System.out.println("insert the pos of the first warehouse (0...)"+Utils.DISABLE_MOVE);
                first = stdin.nextInt();
                if(first==-1)
                {
                    return null;
                }
                System.out.println("insert the pos of the second warehouse (0...)"+Utils.DISABLE_MOVE);
                second = stdin.nextInt();
                if(second==-1)
                {
                    return null;
                }
                //Controlling parameters
                /*
                CHECK:
                1) Parameters € [0,4]
                2) If we're trying to swap the same Warehouse return immediately, no action must be taken
                3) If the two indexes refer to additional warehouses we need to check if they exist
         */
                //1)
                if(first<0||first>4||second<0||second>4)
                {
                    System.err.println("Error, indexes must be one of these values: 0,1,2,3,4 !");
                    System.out.println("Re-insert the parameters");
                    parameters_valid = false;
                }
                //2)
                if (first == second) {
                    System.err.println("Error, warehouses' indexes must be different!");
                    System.out.println("Re-insert the parameters");
                    parameters_valid = false;
                }
                //3)
                if (first == 3 || second == 3) //check if the first additional warehouse exists
                {
                    if (player.getWarehousesAdditional().size() == 0) {
                        System.err.println("Error, you haven't any additional warehouse!");
                        System.out.println("Re-insert the parameters");
                        parameters_valid = false;
                    }
                }
                if (first == 4 || second == 4) //check if the second additional warehouse exists
                {
                    if (player.getWarehousesAdditional().size() < 2) {
                        System.err.println("Error, you haven't two additional warehouses!");
                        System.out.println("Re-insert the parameters");
                        parameters_valid = false;
                    }
                }
                if(first>2 || second>2)
                {
                    both_std_warehouse = false;
                }
                if(parameters_valid)
                {
                    if(first>2)//ADDITIONAL
                    {
                        first_w = player.getWarehousesAdditional().get(first-3);
                    }
                    else//STD
                    {
                        first_w = player.getWarehousesStandard().get(first);
                    }
                    if(second>2)//ADDITIONAL
                    {
                        second_w = player.getWarehousesAdditional().get(second-3);
                    }
                    else//STD
                    {
                        second_w = player.getWarehousesStandard().get(second);
                    }
                }
                if(parameters_valid &&!both_std_warehouse) //if both warehouses are standard we don't need to ask for how many resources to move
                {
                    System.out.println("How many " + Utils.resourceTypeToString(first_w.getResourceType()) + " from first warehouse?" +Utils.DISABLE_MOVE);
                    how_many_first = stdin.nextInt();
                    if(how_many_first==-1){return null;}
                    if(how_many_first>first_w.getResources().size()) //we're trying to get more resources than we have
                    {
                        System.err.println("Error, you're trying to get too many resources");
                        parameters_valid = false;
                    }
                }
                if(parameters_valid&&!both_std_warehouse)
                {
                    System.out.println("How many " + Utils.resourceTypeToString(second_w.getResourceType()) + " from second warehouse?"+Utils.DISABLE_MOVE);
                    how_many_second = stdin.nextInt();
                    if(how_many_second==-1){return null;}
                    if(how_many_second>second_w.getResources().size()) //we're trying to get more resources than we have
                    {
                        System.err.println("Error, you're trying to get too many resources");
                        parameters_valid = false;
                    }
                    if(second==0 && first==0)
                    {
                        System.err.println("Error, useless move");
                        parameters_valid = false;
                    }
                }
                //Check dimension correctness
                if(both_std_warehouse)
                {
                    //se non sono compatibili le dimensioni
                    if((first_w.getSpaceAvailable()<second_w.getResources().size())||(second_w.getSpaceAvailable()<first_w.getResources().size()))
                    {
                        System.err.println("Error dimensions are not compatible");
                    }
                }
                }while (!parameters_valid);
                //End controlling parameters
                moveResourcesPlayerMove = MoveResourcesPlayerMove.getInstance(first, second,how_many_first,how_many_second);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return moveResourcesPlayerMove;
        }
    };

    private String description;

    public String getDescription() {
        return description;
    }

    MovePlayerType(String description) {
        this.description = description;
    }

    public abstract PlayerMove elaborateMoveForCLI(Scanner stdin, Match match);
}
