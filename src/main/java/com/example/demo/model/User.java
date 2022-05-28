package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name="user")
public class User {
    @Id
    private Integer id;
    @Column
    private String nome;
    @Column
    private Integer idade;
    @Column
    private String telefone;
    @Column
    private String cidade;

}
