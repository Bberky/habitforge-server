package cz.cvut.fit.tjv.habitforgeserver.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHabit implements DomainEntity<Long> {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private HabitGoalInterval goalInterval;

    @Positive(message = "Goal threshold must be positive.")
    private Double goalThreshold;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Habit habit;

    @OneToMany(mappedBy = "userHabit", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<HabitEntry> entries;

    @JsonProperty("userId")
    private Long getUserId() {
        if (user == null)
            return null;

        return user.getId();
    }

    public void setUserId(Long id) {
        if (user == null)
            user = new User();

        user.setId(id);
    }

    @JsonProperty("habitId")
    private Long getHabitId() {
        if (habit == null)
            return null;

        return habit.getId();
    }

    public void setHabitId(Long id) {
        if (habit == null)
            habit = new Habit();

        habit.setId(id);
    }
}
