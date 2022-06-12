package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserRepository userRepository ;

    public UserRepository getRepository() {
        return userRepository;
    }

    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        Integer id = (int) ((Math.random() * (3000 - 1)) + 1);
        user.setId(id);
       String checkUserExists = String.valueOf(userRepository.findById(user.getId()));
       String checkEmailExists = String.valueOf(userRepository.findUserByEmail(user.getEmail()));
       System.out.println(checkEmailExists);
       if(checkUserExists != "Optional.empty" || checkEmailExists != "null"){
           return new ResponseEntity(HttpStatus.BAD_REQUEST);
       }else{
           userRepository.save(user);
       }
       return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/login/")
    public ResponseEntity login(@RequestBody LoginDTO user) {
        String login = String.valueOf(userRepository.login(user.getEmail(),user.getSenha()));
        if(login == "null"){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
           User loginUser =  userRepository.login(user.getEmail(),user.getSenha());
            return new ResponseEntity<>(loginUser, HttpStatus.OK);
        }
    }
    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users ;
        users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
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
                    user.setEmail(newUser.getEmail());
                    user.setSenha(newUser.getSenha());
                    User userUpdated = userRepository.save(user);
                    return ResponseEntity.ok().body(userUpdated);

                }).orElse(ResponseEntity.notFound().build());
    }


}
