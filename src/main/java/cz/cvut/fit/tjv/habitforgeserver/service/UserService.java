package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetailsService;

import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.User;
import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;

public interface UserService extends CrudService<User, Long>, UserDetailsService {
    Collection<Habit> getHabits(Long userId);

    Collection<UserHabit> getUserHabits(Long userId);
}