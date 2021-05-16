package com.swvalerian.crud.service;

import com.swvalerian.crud.controller.ControllerIF;
import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.repository.SkillRepository;

import java.util.List;

public class ServiceSkill implements ControllerIF {
    // создадим прослойку между контроллером и репозиторием. Это слой сервис
    final private SkillRepository skillRepository = new SkillRepository();

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
