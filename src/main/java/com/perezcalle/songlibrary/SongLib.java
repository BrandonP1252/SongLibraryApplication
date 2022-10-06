package com.perezcalle.songlibrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SongLib extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SongLib.class.getResource("MainScene.fxml"));
        Scene appScene = new Scene(fxmlLoader.load(), 600, 674);
        primaryStage.setResizable(false);
        primaryStage.setScene(appScene);
        primaryStage.setTitle("Song Library Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}