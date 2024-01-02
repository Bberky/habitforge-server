package cz.cvut.fit.tjv.habitforgeserver.dao;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;

public interface UserHabitRepository extends CrudRepository<UserHabit, Long> {
    @Query(value = """
            SELECT sum(h_e.value) / u_h.goalThreshold FROM UserHabit u_h JOIN u_h.entries h_e
            WHERE u_h.id = :userHabitId AND h_e.createdAt >= :since
            GROUP BY u_h.id""")
    Double getHabitCompletion(Long userHabitId, LocalDateTime since);
}
