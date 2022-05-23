package org.app.requests;

import org.app.connection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestResponsible {
    public ArrayList<Object[]> getResponsible() throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        ResultSet resultSet =  DBConnection.statement().executeQuery("SELECT * FROM responsible ORDER BY id_responsible");
        return getObjects(arrayList, resultSet);
    }

    public ArrayList<Object[]> getFIO() throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        ResultSet resultSet =  DBConnection.statement().executeQuery("SELECT surname, \"name\", patronymic FROM responsible\n" +
                "ORDER BY surname, \"name\", patronymic");
        while (resultSet.next()) {
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");

            arrayList.add(new Object[] {surname, name, patronymic});
        }
        return arrayList;
    }

    public void addNewResponsible(ArrayList<String> arrayList) throws SQLException {
        String sql = "INSERT INTO responsible (surname, \"name\", patronymic, \"position\", phone_number, age)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setString(1, arrayList.get(0));
        preparedStatement.setString(2, arrayList.get(1));
        preparedStatement.setString(3, arrayList.get(2));
        preparedStatement.setString(4, arrayList.get(3));
        preparedStatement.setString(5, arrayList.get(4));
        preparedStatement.setShort(6, Short.parseShort(arrayList.get(5)));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void sbrosResponsible(int id) throws SQLException {
        String sql = "UPDATE responsible SET surname = '', \"name\" = '', patronymic = '', \n" +
                "                \"position\" = '', phone_number = '', age = 0 " +
                "                WHERE id_responsible = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateResponsible(ArrayList<String> arrayList) throws SQLException {
        String sql = "UPDATE responsible SET surname = ?, \"name\" = ?, patronymic = ?, " +
                "\"position\" = ?, phone_number = ?, age = ? \n" +
                "WHERE id_responsible = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setString(1, arrayList.get(1));
        preparedStatement.setString(2, arrayList.get(2));
        preparedStatement.setString(3, arrayList.get(3));
        preparedStatement.setString(4, arrayList.get(4));
        preparedStatement.setString(5, arrayList.get(5));
        preparedStatement.setShort(6, Short.parseShort(arrayList.get(6)));
        preparedStatement.setInt(7, Integer.parseInt(arrayList.get(0)));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteResponsible(int id) throws SQLException{
        String sql = "DELETE FROM responsible WHERE id_responsible = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ArrayList<Object[]> getSquaresByResponsible() throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        ResultSet resultSet =  DBConnection.statement().executeQuery("SELECT responsible.id_responsible, surname, \"name\", patronymic, " +
                "\"position\", SUM(classrooms.square) AS total_square FROM responsible\n" +
                "INNER JOIN classrooms ON responsible.id_responsible=classrooms.id_responsible\n" +
                "GROUP BY responsible.id_responsible\n" +
                "ORDER BY surname, \"name\", patronymic");
        while (resultSet.next()) {
            int id = resultSet.getInt("id_responsible");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");
            String position = resultSet.getString("position");
            Double totalSquare = resultSet.getDouble("total_square");;

            arrayList.add(new Object[] {id, surname, name, patronymic, position, totalSquare});
        }
        return arrayList;
    }

    public ArrayList<Object[]> searchResponsible(String search) throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();
        search = '%' + search + '%';

        String sql = "SELECT * FROM responsible\n" +
                "WHERE LOWER(surname) LIKE LOWER(?)\n" +
                "OR LOWER(\"name\") LIKE LOWER(?)\n" +
                "OR LOWER(patronymic) LIKE LOWER(?)\n" +
                "OR LOWER(\"position\") LIKE LOWER(?)";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setString(1, search);
        preparedStatement.setString(2, search);
        preparedStatement.setString(3, search);
        preparedStatement.setString(4, search);
        ResultSet resultSet =  preparedStatement.executeQuery();
        return getObjects(arrayList, resultSet);
    }

    private ArrayList<Object[]> getObjects(ArrayList<Object[]> arrayList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id_responsible");
            String surname = resultSet.getString("surname");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");
            String position = resultSet.getString("position");
            String phoneNumber = resultSet.getString("phone_number");
            short age = resultSet.getShort("age");

            arrayList.add(new Object[] {id, surname, name, patronymic, position, phoneNumber, age});
        }
        return arrayList;
    }
}
