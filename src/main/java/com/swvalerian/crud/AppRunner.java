package com.swvalerian.crud;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.repository.DeveloperRepository;
import com.swvalerian.crud.repository.JavaIODevRepImpl;
import com.swvalerian.crud.repository.SkillRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppRunner {
    public static void main(String[] args) throws SQLException {
        // тест DevRepo
        DeveloperRepository devRep = new JavaIODevRepImpl();
        System.out.println(devRep.getAll());
        System.out.println(devRep.getId(9L));

        SkillRepository skillRepository = new SkillRepository();
        List<Skill> skillList = new ArrayList<>();
        skillList.add(skillRepository.getById(1));
        skillList.add(skillRepository.getById(5));

        devRep.save(new Developer(10, "Test3", "Working", skillList));

        // devRep.deleteById(11l);


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
