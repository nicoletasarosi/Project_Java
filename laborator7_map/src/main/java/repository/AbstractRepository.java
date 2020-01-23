package repository;

import domain.Entity;
import domain.validate.ValidationException;
import domain.validate.Validator;

import java.util.HashMap;

/**
 * CRUD operations repository interface
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> - type of entities saved in repository
 */
public abstract class AbstractRepository<ID,E extends Entity<ID>> implements CrudRepository<ID,E> {
    private HashMap<ID,E> elems;
    private Validator<E> validator;

    public AbstractRepository(Validator<E> validator){
        elems =new HashMap<>();
        this.validator=validator;
    }

    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */

    public E findOne(ID id) throws IllegalArgumentException{
        if(id!=null) {
            return elems.get(id);
        }
        else {
            throw new IllegalArgumentException("Id-ul nu poate fi null la cautare\n");
        }
    }

    /**
     *
     * @return all entities
     */

    public Iterable<E> findAll(){
        return elems.values();
    }

    /**
     *
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */

    public E save(E entity) throws ValidationException,IllegalArgumentException{
        if(entity!=null) {
            validator.validate(entity);
            E elem=findOne(entity.getId());
            if(elem==null) {
                elems.put(entity.getId(), entity);
                return null;
            }
            return entity;
        }
        else throw new IllegalArgumentException("Entitatea nu poate fi null la adaugare\n");
    }

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the
    given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */

    public E delete(ID id) throws IllegalArgumentException{
        if(id!=null){
            E val= elems.get(id);
            if(val!=null) {
                elems.remove(id);
                return val;
            }
            return null;
        }
        else {
            throw new IllegalArgumentException("Id-ul nu poate fi null la stergere\n");
        }
    }

    /**
     *
     * @param entity
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not
    exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     */

    public E update(E entity) throws ValidationException,IllegalArgumentException{
        if(entity!=null){
            validator.validate(entity);
            E elem=findOne(entity.getId());
            if(elem!=null){
                //elems.remove(entity.getId());
                elems.put(entity.getId(),entity);
                return null;
            }
            return entity;
        }
        else {
            throw new IllegalArgumentException("Entitatea nu poate fi null la update\n");
        }
    }
}
