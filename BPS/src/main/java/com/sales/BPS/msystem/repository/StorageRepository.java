package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, String> {
    // 기본 CRUD 메소드와 함께 findAll() 메소드가 제공됩니다.
}