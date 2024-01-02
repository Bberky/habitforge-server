package cz.cvut.fit.tjv.habitforgeserver.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cz.cvut.fit.tjv.habitforgeserver.dao.HabitEntryRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.HabitEntry;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HabitEntryServiceImpl extends CrudServiceImpl<HabitEntry, Long> implements HabitEntryService {
    private HabitEntryRepository habitEntryRepository;

    @Override
    protected CrudRepository<HabitEntry, Long> getRepository() {
        return habitEntryRepository;
    }
}
