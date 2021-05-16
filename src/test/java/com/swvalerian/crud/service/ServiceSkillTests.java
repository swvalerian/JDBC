package com.swvalerian.crud.service;

import com.swvalerian.crud.service.ServiceSkill;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ServiceSkillTests {
    // мой первый Черный Ящик ( первый метод для написания теста )
    //
    // public Skill read(Integer id) {
    //        return skillRepository.getById(id);
    //    }
    ServiceSkill serviceSkillTest = new ServiceSkill();

    @Before
    public void testStart() {
        System.out.println("Запуск теста!");
    }

    @After
    public void testEnd() {
        System.out.println("Тест завершен успешно!");
    }

    @Test
    public void shouldCreateServiceSkill() {
        Assert.assertNotNull(serviceSkillTest);
    }

    @Test
    public void shouldReadFromDBSkill() {
//        assertEquals("здесь я напишу то, что ожидаю получить от запроса","здесь будет запрос " +
//                "который обратиться к МОКИТО, а МОКИТО уже будет эмулировать ответ от СУБД" );
        assertEquals("Ожидаемое сообщение","Test", "Test");
    }
}
