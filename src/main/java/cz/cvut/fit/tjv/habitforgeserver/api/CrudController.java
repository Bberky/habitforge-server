package cz.cvut.fit.tjv.habitforgeserver.api;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.cvut.fit.tjv.habitforgeserver.model.DomainEntity;
import cz.cvut.fit.tjv.habitforgeserver.service.CrudService;
import cz.cvut.fit.tjv.habitforgeserver.service.EntityNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@SecurityRequirement(name = "basicAuth")
@CrossOrigin
public abstract class CrudController<D extends DomainEntity<ID>, ID> {
    protected abstract CrudService<D, ID> getService();

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    public D create(@Valid @RequestBody D entity) {
        return getService().create(entity);
    }

    @GetMapping
    public Collection<D> readAll() {
        return getService().readAll();
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public D readById(@PathVariable ID id) {
        var res = getService().readById(id);

        if (res.isEmpty())
            throw new EntityNotFoundException();

        return res.get();
    }

    @PutMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody D entity) {
        return ResponseEntity.ok(getService().update(id, entity));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    public void deleteById(@PathVariable ID id) {
        getService().deleteById(id);
    }
}
