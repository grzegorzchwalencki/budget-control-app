package com.MyApp.budgetControl.domain.category

import com.MyApp.budgetControl.domain.category.dto.CategoryRequestDTO
import spock.lang.Specification

class CategoryServiceTest extends Specification {

    def categoryRepository = Mock(CategoryRepository)
    def categoryService = new CategoryService(categoryRepository)

    def "saveCategory should throw exception when DTO is null"() {
        when: "Call the method with null DTO"
            categoryService.saveCategory(null)

        then: "Expect NullPointerException"
            thrown(NullPointerException)

        and: "Repository interactions"
            0 * categoryRepository._
    }

    def "saveCategory should propagate repository exception"() {
        given: "A sample DTO"
            def dto = new CategoryRequestDTO("Test Category")

        and: "Mock repository to throw exception"
            1 * categoryRepository.save(_) >> { throw new RuntimeException() }

        when: "Call the method"
            categoryService.saveCategory(dto)

        then: "Expect RuntimeException"
            thrown(RuntimeException)
    }

    def "saveCategory should map DTO to entity and call repository save method successfully"() {
        given: "A sample DTO"
            def dto = new CategoryRequestDTO("Test Category")

        and: "Mock repository to simulate save with ID generation"
            1 * categoryRepository.save(_) >> { CategoryEntity entity -> return entity }

        when: "Call the method"
            def result = categoryService.saveCategory(dto)

        then: "Verify returned entity matches expectations"
            result.categoryId != null
            result.categoryName == "Test Category"
    }

    def "findAllCategories should return #expectedSize DTOs when repository returns #repoSize categories"() {
        given: 'Mocked repository response'
            def categories = (0..<repoSize).collect {
                new CategoryEntity(
                        UUID.randomUUID().toString(),
                        "Category $it",
                        [] as List
                )
            }
            1 * categoryRepository.findAll() >> categories

        when: 'Calling the method'
            def result = categoryService.findAllCategories()

        then: 'Verify the result is a list of DTOs with correct mapping'
            result.size() == expectedSize
            if (expectedSize > 0) {
                result.eachWithIndex { dto, index ->
                    assert dto.categoryId == categories.get(index).categoryId
                    assert dto.categoryName == categories.get(index).categoryName
                }
            }
        where:
            repoSize | expectedSize
            0        | 0
            1        | 1
            4        | 4
    }

    def "findAllCategories should propagate repository throwing an exception"() {
        given: 'Repository throws an exception'
            1 * categoryRepository.findAll() >> { throw new RuntimeException("Database error") }

        when: 'Calling the method'
            categoryService.findAllCategories()

        then: 'Exception is propagated'
            thrown(RuntimeException)
    }

    def "findCAtegoryById should return category"() {
        given: 'Mocked repository response'
            def category = new CategoryEntity(
                    "categoryId",
                    "categoryName",
                    Collections.emptyList())
            1 * categoryRepository.findById(_) >> Optional.of(category)

        when: 'Calling the method'
            def result = categoryService.findCategoryById("categoryId")

        then: 'Verify'
            assert result.categoryId == category.categoryId
            assert result.categoryName == category.categoryName
            assert result.categoryExpenses == category.categoryExpenses
            assert result.categoryExpenses.isEmpty()

    }

    def "findCAtegoryById should return NoSuchElementException when category not found"() {
        given: 'Mocked repository response'

            1 * categoryRepository.findById(_) >> Optional.empty()

        when: 'Calling the method'
            categoryService.findCategoryById("categoryId")

        then: 'Verify'
            thrown(NoSuchElementException)
    }

}