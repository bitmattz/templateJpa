package com.example.demo.controller;

import com.example.demo.model.Livro;
import com.example.demo.model.User;
import com.example.demo.repository.LivroRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/book/")
public class LivroController {

    @Autowired
    LivroRepository livroRepository ;

    public LivroRepository getRepository() {
        return livroRepository;
    }

    public void setRepository(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @PostMapping
    public ResponseEntity createBook(@RequestBody Livro livro) {
        Integer id = (int) ((Math.random() * (3000 - 1)) + 1);
        livro.setId(id);
        String checkUserExists = String.valueOf(livroRepository.findById(livro.getId()));
        if(checkUserExists != "Optional.empty"){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            livroRepository.save(livro);
        }
        return new ResponseEntity<>(livro, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Livro>> getAll(){
        List<Livro> livros ;
        livros = livroRepository.findAll();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Livro>> getById(@PathVariable Integer id){
        Optional<Livro> livro;
        try{
            livro = livroRepository.findById(id);
            return new ResponseEntity<Optional<Livro>>(livro,HttpStatus.OK);
        }catch (NoSuchElementException ex){
            return new ResponseEntity<Optional<Livro>>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Livro>> deleteById(@PathVariable Integer id){
        try{
            livroRepository.deleteById(id);
            return new ResponseEntity<Optional<Livro>>(HttpStatus.OK);
        }catch(NoSuchElementException ex){
            return new ResponseEntity<Optional<Livro>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Livro> update(@PathVariable Integer id, @RequestBody Livro newLivro){
        return livroRepository.findById(id).map(
                livro ->{
                    livro.setNome(newLivro.getNome());
                    livro.setDescr(newLivro.getDescr());
                    livro.setIdUser(newLivro.getIdUser());
                    livro.setAutor(newLivro.getAutor());
                    Livro livroUpdated = livroRepository.save(livro);
                    return ResponseEntity.ok().body(livroUpdated);

                }).orElse(ResponseEntity.notFound().build());
    }
}
