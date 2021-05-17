package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JavaIODevRepImpl implements DeveloperRepository {
    static private final String DATABASE_URL = "jdbc:mysql://localhost:3306/swvalerian";
    static private final String User = "root";
    static private final String Password = "QWERTgfdsa1980";

    private List<Developer> getListFFD() {
        List<Developer> devList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            String SQL = "select Developers.Id, firstName, lastName, Skills.Id as Skill_ID, Skill from Developers\n" +
                    "join Developers_Skills \n" +
                    "on Developers.Id = Developers_Skills.Dev_Skill_Id\n" +
                    "join Skills\n" +
                    "on Skills.Id = Developers_Skills.Skill_Id\n" +
                    "order by Developers.Id;";
            // подготовили запрос
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                // выполняем запрос
                ResultSet rS = preparedStatement.executeQuery();
                int prevId = 1; // для сравнения ID, если тот же девелопер, тогда список Skill заполняем дальше

                while (rS.next()) {
                    List<Skill> skillList = new ArrayList<>();
                    String firstName = rS.getString("firstName");
                    String lastName = rS.getString("lastName");
                    int id = rS.getInt("Id");

                    while (id == prevId) {
                        skillList.add(new Skill(rS.getInt("Skill_Id"), rS.getString("Skill")));
                        if (rS.next()) {
                            id = rS.getInt("Id");
                        } else {
                            break;
                        }
                    }

                    devList.add(new Developer(prevId, firstName, lastName, skillList));
                    prevId = id; // изменили значение на следующее.
                    // теперь нужно вернуть указатель на предыдущий маркер, т.к. при следующей итерации он вновь сдвинется
                    rS.previous();
                }
                rS.close();
            }
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

    // JDBC! - приватный метод
    private void writeBuf(Developer d) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            String firstName = d.getFirstName();
            String lastName = d.getLastName();
            List<Skill> skills = d.getSkills();
            int Dev_Skill_Id;

            // подготовим запрос на добавление нового элемента в таблицу
            String SQL = "INSERT INTO Developers (firstName, lastName) values(?,?);"; // у нас автоинкремент, ID автоматом проставится

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.executeUpdate();

                SQL = "SELECT last_insert_id();"; // надо узнать номер инкремента, чтоб корректно добавить ID в Dev_Skills
                ResultSet resultSet = preparedStatement.executeQuery(SQL);
                resultSet.next();
                Dev_Skill_Id = resultSet.getInt(1); // получили ID вновь добавленного девелопера
            }

            // теперь запишем соответствие скилов в таблицу developers_skills
            SQL = "INSERT INTO Developers_Skills (Dev_Skill_Id, Skill_Id) values(?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                for (int i = 0; i < skills.size(); i++) {
                    preparedStatement.setInt(1, Dev_Skill_Id);
                    preparedStatement.setInt(2, skills.get(i).getId());
                    preparedStatement.addBatch(); // пишем запросы в пакет
                }
                preparedStatement.executeBatch(); // пакетный запуск запросов.
            }
        } catch (SQLException e) {
            System.err.println("Ошибка работы с БД в методе DevRep.WriteBuf");
        }
    }

    @Override
    public List<Developer> update(Developer developer) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            // подготовим запрос для изменения записи в таблице
            String SQL = "UPDATE Developers SET firstName=?, lastName=? WHERE Id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setString(1, developer.getFirstName());
                preparedStatement.setString(2, developer.getLastName());
                preparedStatement.setInt(3, developer.getId());
                // выполняем запрос
                preparedStatement.executeUpdate();
            }

            // теперь нам надо изменить данные о Skills - тут по хитрому поступим.
            // Сначало сотрем все старые данные об умениях (и неважно где они в таблице)
            // а после добавим новые данные, в конец таблицы, тоже не важно - где они хранятся.
            SQL = "DELETE FROM Developers_Skills WHERE Dev_Skill_id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setLong(1, developer.getId());
                // удаляем
                preparedStatement.executeUpdate();
            }

            //а теперь добавим данные об умениях в таблицу Dev_Skill
            List<Skill> skills = developer.getSkills(); // получим список

            SQL = "INSERT INTO Developers_Skills (Dev_Skill_Id, Skill_Id) values(?,?);";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                for (int i = 0; i < skills.size(); i++) {
                    preparedStatement.setInt(1, developer.getId());
                    preparedStatement.setInt(2, skills.get(i).getId());
                    preparedStatement.addBatch(); // пишем запросы в пакет
                }
                preparedStatement.executeBatch(); // пакетный запуск запросов.
            }
        } catch (SQLException e) {
            System.err.println("Ошибка работы с БД в методе DevRep.UpDate");
        }
        return getListFFD();
    }

    @Override // JDBC работает!
    public void deleteById(Long id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, User, Password)) {
            // подготовим запрос для удаления записи из таблицы
            String SQL = "DELETE FROM Developers WHERE Id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setLong(1, id);
                // выполняем запрос
                preparedStatement.executeUpdate();
            }

            // подготовим запрос для удаления записей из таблицы Developers_Skills
            SQL = "DELETE FROM Developers_Skills WHERE Dev_Skill_id = ?;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
                preparedStatement.setLong(1, id);
                // выполняем запрос
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка работы с БД в методе DevRep.DeleteByID");
        }
    }
}
