package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Skill;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepository {
    // удалить потом файл, когда все переделаю
    final private File file = new File("src\\main\\resources\\files\\skills.txt");
    // а вот и "связка" репозитория с "БД"
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //     "jdbc:mysql://localhost:3306/?user=root"
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/swvalerian";
    // User $ Password
    static final String User = "root";
    static final String Password = "QWERTgfdsa1980";

    // приватный метод, создание списка из файла, который повторяется по коду много раз.
    private List<Skill> getListFromDB() {
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов
        List<Skill> skillList = new ArrayList<>(); // сюда поместим список всех значений

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);

            String SQL = "SELECT * FROM Skills";
            // подготовили запрос
            preparedStatement = connection.prepareStatement(SQL);
            // выполняем запрос
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                skillList.add(new Skill(result.getInt("ID"), result.getString("Skill")));
            }
            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
            result.close();
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД");
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
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);

            Integer id = skills.getId();
            String skillName = skills.getName();

            // подготовим запрос на изменение таблицы
            String SQL = "UPDATE Skills SET Skill=? WHERE Id=?;";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, skillName); // вместо первого знака ?
            preparedStatement.setInt(2, id); // вместо второго знака ?
            // выполняем запрос
            preparedStatement.executeUpdate();
            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в методе UPDATE");
        }
        return getListFromDB();
    }

    public Skill save(Skill skills) {
        // сохраним обьект - добавим его в нашу таблицу
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);

            String skillName = skills.getName();

            // подготовим запрос на добавление нового элемента в таблицу
            String SQL = "INSERT INTO Skills (Skill) values(?);"; // у нас автоинкремент, ID автоматом проставится

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, skillName);

            // выполняем запрос
            preparedStatement.executeUpdate();

            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в методе SAVE");
        }
        return skills;
    }

    public void deleteById(Integer id) {
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);

            // подготовим запрос для удаления записи из таблицы
            String SQL = "DELETE FROM Skills WHERE Id = ?;";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);

            // выполняем запрос
            preparedStatement.executeUpdate();

            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Ошибка соединения с БД в методе DeleteByID");
        }
    }
}