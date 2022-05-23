package org.app.gui;

import org.app.connection.DBConnection;
import org.app.requests.RequestClassrooms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PanelClassroom extends JPanel implements ActionListener {
    private final int width_window = 600;//Кратно трём
    private final int height_window = 286;
    private final int height_button_panel = 40;
    private final int height_gap = 3;
    private String mode;
    private String dataTo[];
    private JPanel panelUp, panelLabel, panelText, panelButton;
    private JLabel labelBuilding;
    private JLabel labelAudienceNumber;
    private JLabel labelName;
    private JLabel labelSquare;
    private JLabel labelResponsible;
    private JTextField txtBuilding;
    private JTextField txtAudienceNumber;
    private JTextField txtName;
    private JTextField txtSquare;
    private JTextField txtResponsible;
    private JButton buttonApplay;
    private JButton buttonCancel;

    public PanelClassroom(String mode, String[] dataTo) {
        super();
        this.mode = mode;
        this.dataTo = dataTo;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width_window, height_window));
        panelUp = new JPanel();//Панель для размещения панелей

        panelLabel = new JPanel();
        panelText = new JPanel();
        panelButton = new JPanel();
        labelBuilding = new JLabel("Учебное здание");
        labelAudienceNumber = new JLabel("Номер аудитории");
        labelName = new JLabel("Наименование");
        labelSquare = new JLabel("Площадь");
        labelResponsible = new JLabel("Номер ответственного");
        txtBuilding = new JTextField(dataTo[1]);
        txtAudienceNumber = new JTextField(dataTo[2]);
        txtName = new JTextField(dataTo[3]);
        txtSquare = new JTextField(dataTo[4]);
        txtResponsible = new JTextField(dataTo[5]);
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
        panelLabel.setLayout(new GridLayout(5,1));
        panelLabel.add(labelBuilding);
        panelLabel.add(labelAudienceNumber);
        panelLabel.add(labelName);
        panelLabel.add(labelSquare);
        panelLabel.add(labelResponsible);
        panelText.setPreferredSize(new Dimension(2*width_window/3, height_window
                - height_button_panel - height_gap));
        panelText.setBorder(BorderFactory.createBevelBorder(1));
        panelText.setLayout(new GridLayout(5,1));
        panelText.add(txtBuilding);
        panelText.add(txtAudienceNumber);
        panelText.add(txtName);
        panelText.add(txtSquare);
        panelText.add(txtResponsible);
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
        RequestClassrooms requestClassrooms = new RequestClassrooms();
        String command = e.getActionCommand();
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);
        if ("Отменить".equals(command)) {
            dialog.dispose();
        }
        if ("Принять".equals(command)) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(txtBuilding.getText().trim());
            arrayList.add(txtAudienceNumber.getText().trim());
            arrayList.add(txtName.getText().trim());
            arrayList.add(txtSquare.getText().trim());
            arrayList.add(txtResponsible.getText().trim());

            try {
                Integer.parseInt(arrayList.get(0));
                Integer.parseInt(arrayList.get(1));
                Double.parseDouble(arrayList.get(3));
                Integer.parseInt(arrayList.get(4));
            } catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Введены некорректные данные!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (arrayList.contains("")) {
                JOptionPane.showMessageDialog(this, "Введено пустое значение!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if ("Создать аудиторию".equals(mode)) {
                try {
                    DBConnection.connection().setAutoCommit(false);
                    requestClassrooms.addNewClassroom(arrayList);
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

            if ("Редактировать аудиторию".equals(mode)) {
                arrayList.add(0, dataTo[0]);

                try {
                    DBConnection.connection().setAutoCommit(false);
                    requestClassrooms.updateClassroom(arrayList);
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
