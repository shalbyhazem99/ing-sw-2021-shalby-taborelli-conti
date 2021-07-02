package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.leaderCard.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to read data from file
 */
public class FileReader {

    /**
     * Read all the development cards
     *
     * @return a matrix of {@link Stack<DevelopmentCard>} with all 64 cards
     */
    public Stack<DevelopmentCard>[][] readDevelopmentCards() {
        Stack<DevelopmentCard>[][] stacks = new Stack[Utils.DEV_CARD_ROW_NUMBER][Utils.DEV_CARD_COL_NUMBER];
        //stacks initialization
        for (int row = 0; row < Utils.DEV_CARD_ROW_NUMBER; row++) {
            for (int column = 0; column < Utils.DEV_CARD_COL_NUMBER; column++) {
                stacks[row][column] = new Stack<>();
            }
        }
        Gson gson = new Gson();
        String filePath = "/json/DevelopmentCard/DevelopmentCard.json";
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String contents = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            // Convert JSON File to Java Object
            DevelopmentCard[] array = gson.fromJson(contents, (DevelopmentCard[].class));
            ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
            Collections.addAll(developmentCards, array);
            Collections.shuffle(developmentCards);
            for (DevelopmentCard developmentCard : developmentCards) {
                stacks[developmentCard.getLevel().label][developmentCard.getType().label].push(developmentCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stacks;
    }

    /**
     * Read all the leader cards
     *
     * @return a {@link Stack<LeaderCard>} with all 16 cards
     */
    public Stack<LeaderCard> readLeaderCard() {
        Stack<LeaderCard> stacks = new Stack<>();
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        Gson gson = new Gson();
        Map<String, Type> fileSet = new HashMap<>();

        fileSet.put("/json/LeaderCard/LeaderCard_AddProductive.json",LeaderCardAddProductive[].class);
        fileSet.put("/json/LeaderCard/LeaderCard_AddWarehouse.json",LeaderCardAddWarehouse[].class);
        fileSet.put("/json/LeaderCard/LeaderCard_Color.json",LeaderCardColor[].class);
        fileSet.put("/json/LeaderCard/LeaderCard_Discount.json",LeaderCardDiscount[].class);


        for (Map.Entry<String, Type> entry : fileSet.entrySet()) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(entry.getKey());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String contents = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                LeaderCard[] array = gson.fromJson(contents, entry.getValue());
                Collections.addAll(leaderCards, array);
            } catch (Exception e) {

            }
        }
        Collections.shuffle(leaderCards);
        stacks.addAll(leaderCards);
        return stacks;
    }
}
