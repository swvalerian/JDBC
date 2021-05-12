package com.swvalerian.crud.controller;

import com.swvalerian.crud.model.Skill;

import java.sql.SQLException;
import java.util.List;

public interface ControllerIF {
    Skill create(Integer id, String name);
    Skill read(Integer id) throws SQLException;
    Skill update(Integer id, String name) throws SQLException;
    void delete(Integer id) throws SQLException;
    List<Skill> getAll() throws SQLException;
}
