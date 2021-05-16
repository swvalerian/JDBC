package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.service.ServiceSkill;

import java.util.List;

public class SkillController implements ControllerIF {
    // связываем наш контроллер с репозиторием, через создание обьекта
    final private ServiceSkill serviceSkill = new ServiceSkill();

    public SkillController() {
    }

    @Override
    public Skill create(Integer id, String name) {
        return serviceSkill.create(id, name);
    }

    @Override
    public Skill read(Integer id) {
       return serviceSkill.read(id);
    }

    @Override
    public Skill update(Integer id, String name) {
        return serviceSkill.update(id, name);
    }

    @Override
    public void delete(Integer id) {
        serviceSkill.delete(id);
    }

    @Override
    public List<Skill> getAll() {
        serviceSkill.getAll().stream().forEach(s -> System.out.println(s.getId() + " : " +  s.getName()));
        return serviceSkill.getAll();
    }
}
