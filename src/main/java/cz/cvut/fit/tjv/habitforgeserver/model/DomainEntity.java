package cz.cvut.fit.tjv.habitforgeserver.model;

public interface DomainEntity<ID> {
    ID getId();

    void setId(ID id);
}
