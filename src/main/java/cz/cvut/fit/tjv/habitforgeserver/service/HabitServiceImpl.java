package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cz.cvut.fit.tjv.habitforgeserver.dao.HabitRepository;
import cz.cvut.fit.tjv.habitforgeserver.dao.TagRepository;
import cz.cvut.fit.tjv.habitforgeserver.dao.UserRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.Tag;
import cz.cvut.fit.tjv.habitforgeserver.model.User;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HabitServiceImpl extends CrudServiceImpl<Habit, Long> implements HabitService {
    private HabitRepository habitRepository;
    private TagRepository tagRepository;
    private UserRepository userRepository;

    @Override
    protected CrudRepository<Habit, Long> getRepository() {
        return habitRepository;
    }

    @Override
    public Habit create(Habit entity) {
        if (entity.getAuthor() != null &&
                !userRepository.existsById(entity.getAuthor().getId()))
            throw new EntityNotFoundException();

        return super.create(entity);
    }

    @Override
    public Collection<Habit> findByAuthorId(Long authorId) {
        return habitRepository.findByAuthorId(authorId);
    }

    @Override
    public Collection<Tag> readHabitTags(Long habitId) {
        var habit = habitRepository.findById(habitId);

        if (habit.isEmpty())
            throw new EntityNotFoundException();

        return habit.get().getTags();
    }

    @Override
    public void addTag(Long habitId, Long tagId) {
        var habit = habitRepository.findById(habitId);
        var tag = tagRepository.findById(tagId);

        if (habit.isEmpty() || tag.isEmpty())
            throw new EntityNotFoundException();

        if (habit.get().getTags().contains(tag.get()))
            throw new EntityAlreadyExistsException();

        habit.get().getTags().add(tag.get());
        habitRepository.save(habit.get());
    }

    @Override
    public void removeTag(Long id, Long tagId) {
        var habit = habitRepository.findById(id);
        var tag = tagRepository.findById(tagId);

        if (habit.isEmpty() || tag.isEmpty() || !habit.get().getTags().contains(tag.get()))
            throw new EntityNotFoundException();

        habit.get().getTags().remove(tag.get());
        habitRepository.save(habit.get());
    }

    @Override
    public Optional<User> getAuthor(Long habitId) {
        var habit = habitRepository.findById(habitId);

        if (habit.isEmpty())
            throw new EntityNotFoundException();

        return Optional.ofNullable(habit.get().getAuthor());
    }
}
