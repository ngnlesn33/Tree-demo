package Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;

public class MainMenuController {
    private Stage mainStage; // Add this field
    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    @FXML
    private void handleGenericTree() {}

    @FXML
    private void handleBinarySearchTree() {
        handleTreeTypeSelection("/View/BSTAnimation.fxml", "BST Visualization");
    }

    @FXML
    private void handleAVLTree() {
        handleTreeTypeSelection("AVLAnimation.fxml", "AVL Visualization");
    }

    @FXML
    private void handleBalancedBinaryTree() {}

    @FXML
    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Tree Visualization App Help");
        alert.setContentText("This is a tree visualization application. "
                + "Please select a type of data structure to visualize.");

        alert.showAndWait();
    }

    @FXML
    private void askForConfirmationAndExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.runLater(() -> {
                    if (mainStage != null) {
                        mainStage.close();
                    }
                });
            }
        });
    }

    private void handleTreeTypeSelection(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load the FXML file: " + fxmlFile, e);
        }
    }
}
