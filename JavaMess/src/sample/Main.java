package sample;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
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

    PrintWriter output;
    BufferedReader input;

    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        String loginPacket = "";
        //while (loginPacket.compareTo( "e") == 0) {


        //}
        try {
            Socket socket = new Socket("localhost", 5000);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (input.ready()) {
                String check = input.readLine();
                System.out.println(check);
            }
            output.println("Connected at : " + new Date());


            String auth = "";
            String[] tempReg;

            while (auth.compareTo("auth") != 0) {
                loginPacket = Login.display();
                output.println(loginPacket);
                System.out.println(loginPacket);
                //System.out.println("postlogin");
                //if (input.ready())
                auth = input.readLine();


                System.out.println(auth);

            }

            tempReg = loginPacket.split(":");
            //System.out.println("successfully logged in");
            MainInterface mainWindow = new MainInterface();
            String quit = mainWindow.display(input, output, tempReg[1]);


            //while (true);


            if (quit.compareTo("q:") == 0) {

            output.close();
            input.close();
            socket.close();
            }
        } catch (IOException exception) {
            System.out.println("Error: " + exception);
        }


        //System.out.println(loginPacket);

    }
}

