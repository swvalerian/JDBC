package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.service.ServiceDeveloper;

import java.sql.SQLException;
import java.util.List;

public class DevController {

    final private ServiceDeveloper serviceDeveloper = new ServiceDeveloper();

    public DevController() {
    }


    public Developer create(Integer id, String firstName, String lastName) throws SQLException {
        serviceDeveloper.create(id, firstName, lastName);
        return serviceDeveloper.create(id, firstName, lastName);
    }


    public Developer read(Long id) {
        System.out.println(serviceDeveloper.read(id));
        return serviceDeveloper.read(id);
    }


    public Developer update(Integer id, String firstName, String lastName) throws SQLException {
        serviceDeveloper.update(id, firstName, lastName);
        return serviceDeveloper.update(id, firstName, lastName);
    }


    public void delete(Long id) {
        serviceDeveloper.delete(id);
    }


    public List<Developer> getAll() {
        serviceDeveloper.getAll().stream().forEach(s -> System.out.println(s.getId() + " : " +  s.getFirstName() + ":" +s.getLastName() + "\n"));
        return serviceDeveloper.getAll();
    }
}