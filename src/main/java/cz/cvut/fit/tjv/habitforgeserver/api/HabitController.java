package cz.cvut.fit.tjv.habitforgeserver.api;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.Tag;
import cz.cvut.fit.tjv.habitforgeserver.model.User;
import cz.cvut.fit.tjv.habitforgeserver.service.CrudService;
import cz.cvut.fit.tjv.habitforgeserver.service.HabitService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/habits", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class HabitController extends CrudController<Habit, Long> {
    private HabitService habitService;

    @Override
    protected CrudService<Habit, Long> getService() {
        return habitService;
    }

    @Override
    @ApiResponses({
            @ApiResponse(responseCode = "422", content = @Content)
    })
    @PostMapping
    public Habit create(@Valid @RequestBody Habit entity) {
        return super.create(entity);
    }

    @Override
    @ApiResponses({
            @ApiResponse(responseCode = "422", content = @Content)
    })
    @PutMapping("/{id}")
    public Habit update(@PathVariable Long id, @Valid @RequestBody Habit entity) {
        return super.update(id, entity);
    }

    @GetMapping("/{id}/author")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public Optional<User> getAuthor(@PathVariable Long id) {
        return habitService.getAuthor(id);
    }

    @GetMapping("/{id}/tags")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public Collection<Tag> readHabitTags(@PathVariable Long id) {
        return habitService.readHabitTags(id);
    }

    @PutMapping("/{id}/tags/{tagId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "409", content = @Content)
    })
    public void addTag(@PathVariable Long id, @PathVariable Long tagId) {
        habitService.addTag(id, tagId);
    }

    @DeleteMapping("/{id}/tags/{tagId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public void removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        habitService.removeTag(id, tagId);
    }
}
