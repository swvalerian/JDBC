package com.swvalerian.crud.service;

import com.swvalerian.crud.model.Developer;
import com.swvalerian.crud.model.Team;
import com.swvalerian.crud.repository.JavaIOTeamRepImpl;
import com.swvalerian.crud.repository.TeamRepository;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.InOrder.*;

public class ServiceTeamTests {
    ServiceTeam serviceTeamMockito = Mockito.mock(ServiceTeam.class);

    @Test
    public void shouldGetAllTeam() {
        List<Team> teamListExpected = new ServiceTeam().getAll();

        Mockito.when(serviceTeamMockito.getAll()).thenReturn(new JavaIOTeamRepImpl().getAll());

        assertEquals(teamListExpected.toString(),serviceTeamMockito.getAll().toString());
    }

    @Test
    public void shouldCreateTeam() {
        List<Developer> developersList = new ServiceDeveloper().getAll();
        Team teamCreateExpected = new Team(5, "Memory", developersList);

        Mockito.when(serviceTeamMockito.create(5, "Memory")).thenReturn(new Team(5, "Memory", developersList));
        assertEquals(teamCreateExpected.toString(), serviceTeamMockito.create(5, "Memory").toString());
    }

    @Test
    public void shouldUpdateTeam() {
        List<Developer> developersList = new ServiceDeveloper().getAll();
        Team teamUpdateExpected = new Team(3, "puhska", developersList);

        Mockito.when(serviceTeamMockito.update(3, "puhska")).thenReturn(new Team(3, "puhska", developersList));
        assertEquals(teamUpdateExpected.toString(), serviceTeamMockito.update(3, "puhska").toString());
    }

    @Test
    public void shouldReadTeam() {
        List<Developer> developersList = new ServiceDeveloper().getAll();
        Team teamReadExpected = new Team(6, "Vesna", developersList);

        Mockito.when(serviceTeamMockito.read(6)).thenReturn(new Team(6, "Vesna", developersList));
        assertEquals(teamReadExpected.toString(), serviceTeamMockito.read(6).toString());
    }

    @Test
    public void shouldDeleteTeam() {

       /* JavaIOTeamRepImpl teamRepMackito = Mockito.mock(JavaIOTeamRepImpl.class);

        Mockito.verify(serviceTeamMockito).delete(1);

        Mockito.doReturn(NullPointerException.class).doNothing().when(serviceTeamMockito).delete(1);

        serviceTeamMockito.delete(1);
        assertEquals(,serviceTeamMockito.delete(1));*/

    }
}
