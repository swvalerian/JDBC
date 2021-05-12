package com.swvalerian.crud;

import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.repository.SkillRepository;

import java.sql.SQLException;
import java.util.List;

public class AppRunner {
    public static void main(String[] args) throws SQLException {


        // тест SkillRepo
        SkillRepository skillRepository = new SkillRepository();
        List<Skill> sl = skillRepository.getAll();
        System.out.println(sl.get(1));
        System.out.println("\n " + skillRepository.getById(1));
        skillRepository.update(new Skill(1, "Java EE"));
        skillRepository.save(new Skill(21, "Basic")); // неважно какой указать, автоинкремент
        skillRepository.deleteById(20);
    }
}
