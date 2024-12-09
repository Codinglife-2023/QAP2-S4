package com.example.golfclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.golfclub.entity.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    // Additional query methods can be defined here if needed
}
