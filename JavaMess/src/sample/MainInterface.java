package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    TextArea currentUsers = new TextArea();

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
        messageGrid.setHgap(10);
        messageGrid.setVgap(5);
        messageGrid.setPadding(new Insets(25, 0, 25, 25));
        messageGrid.setAlignment(Pos.BOTTOM_CENTER);


        messageDisplay.setEditable(false);
        HBox mDisplay = new HBox(5);
        mDisplay.setAlignment(Pos.TOP_CENTER);
        mDisplay.getChildren().add(messageDisplay);
        messageGrid.add(mDisplay,0,1);
        //Text dialog = new Text("");
        TextArea messageTextField = new TextArea();
        HBox mTextBox = new HBox(5);
        mTextBox.setAlignment(Pos.BOTTOM_LEFT);
        mTextBox.getChildren().add(messageTextField);
        messageGrid.add(mTextBox, 0, 2);

        VBox hbBtn = new VBox(5);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        VBox userList = new VBox(5);
        userList.setAlignment(Pos.TOP_RIGHT);
        userList.setMaxSize(100,400);
        userList.getChildren().add(currentUsers);

        messageGrid.add(userList,2,1);


        Button sendButton = new Button("send");
        hbBtn.getChildren().add(sendButton);
        Button exitButton = new Button("quit");
        hbBtn.getChildren().add(exitButton);
        messageGrid.add(hbBtn, 2, 2);
        //mDisplay.getChildren().add(dialog);
        String dialog ="";
        main = new Scene(messageGrid, 700, 400);


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                output.println("q;" +userName );
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

                                if( inStream.startsWith("s") ){
                                    display = display  + inStream.substring(2) + "\n";
                                    messageDisplay.setText(display);
                                    System.out.println(display);
                                }
                                if ( inStream.startsWith("u") ){
                                    currentUsers.setText("");
                                    String list="";
                                    String[] uselist = inStream.split(";");
                                    for (int i = 1; i< uselist.length; ++i)
                                    {
                                        list = list + uselist[i] + "\n";
                                    }
                                    System.out.println(list);
                                    currentUsers.setText(list);
                                }
                                if ( inStream.startsWith("q") ){

                                    window.close();
                                }
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
