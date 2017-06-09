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
    static boolean runServer = true;
    private static String filePath = "/Users/chrism/CS300/";

    public static void main(String[] args) {

        int portNumber = 5000;

        loadUsers();


        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (runServer) {
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
    static void loadUsers()
    {
        File infile = new File(filePath + "chatusers.txt");
        try {
        BufferedReader b = new BufferedReader(new FileReader(infile));

        String readLine = "";


            while ((readLine = b.readLine()) != null) {
                String[] tempUser = readLine.split(":");
                user temp = new user(tempUser[0],tempUser[1]);
                users.put(tempUser[0], temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }




       static public void fileWriter() {
           Set<String> keys = JMserver.users.keySet();

           BufferedWriter bw = null;
           FileWriter fw = null;
           File file = new File(filePath + "chatusers.txt");
           try {
               file.createNewFile();

               if (!file.exists()) {
                   file.createNewFile();
               }
               fw = new FileWriter(file);
               bw = new BufferedWriter(fw);
               for (String key : keys) {
                   user temp = (user) JMserver.users.get(key);
                   String data = temp.write();
                   fw.write(data);

               }
               fw.close();
           } catch (IOException e) {
               e.printStackTrace();
           } finally {

               try {

                   if (bw != null)
                       bw.close();

                   if (fw != null)
                       fw.close();

               } catch (IOException ex) {

                   ex.printStackTrace();

               }
           }
       }

}





class clientThread extends Thread {

    private String name = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    static private int maxClientsCount;
    private boolean runThread = true;


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
            boolean notLoggedIn = true;
            while (!line.startsWith("q")) {

                line = is.readLine();
                String response;
                if (line != null) {

                    if (line.startsWith("q;")) {
                        break;
                    }


                    response = loginHandler(line, JMserver.users);
                    System.out.println(response);

                    if (response.startsWith("auth") || response.startsWith("reg") ) {
                        String[] temp = response.split(":");
                        name = temp[1];
                        notLoggedIn = false;
                    }
                    os.println(response);
                    //System.out.println(response);
                }

                synchronized (this) {
                    String userlist = "u;";
                    //os.println(response);
                    for (int i = 0; i < maxClientsCount; i++) {
                        if (threads[i] != null && threads[i].name != null) {
                            //System.out.println(i + threads[i].name);
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
                    response = messageHandler(line);
                    os.println(response);
                    //System.out.println(response);
                    System.out.println(response);
                    if (response.substring(0, 4).compareTo("m:X:") == 0) {

                        for (int i = 0; i < maxClientsCount; i++) {
                            if (threads[i] != null && threads[i].name != null) {
                                threads[i].os.println("s:" + name + ": " + response.substring(4));

                            }
                        }
                    }

                }
                if ((line.substring(0, 1).compareTo("p") == 0) ||line.startsWith("h;")) {

                    String[] words = line.split(";");
                    //if (words.length > 1 && words[2] != null) {
                    //words[2] = words[2].trim();
                    System.out.println(words[2]);
                    if (!words[2].isEmpty()) {
                        synchronized (this) {
                            for (int i = 0; i < maxClientsCount; i++) {
                                if (threads[i] != null && threads[i] != this
                                        && threads[i].name != null
                                        && threads[i].name.equals(words[2])) {
                                    threads[i].os.print(line);
                                    //System.out.println("private message to :" + threads[i].name);
                    /*
                     * Echo this message to let the client know the private
                     * message was sent.
                     */
                                    //this.os.println("r;"+words[1] + ";" + words[3]);
                                    break;
                                }
                            }
                        }
                    }
                    // }

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
     String loginHandler(String line, Hashtable users) {
        //get command from front of string
        String command = line.substring(0, 1);
        //parse line

        switch (command) {

            case "l":
                //check login information
                //login()
                String[] tempLogin = line.split(":");
                user loginUser = (user) users.get(tempLogin[1]);
                if (loginUser == null) return "err:unf";
                if (loginUser.passWord_.compareTo(tempLogin[2]) == 0) {
                    //name = tempLogin[1];
                    return ("auth:" + tempLogin[1]);
                }


                return "err:pwf";
            case "q":
                //logout

                return line;

            case "r":
                //register
                String[] tempReg = line.split(":");
                if (tempReg[1].startsWith("X")) return "err:X";
                if (users.get(tempReg[1]) != null) return "err:AR";
                //parse
                user newUser = new user(tempReg[1], tempReg[2]);
                users.put(tempReg[1], newUser);

                return "reg:" + tempReg[1] ;



        }
        //System.out.println(line);

        return line;

    }

    String messageHandler(String line) {
        //get command from front of string
        String command = line.substring(0, 1);
        //parse line

        switch (command) {
            case "h":
                //retrieve history
                String history = "";

                String path = "/Users/chrism/CS300/";

                String[] words = line.split(":");
                String sender = words[1];

                if (words[1].compareTo(words[2]) > 0) {
                    String temp = words[1];
                    words[1] = words[2];
                    words[2] = temp;
                }
                System.out.println(path + words[1] + words[2] + "log.txt");
                File infile = new File(path + words[1] + words[2] + "log.txt");
                try {
                    BufferedReader b = new BufferedReader(new FileReader(infile));

                    String readLine = "";


                    while ((readLine = b.readLine()) != null) {
                    history =history + readLine + "`";
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //System.out.println(history);
                return "h;" + sender + ";" +history;

            case "p":

                 words = line.split(";");
                 sender = words[1];

                if (words[1].compareTo(words[2]) > 0) {
                    String temp = words[1];
                    words[1] = words[2];
                    words[2] = temp;
                }
                path = "/Users/chrism/CS300/";

                String historyFileName = path + words[1] + words[2] + "log.txt";

                BufferedWriter bw = null;
                FileWriter fw = null;
                File file = new File(historyFileName);
                try {
                    file.createNewFile();

                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    fw = new FileWriter(file.getAbsoluteFile(), true);
                    bw = new BufferedWriter(fw);

                    fw.write(sender + ": " + words[3] + "\n");

                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    try {

                        if (bw != null)
                            bw.close();

                        if (fw != null)
                            fw.close();

                    } catch (IOException ex) {

                        ex.printStackTrace();

                    }
                }

                return line;
            case "m":
                //send message
                //System.out.println("message received");
                String[] tempMess = line.split(":");
                if (tempMess[1].compareTo("X") == 0) {
                    //copy message to public file
                    if (tempMess[2].equals("killserver")) {
                        JMserver.fileWriter();
                        //killThread();
                    }
                    return (tempMess[0] + ":X:" + tempMess[2]);

                }
            case "q":
                //logout

                return line;


        }
        return line;
    }


    void killThread()
    {
        for (int i = 0; i < maxClientsCount; i++)
        {
            threads[i].runThread = false;
        }
    }

}
