package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.connection.view.ClientConnectionView;
import it.polimi.ingsw.controller.move.MovePlayerType;
import it.polimi.ingsw.controller.move.MoveResponse;
import it.polimi.ingsw.controller.move.production.move.ResourcePick;
import it.polimi.ingsw.controller.move.settings.MessageMove;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.ProductivePower;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardLevel;
import it.polimi.ingsw.model.developmentCard.DevelopmentCardType;
import it.polimi.ingsw.model.market.MoveType;
import it.polimi.ingsw.view.gui.App;
import it.polimi.ingsw.view.gui.GenericController;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WelcomeController extends GenericController {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField playerName;

    public void start() {
        IntegerProperty showImg = new SimpleIntegerProperty(0);
        URL url1,url2,url3,url4;
        try {
            url1 = new File("src/main/resources/images/welcome_card/game-back.png").toURI().toURL();
            url2 = new File("src/main/resources/images/welcome_card/conti-front.png").toURI().toURL();
            url3 = new File("src/main/resources/images/welcome_card/hazem-front.png").toURI().toURL();
            url4 = new File("src/main/resources/images/welcome_card/tabo-front.png").toURI().toURL();
            Node card = createCard(showImg,
                    new Image(url1.toString()),
                    new Image(url2.toString()),
                    new Image(url3.toString()),
                    new Image(url4.toString())
            );
            // first 90° -> show front
            RotateTransition rotator1 = createRotator(card, 0, 90);

            // from 90° to 270° show backside
            rotator1.setOnFinished(evt -> setInteger(showImg));
            RotateTransition rotator2 = createRotator(card, 90, 270);

            // from 270° to 360° show front again
            rotator2.setOnFinished(evt -> setInteger(showImg));
            RotateTransition rotator3 = createRotator(card, 270, 360);
            rotator2.setOnFinished(evt -> setInteger(showImg));


            SequentialTransition rotator = new SequentialTransition(card, rotator1, rotator2, rotator3);
            rotator.setCycleCount(Timeline.INDEFINITE);
            rotator.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    public void play() throws IOException {
        if(!playerName.getText().isBlank()){
            changeView("register", App.connection);
            notify(MessageMove.getInstance(playerName.getText()));
        }
    }


    private void setInteger(IntegerProperty showImg) {
        if (showImg.get() == 3) {
            showImg.set(0);
        } else {
            showImg.set(showImg.get() + 1);
        }
    }

    private Node createCard(IntegerProperty showImg, Image back, Image front1, Image front2, Image front3) {
        imageView.setFitHeight(back.getHeight());
        imageView.setFitWidth(back.getWidth());

        // show front/back depending on value of the showFront property
        imageView.imageProperty().bind(
                Bindings.when(showImg.isEqualTo(0)).then(back)
                        .otherwise(Bindings.when(showImg.isEqualTo(1)).then(front1)
                                .otherwise(Bindings.when(showImg.isEqualTo(2)).then(front2)
                                        .otherwise(front3))));

        // mirror image, when backside is shown to prevent wrong orientation
        imageView.scaleXProperty().bind(Bindings.when(showImg.isEqualTo(0).or(showImg.isEqualTo(2))).then(1d).otherwise(-1d));
        return imageView;
    }

    private RotateTransition createRotator(Node card, double fromAngle, double toAngle) {
        // animation length proportional to the rotation angle
        RotateTransition rotator = new RotateTransition(Duration.millis(Math.abs(5000 * (fromAngle - toAngle) / 360)), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(fromAngle);
        rotator.setToAngle(toAngle);
        rotator.setInterpolator(Interpolator.LINEAR);

        return rotator;
    }

    @Override
    public void changeView(String fxml, ClientConnectionView clientConnectionView) throws IOException {
        super.changeView(fxml, clientConnectionView);
    }

    @Override
    public void blockView() {

    }

    @Override
    public void printModel() {
        super.printModel();
    }

    @Override
    public void disableAllMoves() {
        super.disableAllMoves();
    }

    @Override
    public void initialization() {
        super.initialization();
    }

    @Override
    public void printMessage(String message) {

    }

    @Override
    public void flipLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }

    @Override
    public void enableProduction(ProductivePower power, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {

    }

    @Override
    public void buyDevelopmentCard(DevelopmentCardType type, DevelopmentCardLevel level, int posToAdd, ArrayList<ResourcePick> resourceToUse, int executePlayerPos) {

    }

    @Override
    public void discardLeaderCard(int leaderCardPosition, int executePlayerPos) {

    }

    @Override
    public void manageResourceMarket(MoveType moveType, int pos, int executePlayerPos, int num) {

    }

    @Override
    public void manageResourceMarketConvert(int first, int second, int executePlayerPos) {

    }

    @Override
    public void manageAllowedMoves(ArrayList<MovePlayerType> possibleMove) {

    }

    @Override
    public void manageEndTurn(boolean correctlyEnded, int executePlayerPos, String message) {

    }

    @Override
    public void manageEndMatch() {

    }

    @Override
    public void updateModel(Match match, int playerPosition) {

    }

    @Override
    public void manageReconnection(String playerName) {

    }

    @Override
    public void manageResourceMarketPositioning(ArrayList<Integer> whereToPlace, int executePlayerPos) {

    }

    @Override
    public void askToDiscardTwoLeader(int numOfResource, int executePlayerPos) {

    }

    @Override
    public void askForData(String message, int executePlayerPos) {

    }

    @Override
    public void moveResourceResponse(int num_from_first, int num_from_second, int indexFirstWarehouse, int indexSecondWarehouse) {

    }

    @Override
    public void manageDisconnection(String playerName) {

    }

    @Override
    public void update(MoveResponse message) {

    }
}
