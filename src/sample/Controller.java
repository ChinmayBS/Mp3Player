package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    public Label songLabel;
    public ProgressBar songProgress;
    public AnchorPane pane;
    public ComboBox<String> speedComboBox;
    public Slider volumeSlider;

    public Button play;
    public Button previous;
    public Button pause;
    public Button reset;
    public Button next;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;

    private int[] speeds={25,50,75,100,125,150,175,200};

    private Timer timer;

    private TimerTask task;

    private boolean running;


    private MediaPlayer mediaPlayer;
    private Media media;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs=new ArrayList<>();
        directory=new File("/home/chinmay/sem-four/oops/theory/Mp3Player/src/music");
        files=directory.listFiles();           //All the files in the directory
        if(files!=null){
            for (File file:files){
                songs.add(file);
                System.out.println(file);
            }
        }

       // System.out.println(songs.get(songNumber).toURI().toString());
        media=new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer=new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());

        for (int i=0;i<speeds.length;i++){
            speedComboBox.getItems().add(speeds[i]+"%");
        }

        speedComboBox.setOnAction(this::changeSpeed);  //new syntax

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
            }
        });

        songProgress.setStyle("-fx-accent:#6b705c");

    }

    public void playMedia(ActionEvent actionEvent) {

        mediaPlayer.setVolume(volumeSlider.getValue()*0.01);

        beginTimer();

        changeSpeed(actionEvent);

        mediaPlayer.play();
    }

    public void pauseMedia(ActionEvent actionEvent) {
        cancelTimer();

        mediaPlayer.pause();
    }

    public void resetMedia(ActionEvent actionEvent) {

        songProgress.setProgress(0);
       // mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void nextMedia(ActionEvent actionEvent) {

            songNumber=(songNumber+1)%songs.size();
            mediaPlayer.stop();

            if (running){
                cancelTimer();
            }

            media=new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer=new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

           // playMedia(actionEvent);


    }

    public void previousMedia(ActionEvent actionEvent) {
        if(songNumber-1>=0) {
            songNumber = (songNumber - 1);
        }
        else{
            songNumber=songs.size()-1;
        }
        mediaPlayer.stop();
        if (running){
            cancelTimer();
        }

        media=new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer=new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());

        // playMedia(actionEvent);

    }





    public void changeSpeed(ActionEvent actionEvent) {
        if(speedComboBox.getValue()==null){
            mediaPlayer.setRate(1);
        }else{
            mediaPlayer.setRate(Integer.parseInt(speedComboBox.getValue().substring(0,speedComboBox.getValue().length()-1))*0.01);
        }

    }

    public void beginTimer(){
        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                running=true;
                double current=mediaPlayer.getCurrentTime().toSeconds();
                double end=media.getDuration().toSeconds();
                System.out.println(current/end);

                songProgress.setProgress(current/end);

                if(current/end==1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,1000);
    }

    public void cancelTimer(){
        running=false;
        timer.cancel();
    }



}
