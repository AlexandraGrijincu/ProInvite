package com.proinvite.service;

import com.proinvite.exception.UserAlreadyExistsException;
import com.proinvite.exception.InvalidUserOrPasswordException;
import com.proinvite.model.User;
import com.proinvite.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.proinvite.dto.InvitationRequestDTO;
//Rolul acestei clase este sa ia decizii, sa verifice datele si sa foloseasca Repository-ul pt a salva sau a citi din baza de date
@Service //adnotare Spring
public class UserService {
    /**
     * final asigura ca se foloseste aceeasi instanta a repository-ului pe toata durata sa de viata.
     * UserRepository-tipul de date care defineste comunicarea cu tabela users
     * userRepository-numele variabilei instantiate
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User registerUser(User user){
        if(userRepository.findByUsername(user.getUsername())!=null){ //verificam daca exista deja un utilizator cu acel username
            throw new UserAlreadyExistsException("Username-ul este deja folosit.");
        }
        return userRepository.save(user); //daca username-ul e disponibil, apeleaza metoda save care insereaza noul utilizator in tabela MySQL
    }

    public User loginUser(String username, String password){
        User user=userRepository.findByUsername(username); //Foloseste metoda custom din Repository pentru a prelua obiectul User din baza de date pe baza username-ului.
        if(user != null && user.getPassword().equals(password)){ //verificam daca user nu e null si daca parola introdusa este aceeasi cu cea din baza de date.
            return user;
        }
        throw new InvalidUserOrPasswordException("Username sau parola incorecta.") ;
    }
}
