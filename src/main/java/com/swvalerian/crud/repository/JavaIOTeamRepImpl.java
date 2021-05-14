package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.model.Team;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Team(id, name, List<Developer> developers)
public class JavaIOTeamRepImpl implements TeamRepository{
    final private File file = new File("src\\main\\resources\\files\\teams.txt");
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // устарело. драйвер подгружается автоматом
    static private final String DATABASE_URL = "jdbc:mysql://localhost:3306/swvalerian";
    static private final String User = "root";
    static private final String Password = "QWERTgfdsa1980";

    private List<Team> getListFFTeam() {
        List<Team> teamList = new ArrayList<>();

        List<Developer> developerList = new ArrayList<>(); // этот список постоянно будет изменяться, возможно надо будет перенести в метод ниже

        Connection connection = null; // Интерфейс для соединения с менеджером драйверов БД
        PreparedStatement preparedStatement = null; // А этот содержит методы подтверждения запросов

        try {
            connection = DriverManager.getConnection(DATABASE_URL, User, Password);
            String SQL = "select * from team";
            // подготовили запрос
            preparedStatement = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

        return teamList;
    }

    private Team convertStringToTeam(String s) {
        s = s.substring(0, s.length()-1); // получили строку без последнего знака "/"
        String[] str = s.split(",");

        List<Developer> devList = new ArrayList<>();
        int i = 2;
        while (i < str.length) {
            devList.add(new JavaIODevRepImpl().getId(Long.decode(str[i])));
            i++;
        }

        String name = str[1];
        Integer id = Integer.decode(str[0]);
        return new Team(id, name, devList);
    }

    @Override
    public List<Team> getAll() {
        return getListFFTeam();
    }

    @Override
    public Team getId(Long id) {
        return getListFFTeam().stream().filter( s -> s.getId().equals(id.intValue())).findFirst().orElse(null);
    }

    @Override
    public List<Team> update(Team team) {
        List<Team> teamList = getListFFTeam();

        file.delete();

        return teamList.stream().peek( s ->
        {
            if (s.getId().equals(team.getId())) {
                s.setName(team.getName());
            }
            writeBuf(s);
        }).collect(Collectors.toList());
    }

    // приватный метод, уменьшаем основной код
    private void writeBuf(Team team) {
        try (OutputStream out = new FileOutputStream(file, true);
             BufferedWriter bufWrite = new BufferedWriter(new OutputStreamWriter(out)))
        {
            bufWrite.write(convertTeamToString(team)); // получаем наш формат строки в файле, куда и будет сделана запись
            bufWrite.flush(); // из буфера в файл за раз!

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertTeamToString(Team team) {
        return team.getId().toString() + "," + team.getName() + "/" +"\n";
    }

    @Override
    public Team save(Team team) {
        writeBuf(team);
        return team;
    }

    @Override
    public void deleteById(Long id) {
        List<Team> teamList = getListFFTeam();
        teamList.removeIf(s -> s.getId().equals(id.intValue()));

        file.delete();

        teamList.forEach(this::writeBuf);
    }
}
