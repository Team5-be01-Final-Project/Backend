package com.sales.BPS.msales.repository;

import com.sales.BPS.msales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,String> {
}
