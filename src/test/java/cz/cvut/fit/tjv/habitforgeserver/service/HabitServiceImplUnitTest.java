package cz.cvut.fit.tjv.habitforgeserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import cz.cvut.fit.tjv.habitforgeserver.dao.HabitRepository;
import cz.cvut.fit.tjv.habitforgeserver.dao.UserRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.User;

@SpringBootTest
public class HabitServiceImplUnitTest {
    @Autowired
    private HabitServiceImpl habitService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private HabitRepository habitRepository;

    @Test
    void createHabitWithoutAuthor() {
        var habit = new Habit(1L, "test", "test", "test", null, null);

        Mockito.when(habitRepository.save(habit)).thenReturn(habit);
        Mockito.when(userRepository.existsById(Mockito.any())).thenThrow(IllegalArgumentException.class);

        habitService.create(habit);

        Mockito.verify(habitRepository, Mockito.times(1)).save(habit);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    void createHabitWithAuthor() {
        var user = new User(1L, "test", "", "test", null, null);
        var habit = new Habit(1L, "test", "test", "test", user, null);

        Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);
        Mockito.when(habitRepository.save(habit)).thenReturn(habit);

        habitService.create(habit);

        Mockito.verify(habitRepository, Mockito.times(1)).save(habit);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(user.getId());
        Mockito.verifyNoMoreInteractions(habitRepository);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createHabitWithNonExistingAuthor() {
        var user = new User(1L, "test", "", "test", null, null);
        var habit = new Habit(1L, "test", "test", "test", user, null);

        Mockito.when(userRepository.existsById(user.getId())).thenReturn(false);

        Assertions.assertThrows(ReferencedEntityDoesNotExistException.class, () -> {
            habitService.create(habit);
        });

        Mockito.verify(userRepository, Mockito.times(1)).existsById(user.getId());
        Mockito.verifyNoInteractions(habitRepository);
        Mockito.verifyNoMoreInteractions(userRepository);
    }
}
