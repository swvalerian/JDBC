package com.swvalerian.crud.service;

import com.swvalerian.crud.service.ServiceSkill;
import org.junit.Assert;
import org.junit.Test;

//import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;

public class ServiceSkillTests {

    ServiceSkill serviceSkill = new ServiceSkill();

    @Test
    public void shouldCreateServiceSkill() {
        Assert.assertNotNull(serviceSkill);
    }

    @Test
    public void shouldReadFromDBSkill() {
        assertEquals("здесь я напишу то, что ожидаю получить от запроса","здесь будет запрос " +
                "который обратиться к МОКИТО, а МОКИТО уже будет эмулировать ответ от СУБД" );
//        assertEquals("Test", "Test");
    }
}
