package com.user.management.client.misc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface ExtendedCrudRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    public <S extends T> S merge(S entity);

}
