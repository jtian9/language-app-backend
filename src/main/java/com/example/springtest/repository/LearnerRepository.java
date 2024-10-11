package com.example.springtest.repository;

import com.example.springtest.entity.Learner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LearnerRepository extends CrudRepository<Learner, Integer> {
    Learner findByUsername(String username);
}
