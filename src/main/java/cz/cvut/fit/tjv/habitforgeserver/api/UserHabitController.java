package cz.cvut.fit.tjv.habitforgeserver.api;

import java.util.Collection;

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

import cz.cvut.fit.tjv.habitforgeserver.model.HabitEntry;
import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;
import cz.cvut.fit.tjv.habitforgeserver.service.CrudService;
import cz.cvut.fit.tjv.habitforgeserver.service.EntityNotFoundException;
import cz.cvut.fit.tjv.habitforgeserver.service.HabitEntryService;
import cz.cvut.fit.tjv.habitforgeserver.service.UserHabitService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/user-habits", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserHabitController extends CrudController<UserHabit, Long> {
    private UserHabitService userHabitService;
    private HabitEntryService habitEntryService;

    @Override
    protected CrudService<UserHabit, Long> getService() {
        return userHabitService;
    }

    @Override
    @ApiResponses({
            @ApiResponse(responseCode = "422", content = @Content)
    })
    @PostMapping
    public UserHabit create(@Valid @RequestBody UserHabit entity) {
        return super.create(entity);
    }

    @Override
    @ApiResponses({
            @ApiResponse(responseCode = "422", content = @Content)
    })
    @PutMapping("/{id}")
    public UserHabit update(@PathVariable Long id, @Valid @RequestBody UserHabit entity) {
        return super.update(id, entity);
    }

    @GetMapping("/{id}/completion")
    public Double getUserHabitCompletion(@PathVariable Long id) {
        return userHabitService.getHabitCompletion(id);
    }

    @PostMapping("/{id}/entries")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public HabitEntry createUserHabitEntry(@PathVariable Long id, @RequestBody HabitEntry entity) {
        return userHabitService.addHabitEntry(id, entity);
    }

    @GetMapping("/{id}/entries")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public Collection<HabitEntry> getUserHabitEntries(@PathVariable Long id) {
        return userHabitService.getHabitEntries(id);
    }

    @DeleteMapping("/{id}/entries/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public void deleteUserHabitEntry(@PathVariable Long id, @PathVariable Long entryId) {
        checkUserHabitContainsEntry(id, entryId);
        habitEntryService.deleteById(entryId);
    }

    @PutMapping("/{id}/entries/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public void updateUserHabitEntry(@PathVariable Long id, @PathVariable Long entryId,
            @RequestBody HabitEntry entity) {
        checkUserHabitContainsEntry(id, entryId);
        habitEntryService.update(entryId, entity);
    }

    private void checkUserHabitContainsEntry(Long id, Long entryId) {
        if (!userHabitService.containsEntry(id, entryId))
            throw new EntityNotFoundException();
    }
}
