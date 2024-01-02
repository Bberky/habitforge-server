package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cz.cvut.fit.tjv.habitforgeserver.model.DomainEntity;

public abstract class CrudServiceImpl<T extends DomainEntity<ID>, ID> implements CrudService<T, ID> {
    @Override
    public T create(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public Optional<T> readById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Collection<T> readAll() {
        var res = new ArrayList<T>();

        getRepository().findAll().forEach(e -> res.add(e));

        return res;
    }

    @Override
    public T update(ID id, T entity) {
        Optional<T> oldEntity = getRepository().findById(id);

        if (oldEntity.isEmpty())
            throw new EntityNotFoundException();

        entity.setId(id);

        return getRepository().save(entity);
    }

    @Override
    public void deleteById(ID id) {
        if (!getRepository().existsById(id))
            throw new EntityNotFoundException();

        getRepository().deleteById(id);
    }

    protected abstract CrudRepository<T, ID> getRepository();
}
