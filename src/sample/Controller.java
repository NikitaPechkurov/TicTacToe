package sample;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import model.TicTacToe;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Pane pane;
    Random random;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        random = new Random();
        TicTacToe ticTacToe = new TicTacToe(pane, random);
    }
}
