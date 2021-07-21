package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Label songName;
    public ProgressBar songProgress;
    public Button play;
    public Button previous;
    public Button pause;
    public Button reset;
    public ComboBox speedComboBox;
    public Button next;
    public Slider volumeSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playMedia(ActionEvent actionEvent) {
    }

    public void pauseMedia(ActionEvent actionEvent) {
    }

    public void resetMedia(ActionEvent actionEvent) {
    }

    public void previousMedia(ActionEvent actionEvent) {
    }


    public void nextMedia(ActionEvent actionEvent) {
    }


    public void changeSpeed(ActionEvent actionEvent) {
    }

    public void beginTimer(){

    }

    public void cancelTimer(){

    }



}
