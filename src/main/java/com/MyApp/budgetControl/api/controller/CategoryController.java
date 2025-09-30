package com.MyApp.budgetControl.api.controller;

import com.MyApp.budgetControl.domain.ServicesOrchestrator;
import com.MyApp.budgetControl.domain.category.dto.CategoryRequestDTO;
import com.MyApp.budgetControl.domain.category.dto.CategoryResponseDTO;
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category")
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final ServicesOrchestrator servicesOrchestrator;

  @Operation(summary = "Get the list of categories")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found a categories"),
      @ApiResponse(responseCode = "404", description = "Categories not found")})
  @GetMapping
  public List<CategoryResponseDTO> getCategories() {
    return servicesOrchestrator.findAllCategories();
  }

  @Operation(summary = "Create new category")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created the category")})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addNewCategory(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Category to create", required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ExpenseRequestDTO.class),
              examples = @ExampleObject(value =
                  "{ \"categoryName\": \"exampleCategoryName\"}")))
      @Valid @RequestBody CategoryRequestDTO newCategory) {
    servicesOrchestrator.saveCategory(newCategory);
  }
}
