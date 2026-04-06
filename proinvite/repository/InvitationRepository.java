package com.proinvite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proinvite.model.Invitation;

//interfata InvitationRepository actioneaza ca un administrator de date pentru tabela invitations din baza de date.
@Repository
/**
 * JpaRepository interfata predefinita din biblioteca Spring Data JPA
 * InvitationRepository mosteneste toate functionalitatile lui JpaRepository
 * Invitation-Specifica clasa model pe care acest Repository o va gestiona(tabela invitations)
 * Long-Tipul de date al cheii primare (id) a entitatii Invitation
 */
public interface InvitationRepository extends JpaRepository<Invitation,Long> {
}
