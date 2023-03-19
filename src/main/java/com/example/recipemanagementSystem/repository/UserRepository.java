package com.example.recipemanagementSystem.repository;

import com.example.recipemanagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
