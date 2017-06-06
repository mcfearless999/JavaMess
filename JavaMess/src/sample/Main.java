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
/*
    public void startTask()
    {
        Runnable task = new Runnable()
        {
            public void run()
            {
                runTask();
            }
        };

        Thread backgroundThread = new Thread(task);

        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void runTask()
    {
        String dialog ="foo";
            while(true) {
                try {

                    if (input.ready()) {
                        dialog = input.readLine();
                        System.out.println(dialog);
                        System.out.println("foo");
                        Thread.sleep(500);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
    }

}
 /*


        //registration
        Text registrationTitle = new Text("Please Register");
        registrationTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        GridPane registrationGrid = new GridPane();
        registrationGrid.setAlignment(Pos.CENTER);
        registrationGrid.setHgap(10);
        registrationGrid.setVgap(10);
        registrationGrid.setPadding(new Insets(25, 25, 25, 25));
        //Scene layout = new Scene(grid, 300, 275);
        Button registrationButton = new Button("Register");
        registrationGrid.add(registrationTitle,0,0,1,2);
        Label newUserName = new Label("User Name:");
        registrationGrid.add(newUserName, 0, 2);


        TextField userNameTextField = new TextField();
        registrationGrid.add(userNameTextField, 1, 2);


        //registrationGrid.getChildren().add(registrationButton);
        Label pw1 = new Label("Password:");
        PasswordField pwBox1 = new PasswordField();
        registrationGrid.add(pwBox1,1 , 3);
        registrationGrid.add(pw1,0 , 3);


        registrationGrid.add(registrationButton,1,5);
        Label pw2 = new Label("Reenter Password:");
        PasswordField pwBox2 = new PasswordField();
        registrationGrid.add(pwBox2, 1, 4);
        registrationGrid.add(pw2, 0, 4);

        //Button 2
        Button button2 = new Button("Cancel");
        button2.setOnAction(e -> window.setScene(login));

        //Layout 2

        registrationButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                String Username = userNameTextField.getText();
                //System.out.println(Username);
                //userName = Username;

                String newPw1 = pwBox1.getText();
                String newPw2 = pwBox1.getText();
                boolean status = false;
                if (newPw1.compareTo(newPw2)==0) status = true;
                //String login = Username + ":" + rawPw;
                //Auth = login;
                System.out.println(Username + " "+ status);
                // output.println(login);
            }
        });
        registrationGrid.add(button2,0,5);
        register = new Scene(registrationGrid, 400, 300);

        //Display scene 1 at first
        window.setScene(login);
        window.setTitle("JavaMess Login");
        window.show();




    }



}





/*
    @Override
    public void start(Stage primaryStage) {
        //login userLog = new login();

        try {
        Socket socket = new Socket("localhost",5000);
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String user = getLogin(output,input);
        //System.out.println(user);
        System.out.println("postlogin");

        } catch (IOException exception) {
                System.out.println("Error: " + exception);
        }


    }



    String getLogin(PrintWriter output,BufferedReader input )
    {

        Stage primaryStage = new Stage();
        primaryStage.setTitle("JavaFX Welcome");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String Auth;

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

        Button login = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(login);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);


        login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                String Username = userTextField.getText();
                //System.out.println(Username);
                //userName = Username;

                String rawPw= pwBox.getText();
                String login = Username + ":" + rawPw;
                //Auth = login;
                //System.out.println(rawPw);
                output.println(login);

                try {
                    String inMess= null;
                    //inMess = input.readLine();
                    //System.out.println(inMess);
                    inMess = input.readLine();
                    System.out.println(inMess);
                    if ( (inMess != null) && (inMess.compareTo("quit") == 0))  primaryStage.hide();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                //Client test = new Client();
            }


        });

        Button registerBtn = new Button("Register");
        HBox nBtn = new HBox(10);
        nBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(registerBtn);
        grid.add(nBtn,1,1);

        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            //boolean regCheck = false;
            @Override
            public void handle(ActionEvent e) {


                System.out.println("bar");
                primaryStage.hide();
                //try {
                //System.out.println("register");
                //} catch (IOException exception) {
                //System.out.println("Error: " + exception);
                //}


                //Client test = new Client();
            }


        });

        primaryStage.show();
        return "foo";
    }
}

*/








