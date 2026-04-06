package com.proinvite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proinvite.model.Template;

//interfata TemplateRepository actioneaza ca un administrator de date pentru tabela templates din baza de date.
@Repository
/**
 * JpaRepository interfata predefinita din biblioteca Spring Data JPA
 * TemplateRepository mosteneste toate functionalitatile lui JpaRepository
 * Template-Specifica clasa model pe care acest Repository o va gestiona(tabela templates)
 * Long-Tipul de date al cheii primare (id) a entitatii Template
 */
public interface TemplateRepository extends JpaRepository<Template,Long> {
}
