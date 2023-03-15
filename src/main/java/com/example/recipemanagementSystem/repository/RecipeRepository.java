package com.example.recipemanagementSystem.repository;

import com.example.recipemanagementSystem.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipeRepository extends JpaRepository<Recipe,Integer> {

}
