package com.example.mobilephonemanagement.repository;

import com.example.mobilephonemanagement.model.MobilePhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobilePhoneRepository extends JpaRepository<MobilePhone, Long> {
}
