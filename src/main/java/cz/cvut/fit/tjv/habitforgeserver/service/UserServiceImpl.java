package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cz.cvut.fit.tjv.habitforgeserver.dao.UserRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.User;
import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        return userRepository.save(entity);
    }

    @Override
    public User update(Long id, User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        return super.update(id, entity);
    }

    @Override
    protected CrudRepository<User, Long> getRepository() {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Override
    public Collection<UserHabit> getUserHabits(Long userId) {
        var user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new EntityNotFoundException();

        return user.get().getUserHabits();
    }

    @Override
    public Collection<Habit> getHabits(Long userId) {
        var user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new EntityNotFoundException();

        return user.get().getHabits();
    }
}
