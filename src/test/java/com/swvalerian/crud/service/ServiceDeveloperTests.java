package com.swvalerian.crud.service;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Skill;
import com.swvalerian.crud.repository.SkillRepository;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;

import java.util.List;

public class ServiceDeveloperTests {
    ServiceDeveloper serviceDeveloperTest = new ServiceDeveloper();
    ServiceDeveloper serviceDeveloperMock = Mockito.mock(ServiceDeveloper.class);

    @Test
    public void shouldGetAllDeveloper() {
        List<Developer> developerGetAllExpected = serviceDeveloperTest.getAll();

        Mockito.when(serviceDeveloperMock.getAll()).thenReturn(serviceDeveloperTest.getAll());
        assertEquals(developerGetAllExpected.toString(), serviceDeveloperMock.getAll().toString());
    }

    @Test
    public void shouldCreateDeveloper() {
        List<Skill> skillListTest = new SkillRepository().getAll();

        Developer developerCreateExpected = new Developer(44,"Arkadiy", "Nezabudkin", skillListTest);

        Mockito.when(serviceDeveloperMock.create(44,"Arkadiy", "Nezabudkin")).thenReturn(new Developer(44,"Arkadiy", "Nezabudkin", skillListTest));

        assertEquals(developerCreateExpected.toString(), serviceDeveloperMock.create(44, "Arkadiy", "Nezabudkin").toString());

    }

    @Test
    public void shouldReadDeveloper() {
        List<Skill> skillListTest = new SkillRepository().getAll();
        Developer developerReadExpected = new Developer(35,"Aleksey", "Petrov", skillListTest);

        Mockito.when(serviceDeveloperMock.read(23l)).thenReturn(new Developer(35,"Aleksey", "Petrov", skillListTest));

        assertEquals(developerReadExpected.toString(), serviceDeveloperMock.read(23l).toString());
    }

    @Test
    public void shouldUpdateDeveloper() {
        List<Skill> skillListTest = new SkillRepository().getAll();
        Developer developerUpdateExpected = new Developer(10,"Zina", "Chaikina", skillListTest);

        Mockito.when(serviceDeveloperMock.update(10,"Zina", "Chaikina")).thenReturn( new Developer(10,"Zina", "Chaikina", skillListTest));

        assertEquals(developerUpdateExpected.toString(), serviceDeveloperMock.update(10,"Zina", "Chaikina").toString());

    }
}
