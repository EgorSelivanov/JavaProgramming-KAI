package org.app.gui;

import org.app.connection.DBConnection;
import org.app.requests.RequestResponsible;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PanelResponsible extends JPanel implements ActionListener {
    private final int width_window = 600;//Кратно трём
    private final int height_window = 286;
    private final int height_button_panel = 40;
    private final int height_gap = 3;
    private String mode;
    private String dataTo[];
    private JPanel panelUp, panelLabel, panelText, panelButton;
    private JLabel labelFirstName;
    private JLabel labelLastName;
    private JLabel labelPatronymic;
    private JLabel labelPhoneNumber;
    private JLabel labelPosition;
    private JLabel labelAge;
    private JTextField txtFieldFirstName;
    private JTextField txtFieldLastName;
    private JTextField txtFieldPatronymic;
    private JTextField txtPhoneNumber;
    private JTextField txtAge;
    private JTextField txtPosition;
    private JButton buttonApplay;
    private JButton buttonCancel;

    public PanelResponsible(String mode, String[] dataTo) {
        super();
        this.mode = mode;
        this.dataTo = dataTo;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width_window, height_window));
        panelUp = new JPanel();//Панель для размещения панелей

        panelLabel = new JPanel();
        panelText = new JPanel();
        panelButton = new JPanel();
        labelFirstName = new JLabel("Имя");
        labelLastName = new JLabel("Фамилия");
        labelPatronymic = new JLabel("Отчество");
        labelPosition = new JLabel("Должность");
        labelPhoneNumber = new JLabel("Номер тел.");
        labelAge = new JLabel("Возраст");
        txtFieldFirstName = new JTextField(dataTo[2]);
        txtFieldLastName = new JTextField(dataTo[1]);
        txtFieldPatronymic = new JTextField(dataTo[3]);
        txtPosition = new JTextField(dataTo[4]);
        txtPhoneNumber = new JTextField(dataTo[5]);
        txtAge = new JTextField(dataTo[6]);
        buttonApplay = new JButton("Принять");
        buttonApplay.addActionListener(this);
        buttonCancel = new JButton("Отменить");
        buttonCancel.addActionListener(this);
        panelUp.setPreferredSize(new Dimension(width_window, height_window
                - height_button_panel - height_gap));
        panelUp.setBorder(BorderFactory.createBevelBorder(1));
        add(panelUp);
        panelUp.setLayout(new BoxLayout(panelUp, BoxLayout.LINE_AXIS));
        panelLabel.setPreferredSize(new Dimension(width_window/3, height_window
                - height_button_panel - height_gap));
        panelLabel.setBorder(BorderFactory.createBevelBorder(1));
        panelLabel.setLayout(new GridLayout(6,1));
        panelLabel.add(labelFirstName);
        panelLabel.add(labelLastName);
        panelLabel.add(labelPatronymic);
        panelLabel.add(labelPosition);
        panelLabel.add(labelPhoneNumber);
        panelLabel.add(labelAge);
        panelText.setPreferredSize(new Dimension(2*width_window/3, height_window
                - height_button_panel - height_gap));
        panelText.setBorder(BorderFactory.createBevelBorder(1));
        panelText.setLayout(new GridLayout(6,1));
        panelText.add(txtFieldFirstName);
        panelText.add(txtFieldLastName);
        panelText.add(txtFieldPatronymic);
        panelText.add(txtPosition);
        panelText.add(txtPhoneNumber);
        panelText.add(txtAge);
        panelUp.add(panelLabel);
        panelUp.add(panelText);
        add(Box.createRigidArea(new Dimension(0, height_gap))); // Отступ 10 пикселей
        panelButton.setPreferredSize(new Dimension(width_window, height_button_panel));
        panelButton.setBorder(BorderFactory.createBevelBorder(1));
        add(panelButton);
        panelButton.setLayout(new FlowLayout());
        panelButton.add(buttonApplay);
        panelButton.add(buttonCancel);
    }

    public int getContactPanelWidth(){
        return width_window;
    }
    public int getContactPanelHeight(){
        return height_window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RequestResponsible requestResponsible = new RequestResponsible();

        String command = e.getActionCommand();
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);
        if ("Отменить".equals(command)) {
            dialog.dispose();
        }

        if ("Принять".equals(command)) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(txtFieldLastName.getText().trim());
            arrayList.add(txtFieldFirstName.getText().trim());
            arrayList.add(txtFieldPatronymic.getText().trim());
            arrayList.add(txtPosition.getText().trim());
            arrayList.add(txtPhoneNumber.getText().trim());
            arrayList.add(txtAge.getText().trim());

            try {
                Short.parseShort(arrayList.get(5));
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Введен некорректный возраст!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (arrayList.contains("")) {
                JOptionPane.showMessageDialog(this, "Введено пустое значение!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Создать ответственного".equals(mode)) {
                try {
                    DBConnection.connection().setAutoCommit(false);
                    requestResponsible.addNewResponsible(arrayList);
                    DBConnection.connection().commit();
                    DBConnection.connection().setAutoCommit(true);
                } catch (SQLException | RuntimeException ex) {
                    try {
                        DBConnection.connection().rollback();
                        DBConnection.connection().setAutoCommit(true);
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                    JOptionPane.showMessageDialog(this, "Ошибка при добавлении!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Значение добавлено!", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            if ("Редактировать ответственного".equals(mode)) {
                arrayList.add(0, dataTo[0]);

                try {
                    DBConnection.connection().setAutoCommit(false);
                    requestResponsible.updateResponsible(arrayList);
                    DBConnection.connection().commit();
                    DBConnection.connection().setAutoCommit(true);
                } catch (SQLException | RuntimeException ex) {
                    try {
                        DBConnection.connection().rollback();
                        DBConnection.connection().setAutoCommit(true);
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                    JOptionPane.showMessageDialog(this, "Ошибка при редактировании!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Значение изменено!", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
