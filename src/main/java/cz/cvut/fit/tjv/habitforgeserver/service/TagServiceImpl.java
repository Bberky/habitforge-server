package cz.cvut.fit.tjv.habitforgeserver.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import cz.cvut.fit.tjv.habitforgeserver.dao.TagRepository;
import cz.cvut.fit.tjv.habitforgeserver.model.Tag;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TagServiceImpl extends CrudServiceImpl<Tag, Long> implements TagService {
    private TagRepository tagRepository;

    @Override
    protected CrudRepository<Tag, Long> getRepository() {
        return tagRepository;
    }
}
