package com.swvalerian.crud.service;

import com.swvalerian.crud.model.Skill;
import org.junit.Test;

import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ServiceSkillTests {
    ServiceSkill serviceSkillTest = new ServiceSkill();
    ServiceSkill serviceSkillMock = Mockito.mock(ServiceSkill.class);

    @Test
    public void shouldReadSkill() {
        //ожидаемый возврат после запроса
        String skillReadExpected = "Skill{id=1, name='Delphi'}";
        //Второй вариант реализации теста: задаем то, что вернет затычка - ой, заглушка )))
        Skill skillActual = new Skill(1, "Delphi");

        Mockito.when(serviceSkillMock.read(1)).thenReturn(skillActual);
        assertEquals(skillReadExpected, serviceSkillMock.read(1).toString());

        /*// первый вариант
        Mockito.when(serviceSkillMock.read(1)).thenReturn(serviceSkillTest.read(1));
        assertEquals(skillReadExpected, serviceSkillMock.read(1).toString());
        */
    }

    @Test
    public void shouldGetAllSkill() {
        List<Skill> skillListExpected = serviceSkillTest.getAll();

        Mockito.when(serviceSkillMock.getAll()).thenReturn(skillListExpected);
        assertEquals(skillListExpected, serviceSkillMock.getAll());
    }

    @Test
    public void shouldUpdateSkill() {
        Skill skillUpdateExpected = new Skill(3, "Fortran");

        Mockito.when(serviceSkillMock.update(3,"Fortran")).thenReturn(new Skill(3,"Fortran"));
        assertEquals(skillUpdateExpected.toString(), serviceSkillMock.update(3,"Fortran").toString());
    }

    @Test
    public void shouldCreateSkill() {
        Skill skillCreateExpected = new Skill(10, "C#");

        Mockito.when(serviceSkillMock.create(10,"C#")).thenReturn(new Skill(10,"C#"));
        assertEquals(skillCreateExpected.toString(), serviceSkillMock.create(10,"C#").toString());
    }

    @Test
    public void shouldDeleteSkill() {

    }
}
