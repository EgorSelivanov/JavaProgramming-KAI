package com.company;

import com.company.figures.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Figures extends Frame implements Observer, ActionListener, ItemListener{
    private final int width = 500;
    private final int height = 200;

    private HashMap<Integer, IFigure> mapOfFigures = new HashMap<>();
    private Color col;
    private int number = 0;

    private Frame frame;
    private Button button_start;
    private TextField tf_number_figure;
    private Choice choice_color;
    private Choice choice_figure;
    private Choice choice_speed;
    private Choice choice_run_figure;
    private TextField tf_change_number;
    private Button button_change_number;
    private TextField tf_change_speed;
    private Button button_change_speed;
    private Label label_message;
    private Label label_error;

    public Figures(){
        this.addWindowListener(new WindowAdapter2());
        //настройка frame
        frame = new Frame();
        frame.setSize(new Dimension(750,200));
        frame.setTitle("Контроль");
        frame.setLayout(new FlowLayout());
        frame.addWindowListener(new WindowAdapter2());

        //настройка выбора цвета фигуры
        choice_color = new Choice();
        choice_color.addItem("Синий");
        choice_color.addItem("Зелёный");
        choice_color.addItem("Красный");
        choice_color.addItem("Чёрный");
        choice_color.addItem("Жёлтый");
        choice_color.addItemListener(this);
        frame.add(new Label("Выберите цвет фигуры:"));
        frame.add(choice_color);

        //настройка выбора фигуры
        choice_figure = new Choice();
        choice_figure.addItem("Прямоугольник");
        choice_figure.addItem("Треугольник");
        choice_figure.addItem("Круг");
        choice_figure.addItem("Квадрат");
        choice_figure.addItem("Овал");
        frame.add(new Label("Выберите фигуру:"));
        frame.add(choice_figure);

        //настройка номера фигуры
        frame.add(new Label("Введите номер фигуры:"));
        tf_number_figure = new TextField();
        frame.add(tf_number_figure);

        //настройка выбора скорости
        choice_speed = new Choice();
        choice_speed.addItem("1");
        choice_speed.addItem("2");
        choice_speed.addItem("3");
        choice_speed.addItem("4");
        choice_speed.addItem("5");
        choice_speed.addItem("6");
        frame.add(new Label("Выберите скорость фигуры:"));
        frame.add(choice_speed);

        //настройка кнопки Пуск
        button_start = new Button("Пуск");
        button_start.setSize(new Dimension(10,40));
        button_start.setActionCommand("Start");
        button_start.addActionListener(this);
        frame.add(button_start);

        //настройка выбора запущенной фигуры
        choice_run_figure = new Choice();
        frame.add(new Label("Выберите запущенную фигуру:"));
        frame.add(choice_run_figure);

        //Смена номера
        frame.add(new Label("Смена номера, введите новый:"));
        tf_change_number = new TextField();
        frame.add(tf_change_number);

        //настройка кнопки Сменить
        button_change_number = new Button("Сменить");
        button_change_number.setSize(new Dimension(10,40));
        button_change_number.setActionCommand("ChangeNumber");
        button_change_number.addActionListener(this);
        button_change_number.setEnabled(false);
        frame.add(button_change_number);

        //Изменение скорости
        frame.add(new Label("Изменение скорости фигуры:"));
        tf_change_speed = new TextField();
        frame.add(tf_change_speed);

        //настройка кнопки Изменить
        button_change_speed = new Button("Изменить");
        button_change_speed.setSize(new Dimension(10,40));
        button_change_speed.setActionCommand("ChangeSpeed");
        button_change_speed.addActionListener(this);
        button_change_speed.setEnabled(false);
        frame.add(button_change_speed);

        //настройка сообщений об ошибках
        label_message = new Label("Ошибки:");
        frame.add(label_message);
        label_error = new Label("");
        frame.add(label_error);

        this.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                WindowDimensions.width = getWidth();
                WindowDimensions.height = getHeight();
            }
        });

        frame.setVisible(true);
        this.setSize(width, height);
        WindowDimensions.width = width;
        WindowDimensions.height = height;
        this.setVisible(true);
        this.setLocation(150, 200);
    }
    public void update(Observable o, Object arg) {
        repaint();
    }
    public void paint (Graphics g) {
        if (!mapOfFigures.isEmpty()){
            for (var pair: mapOfFigures.entrySet()){
                pair.getValue().draw(g);
            }
        }
    }
    public void itemStateChanged (ItemEvent iE) {}
    public void actionPerformed (ActionEvent aE) {
        label_error.setText("");
        String str = aE.getActionCommand();
        if (str.equals ("Start")){
            switch (choice_color.getSelectedIndex()) {
                case 0 -> col = Color.blue;
                case 1 -> col = Color.green;
                case 2 -> col = Color.red;
                case 3 -> col = Color.black;
                case 4 -> col = Color.yellow;
            }
            int num;
            try {
                num = Integer.parseInt(tf_number_figure.getText());
                tf_number_figure.setText("");
                if (mapOfFigures.containsKey(num)) {
                    label_error.setText("Такой номер уже существует!");
                    return;
                }
            }
            catch (Exception e){
                label_error.setText("Номер должен быть числом!");
                return;
            }

            if (Main.count >= Main.max_count)
            {
                label_error.setText("Создано максимальное количество фигур!");
                return;
            }
            choice_run_figure.addItem(String.valueOf(num));

            switch (choice_figure.getSelectedIndex()) {
                case 0:
                    Rect rect = new Rect(col, num, Integer.parseInt(choice_speed.getSelectedItem()));
                    rect.addObserver(this);
                    mapOfFigures.put(num, rect);
                    break;
                case 1:
                    Triangle triangle = new Triangle(col, num, Integer.parseInt(choice_speed.getSelectedItem()));
                    triangle.addObserver(this);
                    mapOfFigures.put(num, triangle);
                    break;
                case 2:
                    Ball ball = new Ball(col, num, Integer.parseInt(choice_speed.getSelectedItem()));
                    ball.addObserver(this);
                    mapOfFigures.put(num, ball);
                    break;
                case 3:
                    Square square = new Square(col, num, Integer.parseInt(choice_speed.getSelectedItem()));
                    square.addObserver(this);
                    mapOfFigures.put(num, square);
                    break;
                case 4:
                    Oval oval = new Oval(col, num, Integer.parseInt(choice_speed.getSelectedItem()));
                    oval.addObserver(this);
                    mapOfFigures.put(num, oval);
                    break;
            }

            button_change_speed.setEnabled(true);
            button_change_number.setEnabled(true);
        }
        else if (str.equals("ChangeSpeed")){
            int numOfFigure = Integer.parseInt(choice_run_figure.getSelectedItem());
            String text = tf_change_speed.getText();
            tf_change_speed.setText("");
            if (text.isEmpty())
                return;
            int newSpeed;
            try {
                newSpeed = Integer.parseInt(text);
                if (newSpeed < 0) {
                    label_error.setText("Скорость должна быть положительна!");
                    return;
                }
            }
            catch (Exception e){
                label_error.setText("Введите число!");
                tf_change_speed.setText("");
                return;
            }

            mapOfFigures.get(numOfFigure).setSpeed(newSpeed);
        }
        else if (str.equals("ChangeNumber")) {
            int numOfFigure = Integer.parseInt(choice_run_figure.getSelectedItem());
            String text = tf_change_number.getText();
            tf_change_number.setText("");
            if (text.isEmpty()) {
                label_error.setText("Введите номер фигуры!");
                return;
            }
            int newNum;
            try {
                newNum = Integer.parseInt(text);
                if (newNum < 0) {
                    label_error.setText("Номер меньше 0!");
                    return;
                }
            }
            catch (Exception e){
                label_error.setText("Введите число!");
                tf_change_speed.setText("");
                return;
            }
            if (mapOfFigures.containsKey(newNum)){
                label_error.setText("Такой номер уже существует!");
                return;
            }
            //Удаляем старый номер из choice и добавляем новый
            choice_run_figure.remove(String.valueOf(numOfFigure));
            choice_run_figure.addItem(String.valueOf(newNum));
            //Устанавливаем новый номер фигуре
            IFigure figure = mapOfFigures.get(numOfFigure);
            figure.setNumber(numOfFigure);
            //Обновляем HashMap
            mapOfFigures.remove(numOfFigure);
            mapOfFigures.put(newNum, figure);
        }
        repaint();
    }
}
