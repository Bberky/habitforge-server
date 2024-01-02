package cz.cvut.fit.tjv.habitforgeserver.api;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.tjv.habitforgeserver.model.Habit;
import cz.cvut.fit.tjv.habitforgeserver.model.User;
import cz.cvut.fit.tjv.habitforgeserver.model.UserHabit;
import cz.cvut.fit.tjv.habitforgeserver.service.CrudService;
import cz.cvut.fit.tjv.habitforgeserver.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController extends CrudController<User, Long> {
    private UserService userService;

    @Override
    protected CrudService<User, Long> getService() {
        return userService;
    }

    @Override
    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "409", content = @Content)
    })
    public User create(@Valid @RequestBody User entity) {
        return super.create(entity);
    }

    @Override
    @ApiResponses({
            @ApiResponse(responseCode = "409", content = @Content)
    })
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @Valid @RequestBody User entity) {
        return super.update(id, entity);
    }

    @GetMapping("/me")
    public User getMe(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/{id}/habits")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public Collection<Habit> getHabits(@PathVariable Long id) {
        return userService.getHabits(id);
    }

    @GetMapping("/{id}/user-habits")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public Collection<UserHabit> getUserHabits(@PathVariable Long id) {
        return userService.getUserHabits(id);
    }
}
