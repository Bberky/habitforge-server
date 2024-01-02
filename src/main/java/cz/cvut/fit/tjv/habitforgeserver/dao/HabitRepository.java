package cz.cvut.fit.tjv.habitforgeserver.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import cz.cvut.fit.tjv.habitforgeserver.model.Habit;

public interface HabitRepository extends CrudRepository<Habit, Long> {
    Collection<Habit> findByAuthorId(Long authorId);
}
