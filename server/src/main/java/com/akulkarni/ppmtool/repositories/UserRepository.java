package com.akulkarni.ppmtool.repositories;

import com.akulkarni.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String usernam);
    User getById(Long id);

}
