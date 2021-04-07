package it.polimi.ingsw;

import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;

import java.util.Arrays;

public class prove {
    public static void main(String[] args) {
        DevelopmentCardType[] d= DevelopmentCardType.values();
        Arrays.stream(d).forEach(elem->System.out.print(elem.label+"->"+elem+" "));
    }
}
