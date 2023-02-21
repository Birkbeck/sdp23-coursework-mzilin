package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sml.Labels;

import static org.junit.jupiter.api.Assertions.*;

public class LabelsTest {
    private final Labels labels = new Labels();

    @AfterEach
    void tearDown() {
        this.labels.reset();
    }

    @Test
    void addLabel_normalBehaviour() {
        this.labels.addLabel("A1", 0);
        assertEquals(0, this.labels.getAddress("A1"));
    }

    @Test
    void addLabel_tryingToAddDuplicateLabel() {
        this.labels.addLabel("A1", 0);
        this.labels.addLabel("A1", 5);
        assertEquals(0, this.labels.getAddress("A1"));
    }

    @Test
    void getAddress_tryingToPassNullAsLabel_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.labels.getAddress(null));
        assertEquals("Label cannot be null", exception.getMessage());
    }

    @Test
    void getAddress_tryingToGetNonExistingLabel_throwsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> this.labels.getAddress("L3"));
        assertEquals("Label 'L3' doesn't exist", exception.getMessage());
    }

    @Test
    void toString_labelsPassedNotInOrder_printsOrderedList() {
        labels.addLabel("A4", 0);
        labels.addLabel("A1", 3);
        assertEquals("[A1 -> 3, A4 -> 0]", labels.toString());
    }

    @Test
    void equals_areEqual() {
        labels.addLabel("A4", 0);
        labels.addLabel("A1", 3);
        Labels labels2 = new Labels();
        labels2.addLabel("A4", 0);
        labels2.addLabel("A1", 3);
        assertEquals(labels, labels2);
        assertEquals(labels2, labels);
    }

    @Test
    void equals_notEqual() {
        labels.addLabel("A4", 0);
        labels.addLabel("A1", 3);
        Labels labels2 = new Labels();
        labels2.addLabel("A4", 3);
        labels2.addLabel("A2", 3);
        assertNotEquals(labels, labels2);
        assertNotEquals(labels2, labels);
        assertNotEquals(labels, "labels");
    }
}
