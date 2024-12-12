package com.volvo.CountryInfoAPI.repository;


import com.volvo.CountryInfoAPI.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}