package com.kateringapp.backend.repositories;

import com.kateringapp.backend.entities.client.Client;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, UUID> {

}
