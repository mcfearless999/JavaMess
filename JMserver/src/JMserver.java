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
import java.util.Hashtable;


public class JMserver {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsCount = 10;
    private static final clientThread[] threads = new clientThread[maxClientsCount];
    static Hashtable users = new Hashtable< String, user> ();


    public static void main(String[] args) {

        int portNumber = 5000;

        stringQueue inQueue = new stringQueue();

        String line = " ";
        user chris = new user("chris", "foo");
        users.put("chris", chris);
        user lee = new user("lee", "bar");
        users.put("lee", lee);

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }

        }
}




        /*


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
            //String chatInput = input.readLine();
            //System.out.println(chatInput);

            //Loop that runs server functions
           while  (line.substring(0,1).compareTo("q") !=0 ) {
               String chatInput = input.readLine();
               //while (chatInput != null) {

                   //This will wait until a line of text has been sent
                   node tempNode = new node(chatInput);
                   inQueue.add(tempNode);
               //socket = sSocket.accept();
                   //chatInput = input.readLine();

               //}
               //node tempNode = new node(chatInput);
               inQueue.add(tempNode);
               while (inQueue.check() == true) {
                   if (line != null) {
                       line = inQueue.dequeue();
                       String code = commandHandler(line, users);
                       output.println(code);

                   }

               }

           }

        } catch (IOException exception) {
            System.out.println("Error: " + exception);
        }


    }
*/




}

class clientThread extends Thread {

    private String name = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;


    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;

    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());

            //os.println("You have connected at: " + new Date());

            String line = is.readLine();
            System.out.println(line);
            while (true) {

                line = is.readLine();
                String response;
                if (line != null)
                {

                        if (line.startsWith("q;")) {
                            break;
                        }


                    response = commandHandler(line, JMserver.users);
                        System.out.println(response);
                        if (response.substring(0,4).compareTo("auth")==0)
                        {
                            name = response.substring(5);
                        }
                    os.println(response.substring(0,4));
                    //System.out.println(response);
                }
                //if (response.compareTo("auth") != 0) break;
                //name = is.readLine().trim();
                // if (name.indexOf('@') == -1) {
                //break;
                //} else {
                //os.println("The name should not contain '@' character.");
                //}
                //}

      /* Welcome the new the client.
            os.println("Welcome " + name
                    + " to our chat room.\nTo leave enter /quit in a new line.");
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName = "@" + name;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].os.println("*** A new user " + name
                                + " entered the chat room !!! ***");
                    }
                }
            }
      /* Start the conversation. */

        /* If the message is private sent it to the given client. */

          /* The message is public, broadcast it to all other clients. */

                    synchronized (this) {
                        String userlist ="u;";
                        //os.println(response);
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].name != null) {
                                System.out.println(i + threads[i].name );
                                userlist = userlist + threads[i].name + ";";

                            }
                        }
                        System.out.println(userlist);
                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].name != null) {
                                threads[i].os.println(userlist);
                                //System.out.println(userlist +"foo");


                            }
                        }
                        response =commandHandler(line, JMserver.users);
                        //os.println(response);
                        //System.out.println(response);
                        System.out.println(response);
                        if (response.substring(0,4).compareTo("m:X:")==0){

                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].name != null) {
                                threads[i].os.println( "s:" + name + ": " + response.substring(4));

                            }
                        }
                    }

                    }

                }



                String[] words = line.split(":");
                if (words.length > 1 && words[2] != null) {
                    words[2] = words[2].trim();

                    if (!words[2].isEmpty()) {
                        synchronized (this) {
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null && threads[i] != this
                                        && threads[i].name != null
                                        && threads[i].name.equals(words[1])) {
                                    threads[i].os.println("<" + name + "> " + words[1]);
                    /*
                     * Echo this message to let the client know the private
                     * message was sent.
                     */
                                    this.os.println(name + ": " + words[2]);
                                    break;
                                }
                            }
                        }
                    }
                }



            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i].name != null &&
                        threads[i].name.compareTo(line.substring(2))==0) {
                    threads[i].os.println("q:");
                    //System.out.println(userlist +"foo");


                }
            }



      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
                synchronized (this) {
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] == this) {
                            threads[i] = null;
                        }
                    }
                }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
                is.close();
                os.close();
                clientSocket.close();

            } catch(IOException e){
            }

    }
    static String commandHandler(String line, Hashtable users) {
        //get command from front of string
        String command = line.substring(0, 1);
        //parse line

        switch (command) {

            case "l":
                //check login information
                //login()
                String[] tempLogin =line.split(":");
                user loginUser = (user) users.get(tempLogin[1]);
                if (loginUser == null) return null;
                if (loginUser.passWord_.compareTo(tempLogin[2]) ==0) {
                    //name = tempLogin[1];
                    return ("auth:" + tempLogin[1]);
                }


                return null;
            case "q":
                //logout

                return line;

            case "r":
                //register
                String[] tempReg =line.split(":");
                if (tempReg[1].startsWith("X")) return "error";
                if (users.get(tempReg[1]) !=null) return "error";
                //parse
                user newUser = new user(tempReg[1],tempReg[2] );
                users.put(tempReg[1], newUser);

                return "reg";

            case "h":
                //retrieve history
                return null;

            case "m":
                //send message
                //System.out.println("message received");
                String[] tempMess =line.split(":");
                if (tempMess[1].compareTo("X") ==0)
                {
                    //copy message to public file
                    return (tempMess[0] + ":X:" + tempMess[2] );
                }


                return null;

        }
        //System.out.println(line);

        return line;

    }

}
