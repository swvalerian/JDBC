package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Team;
import com.swvalerian.crud.repository.JavaIODevRepImpl;
import com.swvalerian.crud.repository.JavaIOTeamRepImpl;
import com.swvalerian.crud.service.ServiceTeam;

import java.util.List;

public class TeamController {
    final private ServiceTeam serviceTeam = new ServiceTeam();

    public TeamController() {
    }

    public Team create(Integer id, String name) {
        serviceTeam.create(id, name);
        return new Team(id, name, new JavaIODevRepImpl().getAll());
    }

    public Team read(Integer id) {
        System.out.println(serviceTeam.read(id));
        return serviceTeam.read(id);
    }

    public Team update(Integer id, String name) {
        return serviceTeam.update(id, name);
    }

    public void delete(Integer id) {
        serviceTeam.delete(id);
    }

    public List<Team> getAll() {
        serviceTeam.getAll().stream().forEach(s -> System.out.println(s.getId() + " : " +  s.getName()));
        return serviceTeam.getAll();
    }
}
