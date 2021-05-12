package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.repository.SkillRepository;
import com.swvalerian.crud.repository.JavaIODevRepImpl;

import java.sql.SQLException;
import java.util.List;

public class DevController {

    final private JavaIODevRepImpl devRep = new JavaIODevRepImpl();

    public DevController() {
    }


    public Developer create(Integer id, String firstName, String lastName) throws SQLException {
        devRep.save(new Developer(id, firstName, lastName, new SkillRepository().getAll()));
        return new Developer(id, firstName, lastName, new SkillRepository().getAll());
    }


    public Developer read(Long id) {
        System.out.println(devRep.getId(id.longValue()));
        return devRep.getId(id.longValue());
    }


    public Developer update(Integer id, String firstName, String lastName) throws SQLException {
        devRep.update(new Developer(id, firstName, lastName, new SkillRepository().getAll()));
        return new Developer(id, firstName, lastName, new SkillRepository().getAll());
    }


    public void delete(Long id) {
        devRep.deleteById(id);
    }


    public List<Developer> getAll() {
        devRep.getAll().stream().forEach(s -> System.out.println(s.getId() + " : " +  s.getFirstName() + ":" +s.getLastName() + "\n"));
        return devRep.getAll();
    }
}