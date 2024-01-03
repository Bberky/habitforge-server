package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cz.cvut.fit.tjv.habitforgeserver.dao.HabitRepository;
import cz.cvut.fit.tjv.habitforgeserver.dao.TagRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.Tag;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class HabitServiceIntegrationTest {
    @Autowired
    private HabitServiceImpl habitService;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private TagRepository tagRepository;

    Habit habit;
    List<Tag> tags = new ArrayList<>();

    @BeforeEach
    void setUp() {
        habitRepository.deleteAll();
        tagRepository.deleteAll();

        habit = null;
        tags.clear();

        var habit = new Habit(1L, "habit1", "test", "test", null, new ArrayList<>());
        var tag1 = new Tag(1L, "tag1", 0, new ArrayList<>());
        var tag2 = new Tag(2L, "tag2", 0, new ArrayList<>());

        this.habit = habitRepository.save(habit);
        tags.add(tagRepository.save(tag1));
        tags.add(tagRepository.save(tag2));
    }

    @Test
    void assignTagsToHabit() {
        Assertions.assertTrue(habit.getTags().isEmpty());
        Assertions.assertTrue(tags.get(0).getHabits().isEmpty());
        Assertions.assertTrue(tags.get(1).getHabits().isEmpty());

        habitService.addTag(habit.getId(), tags.get(0).getId());

        Assertions.assertEquals(habit.getTags().size(), 1);
        Assertions.assertTrue(habit.getTags().contains(tags.get(0)));
        Assertions.assertEquals(tags.get(0).getHabits().size(), 1);
        Assertions.assertTrue(tags.get(0).getHabits().contains(habit));

        habitService.addTag(habit.getId(), tags.get(1).getId());

        Assertions.assertEquals(habit.getTags().size(), 2);
        Assertions.assertTrue(habit.getTags().containsAll(tags));
        Assertions.assertEquals(tags.get(0).getHabits().size(), 1);
        Assertions.assertTrue(tags.get(0).getHabits().contains(habit));
        Assertions.assertEquals(tags.get(1).getHabits().size(), 1);
        Assertions.assertTrue(tags.get(1).getHabits().contains(habit));
    }

    @Test
    void removeTagFromHabit() {
        habitService.addTag(habit.getId(), tags.get(0).getId());
        habitService.addTag(habit.getId(), tags.get(1).getId());

        habitService.removeTag(habit.getId(), tags.get(0).getId());

        Assertions.assertEquals(habit.getTags().size(), 1);
        Assertions.assertTrue(habit.getTags().contains(tags.get(1)));
    }

    @Test
    void assignSameTagTwice() {
        habitService.addTag(habit.getId(), tags.get(0).getId());

        Assertions.assertEquals(habit.getTags().size(), 1);
        Assertions.assertTrue(habit.getTags().contains(tags.get(0)));
        Assertions.assertEquals(tags.get(0).getHabits().size(), 1);
        Assertions.assertTrue(tags.get(0).getHabits().contains(habit));

        Assertions.assertThrows(EntityAlreadyExistsException.class,
                () -> habitService.addTag(habit.getId(), tags.get(0).getId()));
    }

    @Test
    void assignNonExistingTag() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> habitService.addTag(habit.getId(), 3L));
    }
}
