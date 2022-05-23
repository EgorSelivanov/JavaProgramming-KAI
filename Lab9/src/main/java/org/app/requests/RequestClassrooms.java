package org.app.requests;

import org.app.connection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class RequestClassrooms {
    public ArrayList<Object[]> getClassrooms() throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        ResultSet resultSet =  DBConnection.statement().executeQuery("SELECT * FROM classrooms ORDER BY id_classroom");
        return getObjects(arrayList, resultSet);
    }

    public ArrayList<Object[]> getAudience() throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        ResultSet resultSet =  DBConnection.statement().executeQuery("SELECT audience_number FROM classrooms\n" +
                "ORDER BY audience_number");

        while (resultSet.next()) {
            int audienceNumber = resultSet.getInt("audience_number");

            arrayList.add(new Object[] {audienceNumber});
        }
        return arrayList;
    }

    public void addNewClassroom(ArrayList<String> arrayList) throws SQLException {
        String sql = "INSERT INTO classrooms (building_number, audience_number, name_classroom, square, id_responsible)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(arrayList.get(0)));
        preparedStatement.setInt(2, Integer.parseInt(arrayList.get(1)));
        preparedStatement.setString(3, arrayList.get(2));
        preparedStatement.setDouble(4, Double.parseDouble(arrayList.get(3)));
        preparedStatement.setInt(5, Integer.parseInt(arrayList.get(4)));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateClassroom(ArrayList<String> arrayList) throws SQLException {
        String sql = "UPDATE classrooms SET building_number = ?, audience_number = ?, name_classroom = ?, " +
                "square = ?, id_responsible = ? \n" +
                "WHERE id_classroom = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(arrayList.get(1)));
        preparedStatement.setInt(2, Integer.parseInt(arrayList.get(2)));
        preparedStatement.setString(3, arrayList.get(3));
        preparedStatement.setDouble(4, Double.parseDouble(arrayList.get(4)));
        preparedStatement.setInt(5, Integer.parseInt(arrayList.get(5)));
        preparedStatement.setInt(6, Integer.parseInt(arrayList.get(0)));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void sbrosClassroom(int id) throws SQLException {
        String sql = "UPDATE classrooms SET building_number = 0, audience_number = 0, name_classroom = '', " +
                "square = 0, id_responsible = NULL \n" +
                "WHERE id_classroom = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void deleteClassroom(int id) throws SQLException{
        String sql = "DELETE FROM classrooms WHERE id_classroom = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ArrayList<Object[]> searchClassrooms(int search) throws SQLException {
        ArrayList<Object[]> arrayList = new ArrayList<>();

        String sql = "SELECT * FROM classrooms\n" +
                "WHERE audience_number = ? \n" +
                "OR building_number = ?";
        PreparedStatement preparedStatement = DBConnection.connection().prepareStatement(sql);
        preparedStatement.setInt(1, search);
        preparedStatement.setInt(2, search);
        ResultSet resultSet =  preparedStatement.executeQuery();

        return getObjects(arrayList, resultSet);
    }

    private ArrayList<Object[]> getObjects(ArrayList<Object[]> arrayList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id_classroom");
            int buildingNumber = resultSet.getInt("building_number");
            int audienceNumber = resultSet.getInt("audience_number");
            String nameClassroom = resultSet.getString("name_classroom");
            double square = resultSet.getDouble("square");
            int idResponsible = resultSet.getInt("id_responsible");

            arrayList.add(new Object[] {id, buildingNumber, audienceNumber, nameClassroom, square, idResponsible});
        }
        return arrayList;
    }
}
