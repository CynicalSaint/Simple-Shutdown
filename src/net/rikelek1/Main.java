package net.rikelek1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Main extends Application {
    private int timeHours, timeMinutes, timeSeconds;
    private int time = 0;
    private String runCommand = "";
    private Robot robot;
    private Stage window;

    private TextField timeFieldHours = new TextField();
    private TextField timeFieldMinutes = new TextField();
    private TextField timeFieldSeconds = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        robot = new Robot();

        window = primaryStage;
        window.setTitle("Shutdown app - v0.1");
        window.setMinWidth(625);
        window.setMinHeight(100);
        window.setMaxWidth(625);
        window.setMaxHeight(100);

        HBox mainHBox = new HBox(5);
        HBox buttons = new HBox(5);
        VBox mainLayout = new VBox(5);

        Label labelHours = new Label("Hours");
        Label labelMinutes = new Label("Minutes");
        Label labelSeconds = new Label("Seconds");

        timeFieldHours.setMaxWidth(275);
        timeFieldMinutes.setMaxWidth(275);
        timeFieldSeconds.setMaxWidth(275);

        Button shutdownButton = new Button("Shutdown");
        Button restartButton = new Button("Restart");
        Button abortButton = new Button("Abort");
        Button cancelButton = new Button("Cancel");

        shutdownButton.setOnAction(e -> {
            setTimes();
            execCommand("shutdown");
        });

        restartButton.setOnAction(e -> {
            setTimes();
            execCommand("restart");
        });

        abortButton.setOnAction(e -> execCommand("abort"));

        cancelButton.setOnAction(e -> System.exit(1337));

        mainHBox.getChildren().addAll(labelHours, timeFieldHours, labelMinutes, timeFieldMinutes, labelSeconds, timeFieldSeconds);
        mainHBox.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(shutdownButton, restartButton, abortButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(mainHBox, buttons);
        mainLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();
    }

    private void setTimes() {
        if(timeFieldHours.getText().equals("")) {
            timeHours = 0;
        } else {
            timeHours = Integer.valueOf(timeFieldHours.getText());
        }

        if(timeFieldMinutes.getText().equals("")) {
            timeMinutes = 0;
        } else {
            timeMinutes = Integer.valueOf(timeFieldMinutes.getText());
        }

        if(timeFieldSeconds.getText().equals("")) {
            timeSeconds = 0;
        } else {
            timeSeconds = Integer.valueOf(timeFieldSeconds.getText());
        }

        time = (timeHours * 3600) + (timeMinutes * 60) + timeSeconds;
        System.out.println(time);
    }

    private void execCommand(String command) {
        switch(command) {
            case "shutdown":
                runCommand = "shutdown -s -t " + time;
                break;
            case "restart":
                runCommand = "shutdown -r -t " + time;
                break;
            case "abort":
                runCommand = "shutdown -a";
                break;
        }

        StringSelection stringSelection = new StringSelection(runCommand);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_R);
        robot.keyRelease(KeyEvent.VK_R);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.delay(100);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        window.close();
        System.exit(1337);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
