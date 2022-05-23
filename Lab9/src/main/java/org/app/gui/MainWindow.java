package org.app.gui;

import org.app.connection.DBConnection;
import org.app.requests.RequestClassrooms;
import org.app.requests.RequestResponsible;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Arrays;

public class MainWindow extends JPanel implements ActionListener {
    private final int width_window = 920;
    private final int delta_size_dialog = 20;
    private final int[] sbrosResponsible = new int[] {};
    private final int[] sbrosClassroom = new int[] {2};
    private static JFrame mainFrame = null;
    private JPanel panelControl, panelFindClassroom, panelFindResponsible, panelShowClassrooms, panelShowResponsible;
    private JButton buttonShow, buttonUpdate, buttonShowAudience, buttonShowFIO, buttonSbros;
    private JButton buttonCreateResponsible;
    private JButton buttonCreateClassroom;
    private JButton buttonEditResponsible;
    private JButton buttonEditClassroom;
    private JButton buttonDelete;
    private JButton buttonFindClassroom;
    private JButton buttonFindResponsible;
    private JTextField textFieldFindClassroom;
    private JTextField textFieldFindResponsible;
    private DefaultTableModel tableShowClassroomsModel;
    private DefaultTableModel tableShowResponsibleModel;
    private JTable tableShowClassrooms;
    private JTable tableShowResponsible;
    private Object[][] data;
    private JLabel labelFindColResponsible;
    private JLabel labelFindColClassroom;

    public MainWindow() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Создание панели "Управление".
        panelControl = new JPanel();
        panelControl.setPreferredSize(new Dimension(width_window, 90));
        panelControl.setBorder(BorderFactory.createTitledBorder("\"Управление\""));
        add(Box.createRigidArea(new Dimension(0, 10))); // Отступ 10 пикселей
        panelControl.setLayout(new FlowLayout());
        buttonUpdate = new JButton("Обновить");
        buttonUpdate.addActionListener(this);
        buttonShow = new JButton("Вывести суммарные площади");
        buttonShow.addActionListener(this);
        buttonShowAudience = new JButton("Вывод аудиторий");
        buttonShowAudience.addActionListener(this);
        buttonShowFIO = new JButton("Вывод ФИО");
        buttonShowFIO.addActionListener(this);
        buttonCreateResponsible = new JButton("Создать ответственного");
        buttonCreateResponsible.addActionListener(this);
        buttonCreateClassroom = new JButton("Создать аудиторию");
        buttonCreateClassroom.addActionListener(this);
        buttonEditResponsible = new JButton("Редактировать ответственного");
        buttonEditResponsible.addActionListener(this);
        buttonEditClassroom = new JButton("Редактировать аудиторию");
        buttonEditClassroom.addActionListener(this);
        buttonDelete = new JButton("Удалить");
        buttonDelete.addActionListener(this);
        buttonSbros = new JButton("Сброс значений");
        buttonSbros.addActionListener(this);
        panelControl.add(buttonUpdate);
        panelControl.add(buttonShow);
        panelControl.add(buttonShowAudience);
        panelControl.add(buttonShowFIO);
        panelControl.add(buttonCreateResponsible);
        panelControl.add(buttonCreateClassroom);
        panelControl.add(buttonEditResponsible);
        panelControl.add(buttonEditClassroom);
        panelControl.add(buttonDelete);
        panelControl.add(buttonSbros);
        add(panelControl);

        //Создание панели "Поиск".
        panelFindClassroom = new JPanel();
        panelFindClassroom.setPreferredSize(new Dimension(width_window, 50));
        panelFindClassroom.setBorder(BorderFactory.createTitledBorder("\"Поиск по аудиториям\""));
        panelFindClassroom.setLayout(new GridLayout());
        textFieldFindClassroom = new JTextField();
        buttonFindClassroom = new JButton("Поиск аудитории");
        buttonFindClassroom.addActionListener(this);
        panelFindClassroom.add(textFieldFindClassroom);
        panelFindClassroom.add(buttonFindClassroom);
        add(Box.createRigidArea(new Dimension(0, 10))); // Отступ сверху вниз на 10 пикселей
        add(panelFindClassroom);

        //Создание панели "Учебные аудитории".
        panelShowClassrooms = new JPanel();
        panelShowClassrooms.setPreferredSize(new Dimension(width_window, 130));
        panelShowClassrooms.setLayout(new BoxLayout(panelShowClassrooms, BoxLayout.Y_AXIS));
        panelShowClassrooms.setBorder(BorderFactory.createTitledBorder("\"Учебные аудитории\""));
        add(Box.createRigidArea(new Dimension(0, 10))); // Отступ сверху вниз на 10 пикселей
        data = new Object[][]{};
        tableShowClassroomsModel = new DefaultTableModel(new Object[]{"Номер","Учебное здание",
                "Номер аудитории","Наименование","Площадь", "Номер ответственного"},0){
            // Disabling User Edits in a JTable with DefaultTableModel
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        tableShowClassrooms = new JTable();
        tableShowClassrooms.setModel(tableShowClassroomsModel);
        panelShowClassrooms.add(new JScrollPane(tableShowClassrooms));
        labelFindColClassroom = new JLabel();
        panelShowClassrooms.add(labelFindColClassroom);
        add(panelShowClassrooms);

        //Создание панели поиск по ответственным
        panelFindResponsible = new JPanel();
        panelFindResponsible.setPreferredSize(new Dimension(width_window, 50));
        panelFindResponsible.setBorder(BorderFactory.createTitledBorder("\"Поиск ответственного\""));
        panelFindResponsible.setLayout(new GridLayout());
        textFieldFindResponsible = new JTextField();
        buttonFindResponsible = new JButton("Поиск ответственного");
        buttonFindResponsible.addActionListener(this);
        panelFindResponsible.add(textFieldFindResponsible);
        panelFindResponsible.add(buttonFindResponsible);
        add(Box.createRigidArea(new Dimension(0, 10))); // Отступ сверху вниз на 10 пикселей
        add(panelFindResponsible);

        //Создание панели "Ответственные".
        panelShowResponsible = new JPanel();
        panelShowResponsible.setPreferredSize(new Dimension(width_window, 130));
        panelShowResponsible.setLayout(new BoxLayout(panelShowResponsible, BoxLayout.Y_AXIS));
        panelShowResponsible.setBorder(BorderFactory.createTitledBorder("\"Ответственные\""));
        add(Box.createRigidArea(new Dimension(0, 10)));
        tableShowResponsibleModel = new DefaultTableModel(new Object[]{"Номер","Фамилия",
                "Имя","Отчество","Должность", "Телефон", "Возраст"},0){
            // Disabling User Edits in a JTable with DefaultTableModel
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        tableShowResponsible = new JTable();
        tableShowResponsible.setModel(tableShowResponsibleModel);
        panelShowResponsible.add(new JScrollPane(tableShowResponsible));

        labelFindColResponsible = new JLabel();
        panelShowResponsible.add(labelFindColResponsible);
        add(panelShowResponsible);

        updateInformation();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Учебные аудитории");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame = frame;
        JComponent componentPanelClassrooms = new MainWindow();
        frame.setContentPane(componentPanelClassrooms);
        frame.pack();
        frame.setVisible(true);
    }

    private void updateInformation() {
        tableShowClassroomsModel.getDataVector().removeAllElements();
        RequestClassrooms requestClassrooms = new RequestClassrooms();
        try {
            ArrayList<Object[]> arrayList = requestClassrooms.getClassrooms();
            for (Object[] array: arrayList) {
                tableShowClassroomsModel.addRow(array);
            }
            labelFindColClassroom.setText("Найдено записей: " + arrayList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableShowResponsibleModel.getDataVector().removeAllElements();
        RequestResponsible getter = new RequestResponsible();
        try {
            ArrayList<Object[]> arrayList = getter.getResponsible();
            for (Object[] array: arrayList) {
                tableShowResponsibleModel.addRow(array);
            }
            labelFindColResponsible.setText("Найдено записей: " + arrayList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableShowClassrooms.clearSelection();
        tableShowResponsible.clearSelection();
    }

    private void searchInformationResponsible(String find) {
        tableShowResponsibleModel.getDataVector().removeAllElements();
        RequestResponsible getter = new RequestResponsible();
        try {
            ArrayList<Object[]> arrayList = getter.searchResponsible(find);
            for (Object[] array: arrayList) {
                tableShowResponsibleModel.addRow(array);
            }
            labelFindColResponsible.setText("Найдено записей: " + arrayList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableShowClassrooms.clearSelection();
        tableShowResponsible.clearSelection();
    }

    private void searchInformationClassroom(String find) {
        int number;
        try {
            number = Integer.parseInt(find);
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Введите целое значение!", "Message",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableShowClassroomsModel.getDataVector().removeAllElements();
        RequestClassrooms requestClassrooms = new RequestClassrooms();
        try {
            ArrayList<Object[]> arrayList = requestClassrooms.searchClassrooms(number);
            for (Object[] array: arrayList) {
                tableShowClassroomsModel.addRow(array);
            }
            labelFindColClassroom.setText("Найдено записей: " + arrayList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableShowClassrooms.clearSelection();
        tableShowResponsible.clearSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        String[] dataToResponsible = new String[7];
        Arrays.fill(dataToResponsible, "");

        String[] dataToClassroom = new String[6];
        Arrays.fill(dataToClassroom, "");

        if ("Обновить".equals(command)) {
            textFieldFindResponsible.setText("");
            textFieldFindClassroom.setText("");
            updateInformation();
            return;
        }

        if ("Поиск ответственного".equals(command)) {
            searchInformationResponsible(textFieldFindResponsible.getText().trim());
            return;
        }

        if ("Поиск аудитории".equals(command)) {
            searchInformationClassroom(textFieldFindClassroom.getText().trim());
            return;
        }

        if ("Вывод аудиторий".equals(command)) {
            Dialog dialogContact = new JDialog(mainFrame,
                    "Аудитории", JDialog.DEFAULT_MODALITY_TYPE);
            PanelAudience panelAudience = new PanelAudience();
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelAudience.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelAudience.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelAudience);
            dialogContact.setVisible(true);
        }

        if ("Вывод ФИО".equals(command)) {
            Dialog dialogContact = new JDialog(mainFrame,
                    "ФИО", JDialog.DEFAULT_MODALITY_TYPE);
            PanelFIO panelFIO = new PanelFIO();
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelFIO.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelFIO.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelFIO);
            dialogContact.setVisible(true);
        }

        if ("Сброс значений".equals(command)) {
            Savepoint save = null;
            try{
                DBConnection.connection().setAutoCommit(false);
                save = DBConnection.connection().setSavepoint();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            //сброс аудиторий
            try {
                DBConnection.connection().setAutoCommit(false);
                RequestClassrooms requestClassrooms = new RequestClassrooms();
                for (int i = 0; i < sbrosClassroom.length; i++) {
                    requestClassrooms.sbrosClassroom(sbrosClassroom[i]);
                }
                DBConnection.connection().commit();
                DBConnection.connection().setAutoCommit(true);
            } catch (SQLException | RuntimeException ex) {
                try {
                    DBConnection.connection().rollback(save);
                    DBConnection.connection().setAutoCommit(true);
                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }
                JOptionPane.showMessageDialog(this, "Ошибка при сбросе!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            //сброс ответственных
            try {
                DBConnection.connection().setAutoCommit(false);
                RequestResponsible requestResponsible = new RequestResponsible();
                for (int i = 0; i < sbrosResponsible.length; i++) {
                    requestResponsible.sbrosResponsible(sbrosResponsible[i]);
                }
                DBConnection.connection().commit();
                DBConnection.connection().setAutoCommit(true);
            } catch (SQLException | RuntimeException ex) {
                try {
                    DBConnection.connection().rollback(save);
                    DBConnection.connection().setAutoCommit(true);
                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }
                JOptionPane.showMessageDialog(this, "Ошибка при сбросе!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if ("Вывести суммарные площади".equals(command)) {
            Dialog dialogContact = new JDialog(mainFrame,
                    "Суммарные площади", JDialog.DEFAULT_MODALITY_TYPE);
            PanelSquares panelSquares = new PanelSquares();
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelSquares.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelSquares.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelSquares);
            dialogContact.setVisible(true);
        }

        if ("Создать ответственного".equals(command)) {
            JDialog dialogContact = new JDialog(mainFrame,
                    "Новый ответственный", JDialog.DEFAULT_MODALITY_TYPE);
            PanelResponsible panelResponsible = new PanelResponsible(command, dataToResponsible);
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelResponsible.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelResponsible.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelResponsible);
            dialogContact.setVisible(true);
        }

        if ("Создать аудиторию".equals(command)) {
            JDialog dialogContact = new JDialog(mainFrame,
                    "Новая аудитория", JDialog.DEFAULT_MODALITY_TYPE);
            PanelClassroom panelContact = new PanelClassroom(command, dataToClassroom);
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelContact.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelContact.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelContact);
            dialogContact.setVisible(true);
        }

        if ("Редактировать ответственного".equals(command)) {
            int selected = tableShowResponsible.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Выберите ответственного!", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (int i = 0; i < dataToResponsible.length; i++) {
                dataToResponsible[i] = String.valueOf(tableShowResponsibleModel.getValueAt(selected, i));
            }

            JDialog dialogContact = new JDialog(mainFrame,
                    "Редактирование ответственного", JDialog.DEFAULT_MODALITY_TYPE);
            PanelResponsible panelResponsible = new PanelResponsible(command, dataToResponsible);
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelResponsible.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelResponsible.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelResponsible);
            dialogContact.setVisible(true);
        }

        if ("Редактировать аудиторию".equals(command)) {
            int selected = tableShowClassrooms.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Выберите аудиторию!", "Message",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (int i = 0; i < dataToClassroom.length; i++) {
                dataToClassroom[i] = String.valueOf(tableShowClassroomsModel.getValueAt(selected, i));
            }

            JDialog dialogContact = new JDialog(mainFrame,
                    "Редактирование аудитории", JDialog.DEFAULT_MODALITY_TYPE);
            PanelClassroom panelClassroom = new PanelClassroom(command, dataToClassroom);
            dialogContact.setBounds(
                    delta_size_dialog, delta_size_dialog,
                    panelClassroom.getContactPanelWidth()+ 3*delta_size_dialog,
                    panelClassroom.getContactPanelHeight() + delta_size_dialog);
            dialogContact.add(panelClassroom);
            dialogContact.setVisible(true);
        }

        if ("Удалить".equals(command)) {
            int selectedClassroom = tableShowClassrooms.getSelectedRow();
            int selectedResponsible = tableShowResponsible.getSelectedRow();
            System.out.println(selectedClassroom + " " + selectedResponsible);
            if (selectedClassroom == -1 && selectedResponsible == -1) {
                JOptionPane.showMessageDialog(this, "Выберите строки для удаления!", "Message",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int result = JOptionPane.showConfirmDialog(this, "Вы хотите удалить выбранные строки?",
                    "Question", JOptionPane.YES_NO_CANCEL_OPTION);
            // Окна подтверждения c 2-мя параметрами
            if (result == JOptionPane.NO_OPTION) {
                tableShowClassrooms.clearSelection();
                tableShowResponsible.clearSelection();
                return;
            }

            Savepoint save = null;
            try{
                DBConnection.connection().setAutoCommit(false);
                save = DBConnection.connection().setSavepoint();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            if (selectedClassroom != -1) {
                //удаляем
                int id = Integer.parseInt(String.valueOf(tableShowClassroomsModel.getValueAt(selectedClassroom, 0)));
                RequestClassrooms requestClassrooms = new RequestClassrooms();
                try {
                    requestClassrooms.deleteClassroom(id);
                    DBConnection.connection().commit();
                } catch (SQLException ex) {
                    try {
                        DBConnection.connection().rollback(save);
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                    JOptionPane.showMessageDialog(this, "Ошибка при удалении!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (selectedResponsible != -1) {
                //Удаляем
                int id = Integer.parseInt(String.valueOf(tableShowResponsibleModel.getValueAt(selectedResponsible, 0)));
                RequestResponsible requestResponsible = new RequestResponsible();
                try {
                    requestResponsible.deleteResponsible(id);
                    DBConnection.connection().commit();
                } catch (SQLException ex) {
                    try {
                        DBConnection.connection().rollback(save);
                    } catch (SQLException exc) {
                        throw new RuntimeException(exc);
                    }
                    JOptionPane.showMessageDialog(this, "Ошибка при удалении!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try{
                DBConnection.connection().setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            tableShowClassrooms.clearSelection();
            tableShowResponsible.clearSelection();
        }

        updateInformation();
    }
}
