package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Random;

public class TicTacToe {

    private Pane pane;//панель для рисования
    private int matrix[][] = new int[3][3];//исходная матрица
    private Path path = new Path();//траектория отрисовки фигур
    private final int x = 1;//крестики
    private final int o = 2;//нолики
    Random random;

    public TicTacToe(Pane pane, Random random) {
        this.pane = pane;
        this.random = random;
        newGame();
        addBorders();
    }

    private void newGame() {//заполнение матрицы, очистка панели, установка границ, начало игры
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = 0;
        pane.getChildren().clear();
        addBorders();
        setOnClickStartGame();
    }

    private void addBorders() {//прорисовка границ на панели
        path.getElements().clear();

        path.setStrokeWidth(5);
        //добавление поэлементно в траекторию, границы
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new LineTo(pane.getPrefWidth(), 0));
        path.getElements().add(new LineTo(pane.getPrefWidth(), pane.getPrefHeight()));
        path.getElements().add(new LineTo(0, pane.getPrefHeight()));
        path.getElements().add(new LineTo(0, 0));
        //добавление делений (квадратов)
        path.getElements().add(new MoveTo(pane.getPrefWidth() / 3, 0));
        path.getElements().add(new LineTo(pane.getPrefWidth() / 3, pane.getPrefHeight()));
        path.getElements().add(new MoveTo(pane.getPrefWidth() / 3 * 2, 0));
        path.getElements().add(new LineTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight()));
        path.getElements().add(new MoveTo(0, pane.getPrefHeight() / 3));
        path.getElements().add(new LineTo(pane.getPrefWidth(), pane.getPrefHeight() / 3));
        path.getElements().add(new MoveTo(0, pane.getPrefHeight() / 3 * 2));
        path.getElements().add(new LineTo(pane.getPrefWidth(), pane.getPrefHeight() / 3 * 2));
        //добавление созданной траектории
        pane.getChildren().remove(path);
        pane.getChildren().add(path);
    }

    private void onTie() {//ничья
        Text youWin = new Text("TIE");
        youWin.setFont(new Font(150));
        youWin.setX(0);
        youWin.setY(pane.getPrefHeight() / 2 + 75);
        youWin.setWrappingWidth(pane.getPrefWidth());
        youWin.setFill(Color.YELLOW);
        youWin.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(youWin);
        setOnClickEndGame();
    }

    private void onWin() {//добавление надписи выигрыша на панель
        Text youWin = new Text("YOU WIN");
        youWin.setFont(new Font(80));
        youWin.setX(0);
        youWin.setY(pane.getPrefHeight() / 2 + 40);
        youWin.setWrappingWidth(pane.getPrefWidth());
        youWin.setFill(Color.GREEN);
        youWin.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(youWin);
        setOnClickEndGame();//завершение игры
    }

    private void onLose() {//проигрыш
        Text youWin = new Text("YOU LOSE");
        youWin.setFont(new Font(80));
        youWin.setX(0);
        youWin.setY(pane.getPrefHeight() / 2 + 40);
        youWin.setWrappingWidth(pane.getPrefWidth());
        youWin.setFill(Color.RED);
        youWin.setTextAlignment(TextAlignment.CENTER);
        pane.getChildren().add(youWin);
        setOnClickEndGame();
    }

    private boolean isWin() {//условие проверки выигрыша (матрица заполнилась единицами)
        if ((matrix[0][0] == 1 && matrix[0][0] == matrix[0][1] && matrix[0][0] == matrix[0][2])
                || (matrix[1][0] == 1 && matrix[1][0] == matrix[1][1] && matrix[1][0] == matrix[1][2])
                || (matrix[2][0] == 1 && matrix[2][0] == matrix[2][1] && matrix[2][0] == matrix[2][2])
                || (matrix[0][0] == 1 && matrix[0][0] == matrix[1][0] && matrix[0][0] == matrix[2][0])
                || (matrix[0][1] == 1 && matrix[0][1] == matrix[1][1] && matrix[0][1] == matrix[2][1])
                || (matrix[0][2] == 1 && matrix[0][2] == matrix[1][2] && matrix[0][2] == matrix[2][2])
                || (matrix[0][0] == 1 && matrix[0][0] == matrix[1][1] && matrix[0][0] == matrix[2][2])
                || (matrix[2][0] == 1 && matrix[2][0] == matrix[1][1] && matrix[2][0] == matrix[0][2]))
            return true;
        return false;
    }

    private boolean isLose() {
        if ((matrix[0][0] == 2 && matrix[0][0] == matrix[0][1] && matrix[0][0] == matrix[0][2])    //
                || (matrix[1][0] == 2 && matrix[1][0] == matrix[1][1] && matrix[1][0] == matrix[1][2])     //
                || (matrix[2][0] == 2 && matrix[2][0] == matrix[2][1] && matrix[2][0] == matrix[2][2])
                || (matrix[0][0] == 2 && matrix[0][0] == matrix[1][0] && matrix[0][0] == matrix[2][0])
                || (matrix[0][1] == 2 && matrix[0][1] == matrix[1][1] && matrix[0][1] == matrix[2][1])     //
                || (matrix[0][2] == 2 && matrix[0][2] == matrix[1][2] && matrix[0][2] == matrix[2][2])
                || (matrix[0][0] == 2 && matrix[0][0] == matrix[1][1] && matrix[0][0] == matrix[2][2])   //
                || (matrix[2][0] == 2 && matrix[2][0] == matrix[1][1] && matrix[2][0] == matrix[0][2])) //
            return true;
        return false;
    }

    private void setOnClickEndGame() {
        pane.setOnMouseClicked(e -> {//начало новой игры по завершении старой
            newGame();
        });
    }

    private void setOnClickStartGame() {
        pane.setOnMouseClicked(e -> {//обработчик клика мыши
            boolean flag = false;
            for (int i[] : matrix)//проверка условия начала игры
                for (int j : i)
                    if (j == 0)
                        flag = true;

            if (flag) { //условие начала игры - матрица заполнена нулями
                double eX = e.getX();//положение курсора
                double eY = e.getY();

                //клетка [0][0]
                if (eX >= 0 && eX < pane.getPrefWidth() / 3 && eY >= 0 && eY < pane.getPrefHeight() / 3) {//условие попадения в клетку
                    if (matrix[0][0] == 0) {//если клетка матрицы на начальной инициализации (с нулем)
                        Path x = new Path(//отрисовка линий крестика
                                new MoveTo(0, 0),
                                new LineTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3),
                                new MoveTo(0, pane.getPrefHeight() / 3),
                                new LineTo(pane.getPrefWidth() / 3, 0)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[0][0] = this.x;//установка единицы в поле с крестиком

                        if(isWin())//проверка условия выигрыша
                            onWin();//вы выиграли!
                        else if(!haveNull())//если не выиграли, но и матрица не начальная (не заполнена нулями)
                            onTie();//ничья
                        else//иначе ход делает система
                            randTurn();//произвольный ход системы (внутри хода системы проверка на проигрыш)
                    }
                }

                //клетка [0][1]; условие попадения в клетку
                else if (eX >= pane.getPrefWidth() / 3 && eX < pane.getPrefWidth() / 3 * 2 && eY >= 0 && eY < pane.getPrefHeight() / 3) {
                    if (matrix[0][1] == 0) {
                        Path x = new Path(//отрисовка линий крестика
                                new MoveTo(pane.getPrefWidth() / 3, 0),
                                new LineTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3),
                                new MoveTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3),
                                new LineTo(pane.getPrefWidth() / 3 * 2, 0)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[0][1] = this.x;//установка единицы

                        if(isWin())//проверка условий
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [0][2]
                else if (eX >= pane.getPrefWidth() / 3 * 2 && eX < pane.getPrefWidth() && eY >= 0 && eY < pane.getPrefHeight() / 3) {
                    if (matrix[0][2] == 0) {
                        Path x = new Path(
                                new MoveTo(pane.getPrefWidth() / 3 * 2, 0),
                                new LineTo(pane.getPrefWidth(), pane.getPrefHeight() / 3),
                                new MoveTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3),
                                new LineTo(pane.getPrefWidth(), 0)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[0][2] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [1][0]
                else if (eX >= 0 && eX < pane.getPrefWidth() / 3 && eY >= pane.getPrefHeight() / 3 && eY < pane.getPrefHeight() / 3 * 2) {
                    if (matrix[1][0] == 0) {
                        Path x = new Path(
                                new MoveTo(0, pane.getPrefHeight() / 3),
                                new LineTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3 * 2),
                                new MoveTo(0, pane.getPrefHeight() / 3 * 2),
                                new LineTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[1][0] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [1][1]
                else if (eX >= pane.getPrefWidth() / 3 && eX < pane.getPrefWidth() / 3 * 2 && eY >= pane.getPrefHeight() / 3 && eY < pane.getPrefHeight() / 3 * 2) {
                    if (matrix[1][1] == 0) {
                        Path x = new Path(
                                new MoveTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3),
                                new LineTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3 * 2),
                                new MoveTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3 * 2),
                                new LineTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[1][1] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [1][2]
                else if (eX >= pane.getPrefWidth() / 3 * 2 && eX < pane.getPrefWidth() && eY >= pane.getPrefHeight() / 3 && eY < pane.getPrefHeight() / 3 * 2) {
                    if (matrix[1][2] == 0) {
                        Path x = new Path(
                                new MoveTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3),
                                new LineTo(pane.getPrefWidth(), pane.getPrefHeight() / 3 * 2),
                                new MoveTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3 * 2),
                                new LineTo(pane.getPrefWidth(), pane.getPrefHeight() / 3)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[1][2] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [2][0]
                else if (eX >= 0 && eX < pane.getPrefWidth() / 3 && eY >= pane.getPrefHeight() / 3 * 2 && eY < pane.getPrefHeight()) {
                    if (matrix[2][0] == 0) {
                        Path x = new Path(
                                new MoveTo(0, pane.getPrefHeight() / 3 * 2),
                                new LineTo(pane.getPrefWidth() / 3, pane.getPrefHeight()),
                                new MoveTo(0, pane.getPrefHeight()),
                                new LineTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3 * 2)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[2][0] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [2][1]
                else if (eX >= pane.getPrefWidth() / 3 && eX < pane.getPrefWidth() / 3 * 2 && eY >= pane.getPrefHeight() / 3 * 2 && eY < pane.getPrefHeight()) {
                    if (matrix[2][1] == 0) {
                        Path x = new Path(
                                new MoveTo(pane.getPrefWidth() / 3, pane.getPrefHeight() / 3 * 2),
                                new LineTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight()),
                                new MoveTo(pane.getPrefWidth() / 3, pane.getPrefHeight()),
                                new LineTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3 * 2)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[2][1] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else
                            randTurn();
                    }
                }

                //клетка [2][2]
                else if (eX >= pane.getPrefWidth() / 3 * 2 && eX < pane.getPrefWidth() && eY >= pane.getPrefHeight() / 3 * 2 && eY < pane.getPrefHeight()) {
                    if (matrix[2][2] == 0) {
                        Path x = new Path(
                                new MoveTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight() / 3 * 2),
                                new LineTo(pane.getPrefWidth(), pane.getPrefHeight()),
                                new MoveTo(pane.getPrefWidth() / 3 * 2, pane.getPrefHeight()),
                                new LineTo(pane.getPrefWidth(), pane.getPrefHeight() / 3 * 2)
                        );
                        x.setStrokeWidth(5);
                        pane.getChildren().add(x);
                        matrix[2][2] = this.x;

                        if(isWin())
                            onWin();
                        else if(!haveNull())
                            onTie();
                        else//
                            randTurn();
                    }
                }


            }
        });//описали обработчик события клика
    }

    private boolean haveNull() {//проверка матрицы на нули
        boolean flag = false;
        for (int i : matrix[0])//если нулями заполнена хотя бы одна строка
            if (i == 0)
                flag = true;
        for (int i : matrix[1])
            if (i == 0)
                flag = true;
        for (int i : matrix[2])
            if (i == 0)
                flag = true;
        return flag;
    }

    private void randTurn() {//ход компьютера (куда ставить нолик)
        if (haveNull()) {//если в матрице есть нули
            do {
                int i = random.nextInt(3);//рандом до 3-х не включительно
                int j = random.nextInt(3);
                if (matrix[i][j] == 0) {
                    Circle zero = new Circle();
                    zero.setFill(Color.WHITE);//отрисовка
                    zero.setStroke(Color.BLACK);//нолика
                    zero.setLayoutX(pane.getPrefWidth() / 3 * j + pane.getPrefWidth() / 3 / 2);
                    zero.setLayoutY(pane.getPrefHeight() / 3 * i + pane.getPrefWidth() / 3 / 2);
                    zero.setRadius(pane.getPrefWidth() / 3 / 2);
                    zero.setStrokeWidth(5);
                    pane.getChildren().add(zero);
                    matrix[i][j] = this.o;//нолик - 2
                    break;
                }
            } while (true);

            if (isLose())//проверка на проигрыш
                onLose();//вывод уведомления о проигрыше
        }
    }
}
