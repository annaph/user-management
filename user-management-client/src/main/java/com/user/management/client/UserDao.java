package com.user.management.client;

import com.user.management.client.misc.ExtendedCrudRepository;
import com.user.management.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao implements ExtendedCrudRepository<User, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <S extends User> S save(S entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <S extends User> Iterable<S> save(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public User findOne(Long aLong) {
        return entityManager.find(User.class, aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return entityManager.contains(findOne(aLong));
    }

    @Override
    public Iterable<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public Iterable<User> findAll(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long aLong) {
        delete(findOne(aLong));
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public void delete(Iterable<? extends User> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        delete(findAll());
    }

    @Override
    public <S extends User> S merge(S entity) {
        return entityManager.merge(entity);
    }

}
