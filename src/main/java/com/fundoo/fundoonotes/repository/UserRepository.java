package com.fundoo.fundoonotes.repository;

import com.fundoo.fundoonotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findAllByEmailAndName(String email,String name);

}
