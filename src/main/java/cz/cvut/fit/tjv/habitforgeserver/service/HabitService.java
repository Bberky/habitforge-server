package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.Collection;
import java.util.Optional;

import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.Tag;
import cz.cvut.fit.tjv.habitforgeserver.model.User;

public interface HabitService extends CrudService<Habit, Long> {
    Optional<User> getAuthor(Long habitId);

    Collection<Habit> findByAuthorId(Long authorId);

    Collection<Tag> readHabitTags(Long habitId);

    void addTag(Long habitId, Long tagId);

    void removeTag(Long id, Long tagId);
}
