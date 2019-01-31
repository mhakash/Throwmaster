package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Real extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Throw master");
        primaryStage.setScene(new Scene(root, 415, 295));
        primaryStage.show();

        Real.primaryStage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
