package cz.cvut.fit.tjv.habitforgeserver.service;

import java.util.Collection;
import java.util.Optional;

import cz.cvut.fit.tjv.habitforgeserver.model.DomainEntity;

public interface CrudService<T extends DomainEntity<ID>, ID> {
    public T create(T entity);

    public Optional<T> readById(ID id);

    public Collection<T> readAll();

    public T update(ID id, T entity);

    public void deleteById(ID id);
}
