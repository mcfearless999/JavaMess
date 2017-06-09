package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by chrism on 6/4/17.
 */
public class PrivateMessage
{
    static Stage window;
    static Scene main;
    static String display ="";
    static String userName;

    static BufferedReader input;
    static PrintWriter output;

    TextArea messageDisplay = new TextArea();
    //TextArea currentUsers = new TextArea();
    boolean display(BufferedReader in , PrintWriter out, String user, String dest, String incMess)
    {
        input = in;
        output = out;
        display = incMess;

        userName = user;
        window = new Stage();

        messageDisplay.setText(display);
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
        messageTextField.setMaxSize(400,80);
        messageGrid.add(mTextBox, 0, 2);

        VBox hbBtn = new VBox(5);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        /*
        VBox userList = new VBox(5);
        userList.setAlignment(Pos.TOP_RIGHT);
        userList.setMaxSize(100,350);
        userList.getChildren().add(currentUsers);

        messageGrid.add(userList,2,1);
        */

        Button sendButton = new Button("send");
        sendButton.setMaxSize(100,25);
        hbBtn.getChildren().add(sendButton);
        Button exitButton = new Button("quit");
        exitButton.setMaxSize(100,25);
        hbBtn.getChildren().add(exitButton);
        messageGrid.add(hbBtn, 2, 2);
        //mDisplay.getChildren().add(dialog);
        String dialog ="";
        main = new Scene(messageGrid, 500, 350);


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //output.println("q;" +userName );
                window.close();
            }
        });


        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                String message = messageTextField.getText();
                messageTextField.setText("");

                //display = display + userName+ ": " + message + "\n";
                //messageDisplay.setText(display);


                output.println("p;" + user + ";" + dest + ";" + message);
                //window.close();
            }
        });
        startTask();


        window.setScene(main);
        window.showAndWait();




        return false;
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

                                if( inStream.startsWith("p") ){
                                    String[] privMess = inStream.split(";");
                                    display = display  + privMess[1] + ": " +  privMess[3] + "\n";
                                    messageDisplay.setText(display);
                                    System.out.println(display);
                                }


                                if( inStream.startsWith("r") ){
                                    String[] privMess = inStream.split(";");
                                    display = display  + privMess[1] + ": " +  privMess[2] + "\n";
                                    messageDisplay.setText(display);
                                    System.out.println(display);
                                }
                                //if ( inStream.startsWith("q") ){

                                    //window.close();
                                //}
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
