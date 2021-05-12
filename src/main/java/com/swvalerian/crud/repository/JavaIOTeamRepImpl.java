package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaIOTeamRepImpl implements TeamRepository{

    final private File file = new File("src\\main\\resources\\files\\teams.txt");

    private List<Team> getListFFTeam() {
        List<Team> teamList = new ArrayList<>();

        try (InputStream in = new FileInputStream(file);
             BufferedReader bufRead = new BufferedReader(new InputStreamReader(in))) {

            return bufRead.lines().map(s -> convertStringToTeam(s)).collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден!");
        } catch (IOException ex) {
            System.err.println("ошибка ввода - вывода");
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
