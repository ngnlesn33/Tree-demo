package BST;

import Controller.BSTAnimationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BSTAnimation extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/BSTAnimation.fxml"));
        Parent root = loader.load();

        BSTAnimationController controller = loader.getController();
        // Initialize your BST and BTView instances in the controller

        Scene scene = new Scene(root, 450, 250);
        primaryStage.setTitle("BSTAnimation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
