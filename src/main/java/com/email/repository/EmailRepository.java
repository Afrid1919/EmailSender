package com.email.repository;

import com.email.entity.Email_Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email_Template, Long> {


    Optional<Email_Template> findByTemplateCodeAndActiveTrue(String templateCode);
}
