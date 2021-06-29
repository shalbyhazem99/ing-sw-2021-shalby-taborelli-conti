package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.model.developmentCard.DevelopmentCard;
import it.polimi.ingsw.model.leaderCard.*;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Class to read data from file
 */
public class FileReader {

    /**
     * Read all the development cards
     * @return a matrix of {@link Stack<DevelopmentCard>} with all 64 cards
     */
    public static Stack<DevelopmentCard>[][] readDevelopmentCards() {
        Stack<DevelopmentCard>[][] stacks = new Stack[Utils.DEV_CARD_ROW_NUMBER][Utils.DEV_CARD_COL_NUMBER];
        //stacks initialization
        for (int row = 0; row < Utils.DEV_CARD_ROW_NUMBER; row++) {
            for (int column = 0; column < Utils.DEV_CARD_COL_NUMBER; column++) {
                stacks[row][column] = new Stack<>();
            }
        }
        Gson gson = new Gson();
        String filePath = new File("JSON/DevelopmentCard/DevelopmentCard.json").getAbsolutePath();
        try (Reader reader = new java.io.FileReader(filePath)) {

            // Convert JSON File to Java Object
            DevelopmentCard[] array = gson.fromJson(reader, (DevelopmentCard[].class));
            ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();
            Collections.addAll(developmentCards, array);
            Collections.shuffle(developmentCards);
            for (DevelopmentCard developmentCard : developmentCards) {
                stacks[developmentCard.getLevel().label][developmentCard.getType().label].push(developmentCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stacks;
    }

    /**
     * Read all the leader cards
     * @return a {@link Stack<LeaderCard>} with all 16 cards
     */
    public static Stack<LeaderCard> readLeaderCard() {
        Stack<LeaderCard> stacks = new Stack<>();
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        Gson gson = new Gson();
        Map<String, Type> fileSet = new HashMap<>();
        fileSet.put(new File("JSON/LeaderCard/LeaderCard_AddProductive.json").getAbsolutePath(), LeaderCardAddProductive[].class);
        fileSet.put(new File("JSON/LeaderCard/LeaderCard_AddWarehouse.json").getAbsolutePath(), LeaderCardAddWarehouse[].class);
        fileSet.put(new File("JSON/LeaderCard/LeaderCard_Color.json").getAbsolutePath(), LeaderCardColor[].class);
        fileSet.put(new File("JSON/LeaderCard/LeaderCard_Discount.json").getAbsolutePath(), LeaderCardDiscount[].class);
        for (Map.Entry<String, Type> entry : fileSet.entrySet()) {
            try (Reader reader = new java.io.FileReader(entry.getKey())) {
                //read data from file;
                LeaderCard[] array= gson.fromJson(reader, entry.getValue());
                Collections.addAll(leaderCards, array);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(leaderCards);
        stacks.addAll(leaderCards);
        return stacks;
    }


    //todo: is it useful?
    public static void main(String[] args) {
        FileReader.readLeaderCard();
    }


}
