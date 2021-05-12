package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.repository.SkillRepository;

import java.sql.SQLException;
import java.util.List;

public class SkillController implements ControllerIF {
    // связываем наш контроллер с репозиторием, через создание обьекта
    final private SkillRepository skillRepository = new SkillRepository();

    public SkillController() {
    }

    @Override
    public Skill create(Integer id, String name) {
        skillRepository.save(new Skill(id, name));
        return new Skill(id, name);
    }

    @Override
    public Skill read(Integer id) {
       return skillRepository.getById(id);
    }

    @Override
    public Skill update(Integer id, String name) {
        skillRepository.update(new Skill(id, name));
        return new Skill(id, name);
    }

    @Override
    public void delete(Integer id) {
        skillRepository.deleteById(id);
    }

    @Override
    public List<Skill> getAll() {
        skillRepository.getAll().stream().forEach(s -> System.out.println(s.getId() + " : " +  s.getName()));
        return skillRepository.getAll();
    }
}
