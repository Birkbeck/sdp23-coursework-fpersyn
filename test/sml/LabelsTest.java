package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.NoSuchElementException;

public class LabelsTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        var factory = new ClassPathXmlApplicationContext("beans.xml");
        machine = (Machine) factory.getBean("machine");
        registers = machine.getRegisters();
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void testAddLabelThrowsExceptionWhenInvalid() {
        // label required
        Assertions.assertThrows(NullPointerException.class,
                () -> machine.getLabels().addLabel(null, 1));

        // label already exists
        machine.getLabels().addLabel("f2", 0);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> machine.getLabels().addLabel("f2", 1));
        machine.getLabels().reset();
    }

    @Test
    void testGetAddress() {
        machine.getLabels().addLabel("f1", 0);
        int address = machine.getLabels().getAddress("f1");
        Assertions.assertEquals(0, address);
        machine.getLabels().reset();
    }

    @Test
    void testGetAddressThrowsExceptionWhenKeyNotFound() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> machine.getLabels().getAddress("f1"));
    }

    @Test
    void testToString() {
        machine.getLabels().addLabel("f1", 0);
        machine.getLabels().addLabel("f2", 1);
        machine.getLabels().addLabel("f3", 2);
        String expectedString = "[f1 -> 0, f2 -> 1, f3 -> 2]";
        Assertions.assertEquals(expectedString, machine.getLabels().toString());
        machine.getLabels().reset();
    }

    @Test
    void testEquals() {
        Labels labels1 = new Labels();
        labels1.addLabel("f1", 0);
        labels1.addLabel("f2", 1);

        Labels labels2 = new Labels();
        labels2.addLabel("f1", 0);
        labels2.addLabel("f2", 1);

        Assertions.assertTrue(labels1.equals(labels2));
    }

    @Test
    void testEqualsTwo() {
        Labels labels1 = new Labels();
        labels1.addLabel("f1", 0);
        labels1.addLabel("f2", 1);

        Labels labels2 = new Labels();
        labels2.addLabel("f1", 1);
        labels2.addLabel("f2", 2);

        Assertions.assertFalse(labels1.equals(labels2));
    }

    @Test
    void testEqualsThree() {
        // A Labels instance should not equal its string representation
        Labels labels1 = new Labels();
        labels1.addLabel("f1", 0);
        labels1.addLabel("f2", 1);

        String labels2 = new String(labels1.toString());

        Assertions.assertFalse(labels1.equals(labels2));
    }
}
