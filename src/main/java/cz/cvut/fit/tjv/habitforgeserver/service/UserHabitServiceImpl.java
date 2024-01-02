package cz.cvut.fit.tjv.habitforgeserver.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cz.cvut.fit.tjv.habitforgeserver.dao.HabitRepository;
import cz.cvut.fit.tjv.habitforgeserver.dao.UserHabitRepository;
import cz.cvut.fit.tjv.habitforgeserver.dao.UserRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.HabitEntry;
import cz.cvut.fit.tjv.habitforgeserver.model.HabitGoalInterval;
import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserHabitServiceImpl extends CrudServiceImpl<UserHabit, Long> implements UserHabitService {
    private UserHabitRepository userHabitRepository;
    private HabitEntryService habitEntryService;
    private HabitRepository habitRepository;
    private UserRepository userRepository;

    @Override
    protected CrudRepository<UserHabit, Long> getRepository() {
        return userHabitRepository;
    }

    @Override
    public UserHabit create(UserHabit entity) {
        if (!habitRepository.existsById(entity.getHabit().getId()) ||
                !userRepository.existsById(entity.getUser().getId()))
            throw new ReferencedEntityDoesNotExistException();

        return super.create(entity);
    }

    @Override
    public UserHabit update(Long id, UserHabit entity) {
        if (!habitRepository.existsById(entity.getHabit().getId()) ||
                !userRepository.existsById(entity.getUser().getId()))
            throw new ReferencedEntityDoesNotExistException();

        return super.update(id, entity);
    }

    public Double getHabitCompletion(Long userHabitId) {
        HabitGoalInterval interval = userHabitRepository.findById(userHabitId).map(h -> h.getGoalInterval())
                .orElse(null);

        if (interval == null) {
            throw new EntityNotFoundException();
        }

        LocalDateTime since = LocalDateTime.now();

        switch (interval) {
            case DAILY:
                since = since.minusDays(1);
                break;
            case WEEKLY:
                since = since.minusWeeks(1);
                break;
            case MONTHLY:
                since = since.minusMonths(1);
                break;
            case YEARLY:
                since = since.minusYears(1);
                break;
        }

        return userHabitRepository.getHabitCompletion(userHabitId, since);
    }

    @Override
    public HabitEntry addHabitEntry(Long userHabitId, HabitEntry habitEntry) {
        UserHabit userHabit = userHabitRepository.findById(userHabitId).orElse(null);

        if (userHabit == null) {
            throw new EntityNotFoundException();
        }

        habitEntry.setUserHabit(userHabit);

        return habitEntryService.create(habitEntry);
    }

    @Override
    public Collection<HabitEntry> getHabitEntries(Long userHabitId) {
        var userHabit = readById(userHabitId);

        if (userHabit.isEmpty())
            throw new EntityNotFoundException();

        return userHabit.get().getEntries();
    }

    @Override
    public boolean containsEntry(Long userHabitId, Long habitEntryId) {
        var userHabit = readById(userHabitId);

        if (userHabit.isEmpty())
            throw new EntityNotFoundException();

        return userHabit.get().getEntries().stream().anyMatch(e -> e.getId().equals(habitEntryId));
    }
}
