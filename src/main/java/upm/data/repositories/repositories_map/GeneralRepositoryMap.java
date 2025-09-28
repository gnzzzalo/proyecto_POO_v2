package upm.data.repositories.repositories_map;

import upm.data.repositories.GeneralRepository;

import java.util.*;

public abstract class GeneralRepositoryMap<T> implements GeneralRepository<T> {
    private final Map<Integer, T> map;
    private int id;

    GeneralRepositoryMap() {
        this.map = new HashMap<>();
        this.id = 1;
    }

    @Override
    public T create(T entity) {
        this.setId(entity, this.id);
        this.map.put(this.id, entity);
        this.id++;
        return entity;
    }

    @Override
    public T update(T entity) {
        if (this.getId(entity) == null) {
            throw new IllegalArgumentException("No se puede actualizar una entidad cuando su id es null: " + entity);
        }
        this.map.put(this.getId(entity), entity);
        return entity;
    }

    @Override
    public Optional<T> read(Integer id) {
        return Optional.ofNullable(this.map.get(id));
    }

    @Override
    public void deleteById(Integer id) {
        this.map.remove(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(map.values());
    }

    public abstract Integer getId(T entity);

    public abstract void setId(T entity, Integer id);

}
