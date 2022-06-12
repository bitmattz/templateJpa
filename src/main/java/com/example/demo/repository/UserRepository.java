package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM public.user WHERE email = ?1",
            nativeQuery = true)
    User findUserByEmail(String name);

    @Query(value = "SELECT * FROM public.user WHERE email = ?1 AND senha = ?2",
            nativeQuery = true)
    User login(String name, String senha);

}
