package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.Collection;

import cz.cvut.fit.tjv.habitforgeserver.model.HabitEntry;
import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;

public interface UserHabitService extends CrudService<UserHabit, Long> {
    Double getHabitCompletion(Long userHabitId);

    HabitEntry addHabitEntry(Long userHabitId, HabitEntry habitEntry);

    Collection<HabitEntry> getHabitEntries(Long userHabitId);

    boolean containsEntry(Long userHabitId, Long habitEntryId);
}
