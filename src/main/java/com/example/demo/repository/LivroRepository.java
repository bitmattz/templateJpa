package com.example.demo.repository;

import com.example.demo.model.Livro;
import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

}
