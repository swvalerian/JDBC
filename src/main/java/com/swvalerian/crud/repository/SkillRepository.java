package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository {
    static private final String DATABASE_URL = "jdbc:mysql://localhost:3306/swvalerian";
    static private final String User = "root";
    static private final String Password = "QWERTgfdsa1980";

    // приватный метод, создание списка из файла, который повторяется по коду много раз.
    private List<Skill> getListFromDB() {
        List<Skill> skillList = new ArrayList<>(); // сюда поместим список всех значений

        try ( Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)){
            String SQL = "SELECT * FROM Skills";
            // подготовили запрос
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                // выполняем запрос
                ResultSet result = preparedStatement.executeQuery();

                while (result.next()) {
                    skillList.add(new Skill(result.getInt("ID"), result.getString("Skill")));
                }
                result.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в SkillRep.GetListfromDB");
        }
        return skillList;
    }

    public List<Skill> getAll() {
        return getListFromDB();
    }

    public Skill getById(Integer id) {
        return getListFromDB().stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Skill> update(Skill skills) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            Integer id = skills.getId();
            String skillName = skills.getName();

            // подготовим запрос на изменение таблицы
            String SQL = "UPDATE Skills SET Skill=? WHERE Id=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, skillName); // вместо первого знака ?
                preparedStatement.setInt(2, id); // вместо второго знака ?
                // выполняем запрос
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в методе SkillRep.UPDATE");
        }
        return getListFromDB();
    }

    public Skill save(Skill skills) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            String skillName = skills.getName();

            // подготовим запрос на добавление нового элемента в таблицу
            String SQL = "INSERT INTO Skills (Skill) values(?);"; // у нас автоинкремент, ID автоматом проставится
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, skillName);
                // выполняем запрос
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в методе SkillRep.SAVE");
        }
        return skills;
    }

    public void deleteById(Integer id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            // подготовим запрос для удаления записи из таблицы
            String SQL = "DELETE FROM Skills WHERE Id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            // выполняем запрос
            preparedStatement.executeUpdate();
        }
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в методе SkillRep.DeleteByID");
        }
    }
}