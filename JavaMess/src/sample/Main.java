package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.util.*;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("JavaFX Welcome");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));



        Scene scene = new Scene(grid, 300, 275);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        primaryStage.setScene(scene);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);


        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                try {
                    Socket socket = new Socket("localhost",5000);
                    PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String Username = userTextField.getText();
                    System.out.println(Username);
                    String rawPw= pwBox.getText();
                    System.out.println(rawPw);
                    output.println(Username);
                } catch (IOException exception) {
                    System.out.println("Error: " + exception);
                }


                //Client test = new Client();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args)
    {

        launch(args);

    }



}

class Client{
    public Client()
    {
        //We set up the scanner to receive user input
        Scanner scanner = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost",5000);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //This will wait for the server to send the string to the client saying a connection
            //has been made.
            //output.println(userName);
            String inputString = input.readLine();
            System.out.println(inputString);
            //Again, here is the code that will run the client, this will continue looking for
            //input from the user then it will send that info to the server.

            while(true) {
                //Here we look for input from the user
                String userInput = scanner.nextLine();
                //Now we write it to the server
                output.println(userInput);
            }
        } catch (IOException exception) {
            System.out.println("Error: " + exception);
        }
    }
}