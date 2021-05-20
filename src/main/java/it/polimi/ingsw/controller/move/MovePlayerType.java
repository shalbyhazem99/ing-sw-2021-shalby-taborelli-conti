package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.controller.move.leaderCard.DiscardLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.leaderCard.EnableLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.*;
import it.polimi.ingsw.controller.move.moveResources.MoveResourcesPlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
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
                    System.out.println(" )");
                    parameters_valid = true;
                    row = stdin.nextInt();
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
                    System.out.println(" )");
                    parameters_valid = true;
                    column = stdin.nextInt();
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
                    System.out.println("insert development card position( 0, 1, 2 )");
                    parameters_valid = true;
                    pos = stdin.nextInt();
                    if(pos<0||pos>2)
                    {
                        parameters_valid = false;
                        System.err.println("Error insert valid parameters!");
                    }
                } while (!parameters_valid);
                //choose where ro get resources
                ArrayList<ResourcePick> resourceToUse=Utils.getRequiredResourceFrom(match.getDevelopmentCardOnTop(type,level).getCosts(),stdin,match);
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
                        System.out.print("Insert the pos of the leader card to enable (0,1)");
                        position = stdin.nextInt();
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
                    int num_of_leader_card_enabled = (int)player.getLeaderCards().stream().filter(p->p.isActive()).count();
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
                        System.out.print("Insert the pos of the leader card to discard (0,1)");
                        position = stdin.nextInt();
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
                System.out.println(" )");
                move = stdin.nextInt();
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
                        System.out.println("insert ROW position(0,1,2)");
                        pos = stdin.nextInt();
                        if(pos<0||pos>2)
                        {
                            System.err.println("Error, insert a valid parameter!");
                            parameters_valid = false;
                        }
                    } else //COLUMN
                    {
                        System.out.println("insert COLUMN position(0,1,2,3)");
                        pos = stdin.nextInt();
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
                System.out.println("insert which type of power you want to active( 0-> base production, 1-> Leader Card, 2-> Development Card)");
                int type = stdin.nextInt();
                ArrayList<ResourcePick> resourceToUse;
                int index;
                switch (type) {
                    case 0: //base production from two get one
                        resourceToUse = Utils.getRequiredResourceFrom((ArrayList<ResourcesCount>) Arrays.asList(ResourcesCount.getInstance(2, ResourceType.ANY)), stdin,match);
                        ResourceType[] resourceTypes = ResourceType.values();
                        System.out.print("insert interaction type ( ");
                        for (int i = 0; i < resourceTypes.length; i++) {
                            System.out.print(i + "->" + resourceTypes[i] + " ");
                        }
                        index = stdin.nextInt();
                        enableProductionPlayerMove = EnableProductionPlayerMoveBase.getInstance(resourceToUse, resourceTypes[index]);
                        break;
                    case 1: //Leader Card
                        System.out.println("insert the index of the Leader Card power to activate (0,...)");
                        index = stdin.nextInt();
                        resourceToUse = Utils.getRequiredResourceFrom(match.getPlayers().get(0).getAddedPower().get(index).getFrom(), stdin,match);
                        enableProductionPlayerMove = EnableProductionPlayerMoveLeaderCard.getInstance(resourceToUse, index);
                        break;
                    case 2: //Development Card
                        System.out.println("insert the index of the Development Card power to activate (0,1,3)");
                        index = stdin.nextInt();
                        resourceToUse = Utils.getRequiredResourceFrom(match.getPlayers().get(0).getDevelopmentCardSpaces().get(index).pickTopCard().getCosts(), stdin,match);
                        enableProductionPlayerMove = EnableProductionPlayerMoveDevelopmentCard.getInstance(resourceToUse, index);
                        break;
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
            try {
                //position
                Player player = match.getCurrentPlayer();
                int first;
                int second;
                int countToMove;
                boolean parameters_valid = true;
                do {
                System.out.println("insert the pos of the first warehouse to swap (0...)");
                first = stdin.nextInt();
                System.out.println("insert the pos of the second warehouse to swap (0...)");
                second = stdin.nextInt();
                System.out.println("insert the number of resources to move from the first warehouse");
                countToMove = stdin.nextInt();
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
                if(countToMove<3){
                    parameters_valid = false;
                }
                }while (!parameters_valid);
                //End controlling parameters
                moveResourcesPlayerMove = MoveResourcesPlayerMove.getInstance(first, second,countToMove);
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
