package com.appgate.test.ronyrodriguez;

import com.appgate.test.ronyrodriguez.datasource.local.Attempt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttemptTest {

    @Test
    public void tesDate() {
        Attempt attempt = new Attempt(1L, "2010-10-10 10:10", "is valid");
        assertEquals("2010-10-10 10:10", attempt.date);
    }
}
