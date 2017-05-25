/*package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
*/
import java.net.*;
import java.nio.*;
import java.util.*;
import java.io.*;


public class JMserver
{
    public static void main(String[] args)
    {
        new JMserver();
    }

    public JMserver()
    {
        try {
            ServerSocket sSocket = new ServerSocket(5000);
            System.out.println("Server started at: " + new Date());

            //Wait for a client to connect
            Socket socket = sSocket.accept();

            //Create the streams
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Tell the client that he/she has connected
            output.println("You have connected at: " + new Date());

            //Loop that runs server functions
            while(true) {
                //This will wait until a line of text has been sent
                String chatInput = input.readLine();
                System.out.println(chatInput);
            }
        } catch(IOException exception) {
            System.out.println("Error: " + exception);
        }
    }

}



