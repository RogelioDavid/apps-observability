package com.app.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.entity.Client;


 @Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
 
}
