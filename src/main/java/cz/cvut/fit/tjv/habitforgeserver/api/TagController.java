package cz.cvut.fit.tjv.habitforgeserver.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.cvut.fit.tjv.habitforgeserver.model.Tag;
import cz.cvut.fit.tjv.habitforgeserver.service.CrudService;
import cz.cvut.fit.tjv.habitforgeserver.service.TagService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TagController extends CrudController<Tag, Long> {
    private TagService tagService;

    @Override
    protected CrudService<Tag, Long> getService() {
        return tagService;
    }
}
