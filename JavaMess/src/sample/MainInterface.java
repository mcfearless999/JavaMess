package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.application.Application;
import javafx.application.Platform;



/**
 * Created by chrism on 6/2/17.
 */
public class MainInterface {

    static Stage window;
    static Scene main;
    static String display ="";
    static String userName;

    static BufferedReader input;
    static PrintWriter output;


    TextArea messageDisplay = new TextArea();

    ListView <Text> displayedMessages = new ListView<>();
    ListView <String> listofUsers = new ListView<>();
    //TextArea currentUsers = new TextArea();

    public String display(BufferedReader in , PrintWriter out, String user ) throws IOException {
        userName = user;
        window = new Stage();
        input = in;
        output = out;

        Text scenetitle = new Text("JavaMess");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        VBox title = new VBox(5);
        title.getChildren().add(scenetitle);

        Text userDisplay = new Text( "logged in as : " + user);
        title.getChildren().add(userDisplay);

        GridPane messageGrid = new GridPane();
        messageGrid.add(title, 0, 0, 2, 1);
        //messageGrid.add(userDisplay,0, 0, 2, 1);
        messageGrid.setAlignment(Pos.CENTER);
        messageGrid.setHgap(5);
        messageGrid.setVgap(5);
        messageGrid.setPadding(new Insets(25, 25, 25, 25));
        messageGrid.setAlignment(Pos.BOTTOM_CENTER);


        //messageDisplay.setEditable(false);
        HBox mDisplay = new HBox(5);
        mDisplay.setAlignment(Pos.TOP_CENTER);
        mDisplay.getChildren().add(displayedMessages);
        messageGrid.add(mDisplay,0,1);
        displayedMessages.setMinWidth(550);

        TextArea messageTextField = new TextArea();
        HBox mTextBox = new HBox(5);
        mTextBox.setAlignment(Pos.BOTTOM_LEFT);
        mTextBox.getChildren().add(messageTextField);
        messageTextField.setMinWidth(550);
        messageTextField.setMaxSize(700,120);
        messageGrid.add(mTextBox, 0, 2);

        VBox hbBtn = new VBox(5);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        VBox userList = new VBox(5);
        userList.setAlignment(Pos.TOP_RIGHT);
        userList.setMaxSize(120,350);
        userList.setMinWidth(120);
        userList.getChildren().add(listofUsers);



        messageGrid.add(userList,2,1);

        Button histButton = new Button("get history");
        hbBtn.getChildren().add(histButton);
        histButton.setMaxSize(120,20);
        Button privButton = new Button("private message");
        hbBtn.getChildren().add(privButton);
        privButton.setMaxSize(120,20);
        Button sendButton = new Button("send");
        sendButton.setMaxSize(120,20);
        hbBtn.getChildren().add(sendButton);
        Button exitButton = new Button("quit");
        exitButton.setMaxSize(120,20);
        hbBtn.getChildren().add(exitButton);
        messageGrid.add(hbBtn, 2, 2);
        //mDisplay.getChildren().add(dialog);
        String dialog ="";
        main = new Scene(messageGrid, 720, 450);


        histButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String dest = listofUsers.getSelectionModel().getSelectedItem();
                String histReq = "h:";
                if (dest != null) {
                    if (user.compareTo(dest) == 0) {
                        messageTextField.setText("error");
                    }

                        histReq = histReq +  user + ":" + dest;
                        output.println(histReq);



                }

            }
        });

        privButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               // PrivateMessage messageWindow = new PrivateMessage();
               // boolean isOpen = messageWindow.display(input,output, user, "lee", "");
                String dest = listofUsers.getSelectionModel().getSelectedItem();

                if (dest != null) {
                    String message = messageTextField.getText();
                    messageTextField.setText("");

                    //display = display + userName+ ": " + message + "\n";
                    //messageDisplay.setText(display);

                    //System.out.println(message);

                    output.println("p;" + user + ";" + dest + ";" + message);
                    //Text temp = new Text(user + "<private message" + ">:" + message);
                    //displayedMessages.getItems().add(temp);
                }else {
                    messageTextField.setText("please select user from userlist for personal message");
                }
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                output.println("q;" +user );
            }
        });


        sendButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    String message = messageTextField.getText();
                    messageTextField.setText("");

                    //display = display + userName+ ": " + message + "\n";
                    //messageDisplay.setText(display);

                    //System.out.println(message);
                    output.println("m:X:" + message);
                    //window.close();
                }
            });
        startTask();


        window.setScene(main);
        window.showAndWait();




        return "q:";
    }




    public void startTask()
    {
        // Create a Runnable
        Runnable task = new Runnable()
        {
            public void run()
            {
                runTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    public void runTask()
    {
        for(;;)
        {
            try
            {
                // Get the Status

                // Update the Label on the JavaFx Application Thread
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //System.out.println("bar");
                        try {
                            if (input.ready())
                            {
                                String inStream = input.readLine();
                                //System.out.println(inStream + "foo");
                                if( inStream.startsWith("s") ){
                                    //display = display  + inStream.substring(2) + "\n";
                                    //messageDisplay.setText(display);
                                    //System.out.println(display);

                                    String[] pubMess = inStream.split(":");
                                    Text temp = new Text (inStream.substring(2));
                                    if (pubMess[1].equals(userName))
                                    {
                                        temp.setFill(javafx.scene.paint.Color.RED);
                                    }else {
                                        temp.setFill(javafx.scene.paint.Color.BLUE);
                                    }
                                    //temp.setEditable(false);
                                    displayedMessages.getItems().add( temp);


                                    /*
                                    TextField temp = new TextField();
                                    temp.appendText(inStream.substring(2));
                                    temp.setStyle("-fx-text-fill: blue");
                                    temp.setEditable(false);
                                    displayedMessages.getItems().add( temp);
                                    //messageDisplay.getChildrenUnmodifiable().add(temp);
                                    */
                                }
                                if ( inStream.startsWith("u") ){

                                    listofUsers.getItems().clear();

                                    String[] uselist = inStream.split(";");
                                    for (int i = 1; i< uselist.length; ++i)

                                    {
                                        if (!uselist[i].equals(userName))
                                        listofUsers.getItems().add(uselist[i]);

                                    }

                                    //System.out.println(list);
                                    //currentUsers.getChildrenUnmodifiable().add(listofUsers);
                                }
                                if( inStream.startsWith("p") ){

                                    String[] privMess = inStream.split(";");
                                    if (privMess.length >= 3) {
                                        String pMess = privMess[1] + " <private message>:" + privMess[3];
                                        //messageDisplay.setText(display);

                                        Text temp = new Text(pMess);
                                        temp.setFill(javafx.scene.paint.Color.GREEN);
                                        //temp.setEditable(false);
                                        displayedMessages.getItems().add(temp);
                                    }
                                    /*
                                    TextField temp = new TextField();
                                    temp.setStyle("-fx-text-fill: green;");
                                    temp.appendText(pMess);
                                    displayedMessages.getItems().add(temp);
                                    temp.setEditable(false);
                                    //messageDisplay.getChildrenUnmodifiable().add(temp);
                                    System.out.println(display);


                                    /*
                                    boolean isOpen;
                                    String[] privMess = inStream.split(";");
                                    int idx =0;
                                    for (int i = 0; i < 10; ++i)
                                    {
                                        if (openPrivates[i] == null)
                                        {
                                            openPrivates[i] = privMess[2];
                                            idx = i;
                                            break;
                                        }
                                        if (openPrivates[i].equals(privMess[2]))
                                        {
                                            idx = i;
                                            break;
                                        }
                                    }

                                    isOpen = true;

                                    String incMess = privMess[1] +": " + privMess[3];
                                    PrivateMessage messageWindow = new PrivateMessage();

                                    isOpen = messageWindow.display(input,output, privMess[2], privMess[1],incMess);
                                    openPrivates[idx] = null;
                                    */
                                }
                                if ( inStream.startsWith("h") ){
                                    String[] hist = inStream.split(";");
                                    //System.out.println(hist[2]);
                                    if (hist.length >= 3) {
                                       boolean catcher = historyWindow.display(hist[2]);
                                    }

                                }


                                if ( inStream.startsWith("q") ){

                                    window.close();
                                }
                                //output.println("@ping");
                                        //System.out.println(display);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //textArea.appendText(status+"\n");

                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }
}
