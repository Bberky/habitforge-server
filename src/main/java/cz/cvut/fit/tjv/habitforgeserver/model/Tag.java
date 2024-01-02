package cz.cvut.fit.tjv.habitforgeserver.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements DomainEntity<Long> {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @PositiveOrZero(message = "Color must be a 24-bit number.")
    @Max(value = 16777215, message = "Color must be a 24-bit number.")
    @NotNull
    private Integer color;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Collection<Habit> habits;

    @PreRemove
    private void removeTagsFromHabits() {
        for (Habit habit : habits)
            habit.getTags().remove(this);
    }
}
