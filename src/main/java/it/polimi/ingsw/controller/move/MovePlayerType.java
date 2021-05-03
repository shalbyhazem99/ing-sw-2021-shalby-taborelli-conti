package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.controller.move.LeaderCard.DiscardLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.LeaderCard.EnableLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.production.move.*;
import it.polimi.ingsw.controller.move.swapWarehouse.SwapWarehousePlayerMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ResourceType;
import it.polimi.ingsw.model.ResourcesCount;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public enum MovePlayerType {
    BUY_DEVELOPMENT_CARD("Buy Development Card") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            BuyDevelopmentCardPlayerMove developmentCardPlayerMove = null;
            try {
                //type
                DevelopmentCardType[] developmentCardTypes = DevelopmentCardType.values();
                System.out.print("insert development card type ( ");
                Arrays.stream(developmentCardTypes).forEach(elem -> System.out.print(elem.label + "->" + elem + " "));
                System.out.println(" )");
                DevelopmentCardType type = developmentCardTypes[stdin.nextInt()];

                //level
                DevelopmentCardLevel[] developmentCardLevels = DevelopmentCardLevel.values();
                System.out.print("insert development card level ( ");
                Arrays.stream(developmentCardLevels).forEach(elem -> System.out.print(elem.label + "->" + elem + " "));
                System.out.println(" )");
                DevelopmentCardLevel level = developmentCardLevels[stdin.nextInt()];
                //pos
                System.out.println("insert development card position( 0, 1, 2 )");
                int pos = stdin.nextInt();
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
                System.out.print("insert the pos of the leader card to enable (0,1)");
                enableLeaderCardPlayerMove = EnableLeaderCardPlayerMove.getInstance(stdin.nextInt());
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
                System.out.print("insert the pos of the leader card to discard (0,1)");
                discardLeaderCardPlayerMove = DiscardLeaderCardPlayerMove.getInstance(stdin.nextInt());
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
                System.out.print("insert interaction type ( ");
                for (int i = 0; i < moveTypes.length; i++) {
                    System.out.print(i + "->" + moveTypes[i] + " ");
                }
                System.out.println(" )");
                MoveType type = moveTypes[stdin.nextInt()];

                //pos
                System.out.println("insert column/row position( 0,...)");
                int pos = stdin.nextInt();

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
                }
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return enableProductionPlayerMove;
        }
    },
    SWAP_WAREHOUSE("Swap Warehouse") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin, Match match) {
            SwapWarehousePlayerMove swapWarehousePlayerMove = null;
            try {
                //position
                System.out.println("insert the pos of the first warehouse to swap (0...)");
                int first = stdin.nextByte();
                System.out.println("insert the pos of the second warehouse to swap (0...)");
                swapWarehousePlayerMove = SwapWarehousePlayerMove.getInstance(first, stdin.nextInt());
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin, match);
            }
            return swapWarehousePlayerMove;
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
