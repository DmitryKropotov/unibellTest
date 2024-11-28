package org.example.repository;

import org.example.model.dao.ClientContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientContactRepository extends JpaRepository<ClientContact, Integer> {
    List<ClientContact> findByClientId(int clientId);
}
