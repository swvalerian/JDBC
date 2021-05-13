package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Skill;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Developer(id, firstName, lastName, List<Skill> skills)

public class JavaIODevRepImpl implements DeveloperRepository {
    final private File file = new File("src\\main\\resources\\files\\developers.txt");
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // устарело. драйвер подгружается автоматом
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/swvalerian";
    static final String User = "root";
    static final String Password = "QWERTgfdsa1980";

    private List<Developer> getListFFD() {
        List<Developer> devList = new ArrayList<>();
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);
            String SQL = "select Developers.Id, firstName, lastName, Skills.Id as Skill_ID, Skill from Developers\n" +
                    "join Developers_Skills \n" +
                    "on Developers.Id = Developers_Skills.Dev_Skill_Id\n" +
                    "join Skills\n" +
                    "on Skills.Id = Developers_Skills.Skill_Id\n" +
                    "order by Developers.Id;";
            // подготовили запрос
            preparedStatement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // выполняем запрос
            ResultSet rS = preparedStatement.executeQuery();
            int prevId = 1; // переменная нужна для сравнения ID тот же девелопер - тогда список Skill заполняем

            while (rS.next()) {
                List<Skill> skillList = new ArrayList<>();
                String firstName = rS.getString("firstName");
                String lastName = rS.getString("lastName");
                int id = rS.getInt("Id");

                while (id == prevId) {
                    skillList.add(new Skill(rS.getInt("Skill_Id"), rS.getString("Skill")));
                    if (rS.next()) { id = rS.getInt("Id");
                    } else { break; }
                }

                devList.add(new Developer(prevId, firstName, lastName, skillList));
                prevId = id; // изменили значение на следующее.
                // теперь нужно вернуть указатель на предыдущий маркер, т.к. при следующей итерации он вновь сдвинется
                rS.previous();
            }
            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
            rS.close();
        } catch (SQLException e) {
            System.err.println("Ошибка в работе с БД в ДевРеп -> метод getListFFD");
        }
        return devList;
    }

    @Override // реализовал JDBC
    public List<Developer> getAll() {
        return getListFFD();
    }

    @Override // реализовал JDBC автоматически
    public Developer getId(Long id) { // работа со значением Long предполагает доп изврат в плане приведения типов ))
        return getListFFD().stream().filter( s -> s.getId().equals(id.intValue())).findFirst().orElse(null);
    }

    @Override // реализовал JDBC!
    public Developer save(Developer developer) {
            writeBuf(developer);
        return developer;
    }

    // JDBC! - приватный метод, уменьшаем основной код
    private void writeBuf(Developer d) {
        // сохраним обьект - добавим его в нашу таблицу
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);

            String firstName = d.getFirstName();
            String lastName = d.getLastName();
            List<Skill> skills = d.getSkills();

            // подготовим запрос на добавление нового элемента в таблицу
            String SQL = "INSERT INTO Developers (firstName, lastName) values(?,?);"; // у нас автоинкремент, ID автоматом проставится

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            // выполняем запрос
            preparedStatement.executeUpdate();

            // теперь запишем соответствие скилов в таблицу developers_skills
            Integer id = d.getId(); // получим ID девелопера

            //
            // мне показалось что тут можно использовать пакетное управление addBatch() - надо будет потом подумать над этим
            //
            for ( int i = 0; i < skills.size(); i++) {
                SQL = "INSERT INTO Developers_Skills (Dev_Skill_Id, Skill_Id) values(?,?);"; // у нас автоинкремент, ID автоматом проставится
                preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, skills.get(i).getId());
                // выполняем запрос
                preparedStatement.executeUpdate();
            }
            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Ошибка работы с БД в методе DevRep.WriteBuf");
        }
    }


    @Override
    public List<Developer> update(Developer developer) {
        List<Developer> listDev = getListFFD();

        return listDev.stream().peek(s ->
                {
                    if (s.getId().equals(developer.getId())) {
                        s.setFirstName(developer.getFirstName());
                        s.setLastName(developer.getLastName());
                    }
                writeBuf(s); // пишем построчно в новый файл.
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);

            // подготовим запрос для удаления записи из таблицы Developers
            String SQL = "DELETE FROM Developers WHERE Id = ?;";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);
            // выполняем запрос
            preparedStatement.executeUpdate();

            // подготовим запрос для удаления записи из таблицы Developers_Skills
            SQL = "DELETE FROM Developers_Skills WHERE Dev_Skill_id = ?;";

            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, id);
            // выполняем запрос
            preparedStatement.executeUpdate();

            // закроем открытые соединения
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Ошибка работы с БД в методе DevRep.DeleteByID");
        }
    }
}
