package org.lievasoft.garden.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

class StandardTests {

    @BeforeAll
    static void initAll() {
        System.out.println("INIT ALL");
    }

    @BeforeEach
    void init() {
        System.out.println("before each");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tear down");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("TEAR DOWN ALL");
    }

    @Nested
    @DisplayName("Standard nested class")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class StandartNestedTests {

        @BeforeAll
        void initAll() {
            System.out.println("INIT ALL INNER");
        }
    
        @BeforeEach
        void init() {
            System.out.println("before each inner class");
        }

        @AfterEach
        void tearDown() {
            System.out.println("tear down inner class");
        }

        @AfterAll
        void tearDownAll() {
            System.out.println("TEAR DOWN ALL INNER");
        }
        
        @Test
        void succeedingTest() {
            System.out.println("test one inner");
        }

        @Test
        void succeedingTestTwo() {
            System.out.println("test two inner");
        }
    }

    @Test
    void succeedingTestfour() {
        System.out.println("test four");
    }

    @Test
    void succeedingTestFive() {
        System.out.println("test five");
    }

    @Test
    void succeedingTestSix() {
        System.out.println("test six");
    }
/*
    @Test
    void failingTest() {
        fail("a failing test");
    }
*/
/*
    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }*/
/*
    @Test
    void abortedTest() {
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }*/
}
