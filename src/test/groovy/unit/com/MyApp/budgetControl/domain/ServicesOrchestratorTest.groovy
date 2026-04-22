package com.MyApp.budgetControl.domain

import com.MyApp.budgetControl.domain.category.CategoryEntity
import com.MyApp.budgetControl.domain.category.CategoryService
import com.MyApp.budgetControl.domain.category.dto.CategoryRequestDTO
import com.MyApp.budgetControl.domain.category.dto.CategoryResponseDTO
import com.MyApp.budgetControl.domain.expense.ExpenseEntity
import com.MyApp.budgetControl.domain.expense.ExpenseService
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO
import com.MyApp.budgetControl.domain.expense.dto.ExpenseResponseDTO
import com.MyApp.budgetControl.domain.user.UserEntity
import com.MyApp.budgetControl.domain.user.UserService
import com.MyApp.budgetControl.domain.user.dto.UserRequestDTO
import com.MyApp.budgetControl.domain.user.dto.UserResponseDTO
import spock.lang.Specification

import java.time.Instant

import static java.util.Collections.emptyList

class ServicesOrchestratorTest extends Specification {

    def categoryService = Mock(CategoryService)
    def expenseService = Mock(ExpenseService)
    def userService = Mock(UserService)

    def orchestrator = new ServicesOrchestrator(categoryService, expenseService, userService)

    def id = "123"
    def name = "testname"


    def "saveCategory should call service and return DTO"() {
        given:
            def categoryDTO = new CategoryRequestDTO(name)

        when:
            def result = orchestrator.saveCategory(categoryDTO)

        then:
            1 * categoryService.saveCategory(_ as CategoryRequestDTO) >>
                    new CategoryEntity(id, name, emptyList())

            result.categoryId == id
            result.categoryName == name
    }

    def "findAllCategories should call method findAllCategories on categoryService"() {
        given:
            def categoryDTO = Mock(CategoryResponseDTO)

            1 * categoryService.findAllCategories() >> List.of(categoryDTO)
        when:
            def result = orchestrator.findAllCategories()
        then:
            result == List.of(categoryDTO)
    }

    def "findCategoryById should get entity from service and map to DTO"() {
        given:
            def categoryEntity = new CategoryEntity(id, name, emptyList())
            def expectedDTO = new CategoryResponseDTO(categoryEntity)

        when:
            def result = orchestrator.findCategoryById(id)

        then:
            1 * categoryService.findCategoryById(id) >> categoryEntity
            result == expectedDTO
    }

    def "saveExpense should call method save on expenseService"() {
        given:
            def expectedCost = BigDecimal.valueOf(123)
            def categoryEntity = new CategoryEntity(id, name, emptyList())
            def userEntity = new UserEntity(id, name, name + "@email.com", emptyList())
            def expenseEntity = new ExpenseEntity(id, expectedCost, categoryEntity, name, Instant.now(), userEntity)
            def expectedResponseDTO = new ExpenseResponseDTO(expenseEntity)
            def requestDTO = new ExpenseRequestDTO(expectedCost, id, name, id)

        when:
            def response = orchestrator.saveExpense(requestDTO)
        then:
            1 * categoryService.findCategoryById(_) >> categoryEntity
            1 * userService.findUserById(_) >> userEntity
            1 * expenseService.saveExpense(requestDTO, categoryEntity, userEntity) >> expenseEntity
            response == expectedResponseDTO
    }

    def "findAllExpenses should call method findAll on expenseService"() {
        given:
            def expenseDTO = Mock(ExpenseResponseDTO)
            1 * expenseService.findAllExpenses() >> List.of(expenseDTO)
        when:
            def result = orchestrator.findAllExpenses()
        then:
            result == List.of(expenseDTO)

    }

    def "findExpenseById should call method findAll on expenseService"() {
        given:
            def expenseDTO = Mock(ExpenseResponseDTO)

        when:
            def result = orchestrator.findExpenseById(id)

        then:
            result instanceof ExpenseResponseDTO
            1 * expenseService.findExpenseById(id) >> expenseDTO
    }

    def "deleteExpenseById should call method deleteExpenseById on expenseService"() {
        when:
            orchestrator.deleteExpenseById(id)
        then:
            1 * expenseService.deleteExpenseById(id)
    }

    def "saveUser should call method save on userService"() {
        given:
            def userEmail = name + "@mail.com"
            def requestDTO = new UserRequestDTO(name, userEmail)
            def userEntity = new UserEntity(id, name, userEmail, emptyList())
            def expectedResponseDTO = new UserResponseDTO(userEntity)
        when:
            def result = orchestrator.saveUser(requestDTO)
        then:
            1 * userService.saveUser(requestDTO) >> userEntity
            result == expectedResponseDTO
    }

    def "findAllUsers should call method findAllUsers on userService"() {
        given:
            def userDTO = Mock(UserResponseDTO)
            1 * userService.findAllUsers() >> List.of(userDTO)
        when:
            def result = orchestrator.findAllUsers()
        then:
            result == List.of(userDTO)
    }

    def "findUserById should get entity from service and map to DTO"() {
        given:
            def email = "aa@bb.cc"
            def userEntity = new UserEntity(id, name, email, emptyList())
            1 * userService.findUserById(id) >> userEntity

        when:
            def result = orchestrator.findUserById(id)

        then:
            result instanceof UserResponseDTO
            result.userId == id
            result.userName == name
            result.userEmail == email
            result.userExpenses == emptyList()
    }

    def "deleteUserById should call method deleteUserById on userService"() {
        when:
            orchestrator.deleteUserById(id)
        then:
            1 * userService.deleteUserById(id)
    }
}
