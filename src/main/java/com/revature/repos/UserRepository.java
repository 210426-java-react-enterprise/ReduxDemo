package com.revature.repos;


import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

    @Query("select case when count(u) > 0 then true else false end from User u where u.username = :username")
    Boolean isUsernameAvailable(String username);

    @Query("select case when count(u) > 0 then true else false end from User u where u.email = :email")
    Boolean isEmailAvailable(String email);

    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    ArrayList<User> findUsersByWantsWeeklyUpdatesTrue();

}
