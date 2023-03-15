package com.example.recipemanagementSystem.controller;

import com.example.recipemanagementSystem.exception.ResourceNotFoundException;
import com.example.recipemanagementSystem.model.Recipe;
import com.example.recipemanagementSystem.repository.RecipeRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Recipe")
public class RecipeController {
    @Autowired
    RecipeRepository recipeRepository;
    @GetMapping(value = "/find-all-recipe")
    public List<Recipe> getAllRecipe(){
        return recipeRepository.findAll();
    }
    @GetMapping(value = "find-recipeById/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id){
        Recipe recipe= recipeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Recipe not exist with id : "+id));
        return ResponseEntity.ok(recipe);
    }
    @PostMapping(value = "/add-recipe")
    public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe){
        JSONObject jsonObject= new JSONObject(recipe);
        List<String> validationList = validateRecipe(jsonObject);

        if(validationList.isEmpty()){
           Recipe recipe1 = setRecipe(jsonObject);
           recipeRepository.save(recipe1);
            return new ResponseEntity<>("Recipe created",HttpStatus.CREATED);
        }
        else{
            String[] answer = Arrays.copyOf(validationList.toArray(),validationList.size(),String[].class);
            return new ResponseEntity<>("Please these needed parameters --> "+Arrays.toString(answer),HttpStatus.BAD_REQUEST);
        }
    }

    private Recipe setRecipe(JSONObject jsonObject) {
        Recipe recipe= new Recipe();
        if(jsonObject.has("recipe_Name")){
            String rname= jsonObject.getString("recipe_Name");
            recipe.setRecipe_Name(rname);
        }
        if(jsonObject.has("recipe_Ingredients")){
            String iname = jsonObject.getString("recipe_Ingredients");
            recipe.setRecipe_Ingredients(iname);
        }
        if(jsonObject.has("recipe_Instructions")){
            String instruct = jsonObject.getString("recipe_Instructions");
            recipe.setRecipe_Instructions(instruct);
        }
        return recipe;
    }

    private List<String> validateRecipe(JSONObject jsonObject) {
        List<String> errorList = new ArrayList<>();
        if(!jsonObject.has("recipe_Name")){
            errorList.add("Recipe Name");
        }
        if(!jsonObject.has("recipe_Ingredients")){
            errorList.add("Recipe Ingredients");
        }
        if(!jsonObject.has("recipe_Instructions")){
            errorList.add("Recipe Instructions");
        }
        return errorList;
    }
    @PutMapping(value = "/updateRecipe/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable int id , @RequestBody String recipeRequest){
        JSONObject jsonObject= new JSONObject(recipeRequest);
        Recipe recipe= recipeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Recipe not exist with id :"+id));
        if(recipe!=null){
            List<String> validationList = validateRecipe(jsonObject);

            if(validationList.isEmpty()){
                Recipe updatedRecipe = setRecipe(jsonObject);
                updatedRecipe.setRecipe_id(id);
                recipeRepository.save(updatedRecipe);
                return new ResponseEntity<>("Recipe updated",HttpStatus.OK);
            }
            else{
                String[] answer = Arrays.copyOf(validationList.toArray(), validationList.size(), String[].class);

                return new ResponseEntity<>("Please pass these mandatory parameters- " + Arrays.toString(answer), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteRecipeById(@PathVariable int id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Recipe not exist with id :"+id));
        if(recipe!=null){
            recipeRepository.deleteById(id);
            return new ResponseEntity<>("Recipe deleted ",HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/add-comment/{id}")
    public ResponseEntity<String> addComment(@RequestBody String requestComment ,@PathVariable int id){
        Recipe recipe= recipeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Recipe not exist with id :"+id));
        recipe.setComment(requestComment);
        recipeRepository.save(recipe);
        return ResponseEntity.ok("Comment Added Successfully");
    }
}
