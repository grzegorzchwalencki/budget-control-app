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

class ServicesOrchestratorTest extends Specification {

    def categoryService = Mock(CategoryService)
    def categoryDTO = Mock(CategoryRequestDTO)
    def categoryEntity = Mock(CategoryEntity)

    def expenseService = Mock(ExpenseService)
    def expenseDTO = Mock(ExpenseRequestDTO)
    def expenseEntity = Mock(ExpenseEntity)

    def userService = Mock(UserService)
    def userDTO = Mock(UserRequestDTO)
    def userEntity = Mock(UserEntity)

    def id = "x"
    def name = "name"

    def orchestrator = new ServicesOrchestrator(categoryService, expenseService, userService)


    def "saveCategory should call method save on categoryService"() {
        given:
            categoryService.saveCategory(_) >> categoryEntity
        when:
            orchestrator.saveCategory(categoryDTO)
        then:
            1 * categoryService.saveCategory(categoryDTO)
    }

    def "findAllCategories should call method findAllCategories on categoryService"() {
        given:
            categoryDTO = Mock(CategoryResponseDTO)
            1 * categoryService.findAllCategories() >> List.of(categoryDTO)
        when:
            def result = orchestrator.findAllCategories()
        then:
            assert result == List.of(categoryDTO)
    }

    def "findCategoryById should get entity from service and map to DTO"() {
        given:
            categoryEntity = new CategoryEntity(id, name, Collections.emptyList())
            1 * categoryService.findCategoryById(id) >> categoryEntity

        when:
            def result = orchestrator.findCategoryById(id)

        then:
            assert result instanceof CategoryResponseDTO
            assert result.categoryId == id
            assert result.categoryName == name
    }

    def "saveExpense should call method save on expenseService"() {
        given:
            expenseService.saveExpense(_) >> expenseEntity
            1 * categoryService.findCategoryById(_) >> categoryEntity
            1 * userService.findUserById(_) >> userEntity

        when:
            orchestrator.saveExpense(expenseDTO)
        then:
            1 * expenseService.saveExpense(expenseDTO, categoryEntity, userEntity)
    }

    def "findAllExpenses should call method findAll on expenseService"() {
        given:
            expenseDTO = Mock(ExpenseResponseDTO)
            1 * expenseService.findAllExpenses() >> List.of(expenseDTO)
        when:
            def result = orchestrator.findAllExpenses()
        then:
            assert result == List.of(expenseDTO)

    }

    def "findExpenseById should call method findAll on expenseService"() {
        given:
            expenseDTO = Mock(ExpenseResponseDTO)
            1 * expenseService.findExpenseById(id) >> expenseDTO

        when:
            def result = orchestrator.findExpenseById(id)

        then:
            assert result instanceof ExpenseResponseDTO
    }

    def "deleteExpenseById should call method deleteExpenseById on expenseService"() {
        when:
            orchestrator.deleteExpenseById(id)
        then:
            1 * expenseService.deleteExpenseById(id)
    }

    def "saveUser should call method save on userService"() {
        given:
            userService.saveUser(_) >> userEntity
        when:
            orchestrator.saveUser(userDTO)
        then:
            1 * userService.saveUser(userDTO)
    }

    def "findAllUsers should call method findAllUsers on userService"() {
        given:
            userDTO = Mock(UserResponseDTO)
            1 * userService.findAllUsers() >> List.of(userDTO)
        when:
            def result = orchestrator.findAllUsers()
        then:
            assert result == List.of(userDTO)
    }

    def "findUserById should get entity from service and map to DTO"() {
        given:
            def email = "aa@bb.cc"
            userEntity = new UserEntity(id, name, email, Collections.emptyList())
            1 * userService.findUserById(id) >> userEntity

        when:
            def result = orchestrator.findUserById(id)

        then:
            assert result instanceof UserResponseDTO
            assert result.userId == id
            assert result.userName == name
            assert result.userEmail == email
            assert result.userExpenses == Collections.emptyList()
    }

    def "deleteUserById should call method deleteUserById on userService"() {
        when:
            orchestrator.deleteUserById(id)
        then:
            1 * userService.deleteUserById(id)
    }
}
