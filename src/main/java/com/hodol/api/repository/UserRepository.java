package com.hodol.api.repository;


import com.hodol.api.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByEmailAndPassword(String email, String password);
}
