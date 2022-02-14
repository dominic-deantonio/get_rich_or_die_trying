package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.GuiApp;

public class GuiController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label loadLabel;

    @FXML
    private Button loadSubmitButton;

    @FXML
    private TextField loadText;

    @FXML
    void loadSubmitPressed(ActionEvent event) {

    }

    @FXML
    private Button backstoryButton1;

    @FXML
    private Button backstoryButton2;

    @FXML
    private Button backstoryButton3;

    @FXML
    private Label backstoryLabel1;

    @FXML
    private Label backstoryLabel2;

    @FXML
    void button1Pressed(ActionEvent event) {

    }
    @FXML
    void button2Pressed(ActionEvent event) {

    }

    @FXML
    void button3Pressed(ActionEvent event) {

    }

    @FXML
    public void switchToNewGameScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(GuiApp.class.getResource("newGame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root,1500, 1000);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    public void switchToLoadGameScene(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(GuiApp.class.getResource("loadGame.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1500, 1000);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}