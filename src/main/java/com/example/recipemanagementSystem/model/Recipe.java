package com.example.recipemanagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Recipe_ID")
    private int recipe_id;
    @Column(name = "Recipe_Name")
    private String recipe_Name;
    @Column(name = "Recipe_Ingredients")
    private String recipe_Ingredients;
    @Column(name = "Recipe_Instructions")
    private String recipe_Instructions;
    @Column(name = "Comments")
    private String comment;
}
