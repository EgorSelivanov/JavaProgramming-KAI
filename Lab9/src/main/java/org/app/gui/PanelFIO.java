package org.app.gui;

import org.app.requests.RequestClassrooms;
import org.app.requests.RequestResponsible;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PanelFIO extends JPanel implements ActionListener {
    private final int width_window = 900;//Кратно трём
    private final int height_window = 286;
    private final int height_button_panel = 40;
    private final int height_gap = 3;
    private JPanel panelUp, panelLabel, panelButton;
    private JLabel labelFindCol;
    private JButton buttonCancel;
    private DefaultTableModel tableShowModel;
    private JTable tableShow;

    public PanelFIO() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(width_window, height_window));
        panelUp = new JPanel();//Панель для размещения панелей

        //Создание панели "Ответственные".
        panelLabel = new JPanel();
        panelButton = new JPanel();
        panelLabel.setPreferredSize(new Dimension(width_window, 260));
        panelLabel.setLayout(new BoxLayout(panelLabel, BoxLayout.Y_AXIS));
        panelLabel.setBorder(BorderFactory.createTitledBorder("\"Результат\""));
        add(Box.createRigidArea(new Dimension(0, 10)));
        tableShowModel = new DefaultTableModel(new Object[]{"Фамилия", "Имя", "Отчество"},0){
            // Disabling User Edits in a JTable with DefaultTableModel
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        tableShow = new JTable();
        tableShow.setModel(tableShowModel);
        panelLabel.add(new JScrollPane(tableShow));


        labelFindCol = new JLabel();
        panelLabel.add(labelFindCol);
        add(panelLabel);
        buttonCancel = new JButton("Вернуться");
        buttonCancel.addActionListener(this);

        panelUp.setPreferredSize(new Dimension(width_window, height_window
                - height_button_panel - height_gap));
        panelUp.setBorder(BorderFactory.createBevelBorder(1));
        add(panelUp);
        panelUp.setLayout(new BoxLayout(panelUp, BoxLayout.LINE_AXIS));
        panelLabel.setPreferredSize(new Dimension(width_window/3, height_window
                - height_button_panel - height_gap));
        panelLabel.setBorder(BorderFactory.createBevelBorder(1));
        panelLabel.setLayout(new GridLayout(2,1));

        panelUp.add(panelLabel);

        panelButton.setPreferredSize(new Dimension(width_window, height_button_panel));
        panelButton.setBorder(BorderFactory.createBevelBorder(1));
        add(panelButton);
        panelButton.setLayout(new FlowLayout());
        panelButton.add(buttonCancel);

        add(Box.createRigidArea(new Dimension(0, height_gap))); // Отступ 10 пикселей

        updateInformation();
    }

    public int getContactPanelWidth(){
        return width_window;
    }
    public int getContactPanelHeight(){
        return height_window;
    }

    private void updateInformation() {
        tableShowModel.getDataVector().removeAllElements();

        RequestResponsible getter = new RequestResponsible();
        try {
            ArrayList<Object[]> arrayList = getter.getFIO();
            for (Object[] array: arrayList) {
                tableShowModel.addRow(array);
            }
            labelFindCol.setText("Найдено записей: " + arrayList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor(this);
        if ("Вернуться".equals(command)) {
            dialog.dispose();
        }
    }
}



