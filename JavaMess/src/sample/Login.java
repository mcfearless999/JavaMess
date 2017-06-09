package sample;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by chrism on 5/30/17.
 */
public class Login {
    static String loginPacket = null;
    static String registrationPacket;

    static Stage window;
    static Scene login, register;

    public static String display(String error) {
        window = new Stage();
        GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));
        //Scene layout = new Scene(grid, 300, 275);
        Button loginButton = new Button("Login");

        Text scenetitle = new Text("Welcome, Please Log In");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        loginGrid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        loginGrid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        loginGrid.add(userTextField, 1, 1);

        loginGrid.getChildren().add(loginButton);
        Label pw = new Label("Password:");
        loginGrid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        loginGrid.add(pwBox, 1, 2);
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        loginGrid.add(hbBtn, 1, 4);

        HBox errorbox = new HBox(5);

        if (!error.equals(""))
        {
            error = errorHandler(error);
        }

        Text Errors = new Text(error);
        Errors.setFill(javafx.scene.paint.Color.RED);
        errorbox.getChildren().add(Errors);



        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                String Username = userTextField.getText();

                //System.out.println(Username);
                //userName = Username;

                String rawPw = pwBox.getText();
                loginPacket = "l:" + Username +":" + rawPw;
                //Auth = login;
                //System.out.println(rawPw);
                // output.println(login);
                window.close();
            }
        });

        Button registerButton = new Button("Register");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(registerButton);
        loginGrid.add(hbBtn1, 0, 4);
        registerButton.setOnAction(e -> window.setScene(register));

        loginGrid.add(errorbox,1,6);
        login = new Scene(loginGrid);
        window.setScene(login);

        Text registrationTitle = new Text("Please Register");
        registrationTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        GridPane registrationGrid = new GridPane();
        registrationGrid.setAlignment(Pos.CENTER);
        registrationGrid.setHgap(10);
        registrationGrid.setVgap(10);
        registrationGrid.setPadding(new Insets(25, 25, 25, 25));
        //Scene layout = new Scene(grid, 300, 275);
        Button registrationButton = new Button("Register");
        registrationGrid.add(registrationTitle, 0, 0, 1, 2);
        Label newUserName = new Label("User Name:");
        registrationGrid.add(newUserName, 0, 2);


        TextField userNameTextField = new TextField();
        registrationGrid.add(userNameTextField, 1, 2);


        //registrationGrid.getChildren().add(registrationButton);
        Label pw1 = new Label("Password:");
        PasswordField pwBox1 = new PasswordField();
        registrationGrid.add(pwBox1, 1, 3);
        registrationGrid.add(pw1, 0, 3);



        Label pw2 = new Label("Reenter Password:");
        PasswordField pwBox2 = new PasswordField();
        registrationGrid.add(pwBox2, 1, 4);
        registrationGrid.add(pw2, 0, 4);
        registrationGrid.add(registrationButton, 1, 5);
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
                loginPacket = "e";
                String newPw1 = pwBox1.getText();
                String newPw2 = pwBox2.getText();
                //boolean status = false;
                if (newPw1.compareTo(newPw2) == 0) loginPacket = "r:" + Username + ":" + newPw1;

                //Auth = login;
                //System.out.println(Username + " "+ status);
                // output.println(login);
                window.close();
            }
        });
        registrationGrid.add(button2, 0, 5);
        register = new Scene(registrationGrid, 400, 300);
        window.setScene(login);
        window.showAndWait();
        //Display scene 1 at first


        return loginPacket;
    }

    static String errorHandler(String error)
    {
        String errorMessage="";
        if (!error.equals("")){
            String[] temp = error.split(":");

            switch (temp[1]) {
                case "pwf":
                    errorMessage = "Error: your password is incorrect";
                    break;
                case "AR":
                    errorMessage = "Error: That username is already registered";
                    break;
                case "unf":
                    errorMessage = "Error: That username is not found, please register";
                    break;
                case "X":
                    errorMessage = "Error: user names cannot contain X";
                    break;


        }

    }
        return errorMessage;
    }
}
