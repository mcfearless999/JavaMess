package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Created by chrism on 6/8/17.
 */
public class historyWindow {

    static private String historyText="";

    static Stage window;
    static Scene main;
    static TextArea messageDisplay = new TextArea();

    static boolean display(String history)
    {
        window = new Stage();

        String[] textLines =history.split("`");
        GridPane messageGrid = new GridPane();
        messageDisplay.setEditable(false);
        HBox mDisplay = new HBox(5);
        //mDisplay.setAlignment(Pos.TOP_CENTER);
        mDisplay.getChildren().add(messageDisplay);
        messageGrid.add(mDisplay,0,1);
        messageGrid.setPadding(new Insets(25, 25, 25, 25));
        //Text dialog = new Text("");

        for (int i = 0; i< textLines.length; ++i)
        {
            historyText = historyText + textLines[i] +"\n";
        }

        messageDisplay.setText(historyText);


        main = new Scene(messageGrid, 480, 300);
        window.setScene(main);
        window.show();

        return true;
    }
}
