package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name="livro")
public class Livro {
    @GeneratedValue
    @Id
    private Integer id;
    @Column
    private String nome;
    @Column
    private String descr;

    @Column(name="id_user")
    private Integer idUser;
    @Column
    private String autor;
}
