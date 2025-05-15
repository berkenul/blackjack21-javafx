package com.example.g21;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 21 game - Main class
 * Name: Berk Enul
 * Date: 15.05.2025
 */
public class Main extends Application {

    public static Stage mainStage;


    @Override
    public void start(Stage stage) {
        try {

            mainStage = stage;


            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root, 800, 600);
            root.setStyle("-fx-background-color: #006400;");


            stage.setTitle("21 Game");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {

            System.out.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void switchToGameScene() {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("game-view.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root, 800, 600);
            root.setStyle("-fx-background-color: #006400;");


            mainStage.setScene(scene);


            GameController controller = loader.getController();
            controller.setupGame();

        } catch (Exception e) {

            System.out.println("Error switching to game screen: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}