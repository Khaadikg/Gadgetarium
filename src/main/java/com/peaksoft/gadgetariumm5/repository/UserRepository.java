package com.peaksoft.gadgetariumm5.repository;

import com.peaksoft.gadgetariumm5.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.firstName =:username")
    User getUserByUsername(@Param("username") String username);
    Optional<User> findByEmail(String email);


}

