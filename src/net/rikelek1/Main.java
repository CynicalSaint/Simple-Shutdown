package net.rikelek1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class Main extends Application {
    private String time = "0";
    private String runCommand = "";
    private Robot robot;
    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        robot = new Robot();

        window = primaryStage;
        window.setTitle("Shutdown app - v0.1");
        window.setMinWidth(300);
        window.setMinHeight(100);
        window.setMaxWidth(300);
        window.setMaxHeight(100);

        HBox buttons = new HBox(5);
        VBox mainLayout = new VBox(5);

        TextField timeField = new TextField();
        timeField.setMaxWidth(275);
        Button shutdownButton = new Button("Shutdown");
        Button restartButton = new Button("Restart");
        Button abortButton = new Button("Abort");
        Button cancelButton = new Button("Cancel");

        shutdownButton.setOnAction(e -> {
            if(timeField.getText().length() > 0) {
                time = timeField.getText();
                execCommand("shutdown");
            } else {
                execCommand("shutdown");
            }
        });

        restartButton.setOnAction(e -> {
            if(timeField.getText().length() > 0) {
                time = timeField.getText();
                execCommand("restart");
            } else {
                execCommand("restart");
            }
        });

        abortButton.setOnAction(e -> execCommand("abort"));

        cancelButton.setOnAction(e -> System.exit(1337));

        buttons.getChildren().addAll(shutdownButton, restartButton, abortButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(timeField, buttons);
        mainLayout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();
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
