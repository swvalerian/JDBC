package com.swvalerian.crud;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.model.Team;
import com.swvalerian.crud.repository.DeveloperRepository;
import com.swvalerian.crud.repository.JavaIODevRepImpl;
import com.swvalerian.crud.repository.JavaIOTeamRepImpl;
import com.swvalerian.crud.repository.SkillRepository;
import com.swvalerian.crud.view.MainView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppRunner {
    public static void main(String[] args) throws SQLException {

        MainView mainView = new MainView();

        mainView.showAll();

        /*JavaIOTeamRepImpl teamRep = new JavaIOTeamRepImpl();

        List<Team> teamList = teamRep.getAll();

        // test1
        System.out.println("\nПроверка метода getAll");
        for (Team team:teamList) {
            System.out.println(team + "\n");
        }

        // test2
        System.out.println("\nПроверим метод getId = 1 -> " + teamRep.getId(1l));

        // test3
        System.out.println("\nПроверим метод save");

        List<Developer> developerList = new JavaIODevRepImpl().getAll();
        developerList.remove(1);
        developerList.remove(5);


        Team team = new Team(10,"", developerList);// неважно каким будет ID
        // teamRep.save(team);
        teamRep.deleteById(12l);
*/
        /*System.out.println(teamRep.getId(11l));

        System.out.println("Проверим метод - удаление: ");
        teamRep.deleteById(11l);

        System.out.println(teamRep.getId(10l));*/

        // test4


        /*
        // тест DevRepo
        DeveloperRepository devRep = new JavaIODevRepImpl();
        System.out.println(devRep.getAll());
        System.out.println(devRep.getId(9L));

        SkillRepository skillRepository = new SkillRepository();
        List<Skill> skillList = new ArrayList<>();
        skillList.add(skillRepository.getById(20));

        devRep.update(new Developer(4,"Pasha", "Sorokin", skillList)); // работает
        System.out.println(devRep.getId(4l)); // работает

        // devRep.save(new Developer(25, "Danil", "Markov", skillList)); // работает, неважно что в ID - т.к. автоинкремент! и я это учел!
        // devRep.deleteById(10l); // работает
        */


        // тест SkillRepo
        /*SkillRepository skillRepository = new SkillRepository();
        List<Skill> sl = skillRepository.getAll();
        System.out.println(sl.get(1));
        System.out.println("\n " + skillRepository.getById(1));
        skillRepository.update(new Skill(1, "Java EE"));
        skillRepository.save(new Skill(21, "Basic")); // неважно какой указать, автоинкремент
        skillRepository.deleteById(20);*/
    }
}
