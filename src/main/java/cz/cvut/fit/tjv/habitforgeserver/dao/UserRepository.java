package cz.cvut.fit.tjv.habitforgeserver.dao;

import org.springframework.data.repository.CrudRepository;

import cz.cvut.fit.tjv.habitforgeserver.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
