package it.polimi.ingsw.gui;

import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.PlayerMove;
import it.polimi.ingsw.controller.move.market.MarketInteractionPlayerMove;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Utils;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;

public class PrimaryController extends GenericController {


    @FXML
    private TabPane tabpane;
    @FXML
    private Pane leadercard1;
    @FXML
    private Pane leadercard2;
    @FXML
    private Circle m_add;
    @FXML
    private Circle m_00;
    @FXML
    private Circle m_01;
    @FXML
    private Circle m_02;
    @FXML
    private Circle m_03;
    @FXML
    private Circle m_10;
    @FXML
    private Circle m_11;
    @FXML
    private Circle m_12;
    @FXML
    private Circle m_13;
    @FXML
    private Circle m_20;
    @FXML
    private Circle m_21;
    @FXML
    private Circle m_22;
    @FXML
    private Circle m_23;
    @FXML
    private Pane[] leadercards;



    @Override
    public void update(MoveResponse message) {
        //elaborate message
        System.out.println("Message obtained");
    }

    public void initialization(){
        leadercards = new Pane[2];
        leadercards[0] = leadercard1;
        leadercards[1] = leadercard2;
    }

    @Override
    public void blockView() {
        tabpane.setDisable(true);
    }

    @Override
    public void printModel() {
        // new Image(url)
        mapLeaderCards();
        mapMarbles();
    }

    public void mapMarbles(){
        m_add.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getAdditionalMarble().getColor()));
        m_00.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,0).getColor()));
        m_01.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,1).getColor()));
        m_02.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,2).getColor()));
        m_03.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(0,3).getColor()));
        m_10.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,0).getColor()));
        m_11.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,1).getColor()));
        m_12.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,2).getColor()));
        m_13.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(1,3).getColor()));
        m_20.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,0).getColor()));
        m_21.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,1).getColor()));
        m_22.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,2).getColor()));
        m_23.setFill(Utils.fromMarbleColorToJavaFXColor(match.getMarketBoard().getMarbleAt(2,3).getColor()));
    }

    public void mapLeaderCards(){
        changeImage(leadercards[0],match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(0).getImage());
        changeImage(leadercards[1],match.getPlayers().get(match.getWhoAmI()).getLeaderCards().get(1).getImage());
    }

    public void changeImage(Pane p,String s){
        URL url = null;
        try{
            url = new File("src/main/resources/images/devcard_leadercard/"+s+".png").toURI().toURL();
        }catch (Exception e){}
        Image image = new Image(url.toString());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage myBI= new BackgroundImage(new Image(url.toString()),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        Background r = new Background(myBI);
        p.setBackground(r);
    }

    //forse non serve metterlo in generic controller?
}
