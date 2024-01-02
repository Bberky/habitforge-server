package cz.cvut.fit.tjv.habitforgeserver.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import cz.cvut.fit.tjv.habitforgeserver.model.HabitEntry;

public interface HabitEntryRepository extends CrudRepository<HabitEntry, Long> {
    Collection<HabitEntry> findAllByUserHabitId(Long userHabitId);
}
