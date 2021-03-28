package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.model.developmentCard.*;

import java.util.ArrayList;

public class ObjectConverter {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int num1;
        int num2;
        int num3;

        ResourceType type1;
        ResourceType type2;
        ResourceType type3;

        ArrayList<ResourcesCount> costs = new ArrayList<ResourcesCount>();

        // I can set the values of the costs of the card
        num1 = 2;
        type1 = ResourceType.SHIELD;
        ResourcesCount Item1 = new ResourcesCount(num1, type1);
        costs.add(Item1);


        /*
        num2 = null;
        type2 = null;
        ResourcesCount Item2 = new ResourcesCount(num2, type2);
        costs.add(Item2);


        num3 = null;
        type3 = null;
        ResourcesCount Item3 = new ResourcesCount(num3, type3);
        costs.add(Item3);*/

        // I set the values of the trades
        ArrayList<ResourcesCount> from = new ArrayList<ResourcesCount>();

        //from
        num1=2;
        type1=ResourceType.SHIELD;
        ResourcesCount from1 = new ResourcesCount(num1, type1);
        num2=1;
        type2=ResourceType.COIN;
        ResourcesCount from2 = new ResourcesCount(num2, type2);

        from.add(from1);
        from.add(from2);

        //to
        ArrayList<Resource> to = new ArrayList<Resource>();
        Resource get = new Resource(ResourceType.FAITH);
        to.add(get);

        ProductivePower powers = new ProductivePower(from, to);


        DevelopmentCard card = new DevelopmentCard(DevelopmentCardLevel.FIRST, DevelopmentCardType.GREEN, 1, costs, powers);
        System.out.println("The JSON representation of Object Card is ");
        System.out.println(new Gson().toJson(card));
    }
}
