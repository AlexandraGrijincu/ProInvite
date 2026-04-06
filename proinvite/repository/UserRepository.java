package com.proinvite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proinvite.model.User;

//interfata UserRepository actioneaza ca un administrator de date pentru tabela users din baza de date.
@Repository
/**
 * JpaRepository interfata predefinita din biblioteca Spring Data JPA
 * UserRepository mosteneste toate functionalitatile lui JpaRepository
 * User-Specifica clasa model pe care acest Repository o va gestiona(tabela users)
 * Long-Tipul de date al cheii primare (id) a entitatii User
 * User findByUsername-Spring Data JPA stie ca trebuie sa creeze interogarea SQL SELECT * FROM users WHERE username = [valoarea primită].
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); //pt autentificare
}
