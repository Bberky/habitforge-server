package cz.cvut.fit.tjv.habitforgeserver.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit implements DomainEntity<Long> {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Habit name cannot be blank.")
    private String name;

    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Habit unit cannot be blank.")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User author;

    @ManyToMany
    @JsonIgnore
    private Collection<Tag> tags;

    @JsonProperty("authorId")
    public Long getUserId() {
        if (author == null)
            return null;

        return author.getId();
    }

    public void setUserId(Long id) {
        if (author == null)
            author = new User();

        author.setId(id);
    }
}
