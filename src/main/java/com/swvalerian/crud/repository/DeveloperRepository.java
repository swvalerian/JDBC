package com.swvalerian.crud.repository;

import com.swvalerian.crud.model.Developer;

// Developer(id, firstName, lastName, List<Skill> skills)
public interface DeveloperRepository extends GenericRepository<Developer, Long> {

}
