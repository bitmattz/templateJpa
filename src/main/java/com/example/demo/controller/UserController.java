package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/user/")
public class UserController {

    UserRepository userRepository ;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
       userRepository.save(user);
       System.out.println(userRepository.save(user));
       return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users ;
        users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path="/teste")
    public ResponseEntity<String> teste(){
        String teste = "Conectou";
        return new ResponseEntity<>(teste, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable Integer id){
        Optional<User> user;
        try{
            user = userRepository.findById(id);
            return new ResponseEntity<Optional<User>>(user,HttpStatus.OK);
        }catch (NoSuchElementException ex){
            return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<User>> deleteById(@PathVariable Integer id){
        try{
            userRepository.deleteById(id);
            return new ResponseEntity<Optional<User>>(HttpStatus.OK);
        }catch(NoSuchElementException ex){
           return new ResponseEntity<Optional<User>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody User newUser){
        return userRepository.findById(id).map(
                user ->{
                    user.setNome(newUser.getNome());
                    user.setIdade(newUser.getIdade());
                    user.setTelefone(newUser.getTelefone());
                    user.setCidade(newUser.getCidade());
                    User userUpdated = userRepository.save(user);
                    return ResponseEntity.ok().body(userUpdated);

                }).orElse(ResponseEntity.notFound().build());
    }


}
