package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SceneContainerTest {
    private SceneContainer container;

    @BeforeEach
    void setUp() {
        container = new SceneContainer();
    }

    @Test
    void testSceneContainerLoadUsersWhenExternalDoesNotContainAnyPreviousUsers_ShouldHaveAnEmptyMap() {
        SceneContainer otherContainer = new SceneContainer();
        otherContainer.loadUsers("testingEmpty.json");
        assertTrue(otherContainer.getUsers().isEmpty());
    }

    @Test
    void testSceneContainerLoadUserWhenExistingFileContainsTwoPreviousUsers_shouldReturnTwoAsSizeOfMap(){
        container.loadUsers("testing.json");
        assertEquals(2, container.getUsers().size());

    }

    @Test
    void testSceneContainerUserMapSizeAfterLoadingExternalFileWithExistingFilesAndAddingAnotherUser_shouldReturnSizeOfThree() {
        container.loadUsers("testing.json");

        container.saveUsers(new Person("Armando", 1500));
        assertEquals(3,container.getUsers().size());
    }
}