//Giving reflective access to your module
//We need to give javafx.graphics reflective access.
//The JavaFX graphics module injects fields into a Controller using reflection.
// This is useful, because it facilitates dependency injection, but it means we need to grant reflective access.
// That requires the opens keyword.
module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens it.polimi.ingsw to javafx.fxml;
    opens it.polimi.ingsw.model.resource to com.google.gson;
    opens it.polimi.ingsw.model to com.google.gson;
    exports it.polimi.ingsw;
}