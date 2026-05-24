package com.example.Pad.repository;

import com.example.Pad.entity.Pad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PadRepository extends JpaRepository<Pad,Integer> {
    Optional<Pad> findByPadKey(String padKey);
    boolean existsByPadKey(String padKey);
}
