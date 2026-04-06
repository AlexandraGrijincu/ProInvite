package com.proinvite.controller;

import com.proinvite.model.User;
import com.proinvite.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proinvite.exception.UserAlreadyExistsException;
import com.proinvite.exception.InvalidUserOrPasswordException;
import org.springframework.web.bind.annotation.CrossOrigin;

//AuthController este poarta de intrare in aplicatie; gestioneaza comunicarea dintre frontend si service; rolul ei este de a primi cereri http
@RestController
@RequestMapping("/api/auth") //URL de Bază: Definește calea de bază pentru toate metodele din această clasă. Orice metodă de aici va începe cu http://localhost:8080/api/auth.
@CrossOrigin(origins = "*", allowedHeaders = "*") //permite frontend-ului sa comunice cu backend-ul; origins="*" inseamna ca accepta cereri de la orice domeniu
public class AuthController {
    private final UserService userService;

    //Controller-ul nu contine logica de business(service) el doar o apeleaza
    public AuthController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register") //dup a /api/auth avem /register
    public ResponseEntity<?> register(@RequestBody User user){ //Spring preia corpul JSON trimis de frontend si il converteste automat intr un obiect Java de tip User
        try{
            User registeredUser=userService.registerUser(user); //codul java verifica baza de date
            registeredUser.setPassword(null); //sterge parola din obiectul User ;masura de securitate pt a ne asigura ca parola nu ajunge niciodata inapoi in browser.
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED); //se returneaza raspunsul catre frontend; HttpStatus.CREATED(care inseamna 201) ii spune browser-ului ca un nou cont a fost creat.
        }catch(UserAlreadyExistsException ex){ //Dacă în timpul execuției din blocul try, Service-ul detectează că Username-ul este deja folosit, Service-ul nu returnează un obiect, ci aruncă o eroare specifică: UserAlreadyExistsException.
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); //se returneaza mesaj de eroare catre frontend: "Username-ul este deja folosit" cu (getMessage());  HttpStatus.BAD_REQUEST(care inseamna 400) ii spune browser-ului ca datele trimise nu sunt valide
        }
    }

    @PostMapping("/login") //dup a /api/auth avem /login
    public ResponseEntity<?> login(@RequestBody User loginDetails){ //Spring preia corpul JSON trimis de frontend si il converteste automat intr un obiect Java de tip User(numit aici loginDetails). Nu sunt preluate toate campurile ci doar cele trimise de username si password.
        try {
            User user = userService.loginUser(loginDetails.getUsername(), loginDetails.getPassword()); //UserService cauta utilizatorul dupa username in baza de date; daca il gaseste si parola se potriveste,returneaza obiectul User, daca nu arunca exceptia
            return new ResponseEntity<>("Login reusit! ", HttpStatus.OK);
        }catch (InvalidUserOrPasswordException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED); //HttpStatus.UNAUTHORIZED (care înseamnă 401) este codul HTTP standard pt autentificare esuata
        }
    }
}
