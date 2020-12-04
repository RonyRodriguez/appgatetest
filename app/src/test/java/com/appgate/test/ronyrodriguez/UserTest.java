package com.appgate.test.ronyrodriguez;

import com.appgate.test.ronyrodriguez.datasource.local.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testUserName() {
        User user = new User("appgate@gmail.com", "Prueba1");
        assertEquals("appgate@gmail.com", user.userName);
    }

    @Test
    public void testPassword() {
        User user = new User("appgate@gmail.com", "Pruebas1");
        assertEquals("Pruebas1", user.password);
    }

}
