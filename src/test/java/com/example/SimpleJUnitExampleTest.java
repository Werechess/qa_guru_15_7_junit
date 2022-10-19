package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimpleJUnitExampleTest {

    @DisplayName("Here comes the bad practise test name")
    @Test
    void simpleTest() {
        Assertions.assertEquals("Str", "Str");
        Assertions.assertTrue("Str".equals("Str"));

        Human first = new Human("Hello");
        Human second = new Human("Hello");
        Human third = new Human("World");

        Assertions.assertEquals(first, second);
//        Assertions.assertEquals(first, third);
    }

    @DisplayName("profile page should be open after click by button")
    @Test
    void profilePageShouldBeOpenAfterClickByButton() {
    }

    @Disabled("JIRA-1111")
    @Test
    void disabledTest() {
    }
}
