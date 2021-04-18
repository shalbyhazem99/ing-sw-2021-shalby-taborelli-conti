package it.polimi.ingsw.controller.move;

import it.polimi.ingsw.controller.move.LeaderCard.EnableLeaderCardPlayerMove;
import it.polimi.ingsw.controller.move.development.BuyDevelopmentCardPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundPlayerMove;
import it.polimi.ingsw.controller.move.endRound.EndRoundResponse;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.leaderCard.LeaderCard;
import it.polimi.ingsw.model.market.MoveType;

import java.util.Arrays;
import java.util.Scanner;

public enum MovePlayerType {
    BUY_DEVELOPMENT_CARD("Buy development card") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin) {
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

                developmentCardPlayerMove = BuyDevelopmentCardPlayerMove.getInstance(type, level, pos);
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin);
            }
            return developmentCardPlayerMove;
        }
    },
    MARKET_INTERACTION("Market interaction") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin) {
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
                elaborateMoveForCLI(stdin);
            }
            return marketInteractionPlayerMove;
        }
    },
    ENABLE_LEADER_CARD("Enable Leader Card") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin) {
            EnableLeaderCardPlayerMove enableLeaderCardPlayerMove = null;
            try {
                //position
                System.out.print("insert the pos of the leader card to enable (0...)");
                enableLeaderCardPlayerMove = EnableLeaderCardPlayerMove.getInstance(stdin.nextInt());
            } catch (Exception e) {
                System.out.println("Error retry:");
                elaborateMoveForCLI(stdin);
            }
            return enableLeaderCardPlayerMove;
        }
    },
    END_TURN("End turn") {
        @Override
        public PlayerMove elaborateMoveForCLI(Scanner stdin) {
          return EndRoundPlayerMove.getInstance();
        }
    };


    private String description;

    public String getDescription() {
        return description;
    }

    MovePlayerType(String description) {
        this.description = description;
    }

    public abstract PlayerMove elaborateMoveForCLI(Scanner stdin);
}
