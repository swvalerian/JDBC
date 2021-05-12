package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Team;
import com.swvalerian.crud.repository.JavaIODevRepImpl;
import com.swvalerian.crud.repository.JavaIOTeamRepImpl;

import java.util.List;

public class TeamController {
    final private JavaIOTeamRepImpl teamCon = new JavaIOTeamRepImpl();

    public TeamController() {
    }


    public Team create(Integer id, String name) {
        teamCon.save(new Team(id, name, new JavaIODevRepImpl().getAll()));
        return new Team(id, name, new JavaIODevRepImpl().getAll());
    }


    public Team read(Integer id) {
        System.out.println(teamCon.getId(id.longValue()));
        return teamCon.getId(id.longValue());
    }


    public Team update(Integer id, String name) {
        teamCon.update(new Team(id, name, new JavaIODevRepImpl().getAll()));
        return new Team(id, name, new JavaIODevRepImpl().getAll());
    }


    public void delete(Integer id) {
        teamCon.deleteById(id.longValue());
    }


    public List<Team> getAll() {
        teamCon.getAll().stream().forEach(s -> System.out.println(s.getId() + " : " +  s.getName()));
        return teamCon.getAll();
    }
}
