package com.MyApp.budgetControl.domain.expense

import com.MyApp.budgetControl.domain.category.CategoryEntity
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO
import com.MyApp.budgetControl.domain.user.UserEntity
import spock.lang.Specification

import java.time.Instant

class ExpenseServiceTest extends Specification {

    def expenseRepository = Mock(ExpenseRepository)
    def expenseService = new ExpenseService(expenseRepository)

    def category = new CategoryEntity("categoryId", "testCategory", new ArrayList<>())
    def user = new UserEntity("userId", "userName", "user@email.com", new ArrayList<>())
    def dto = new ExpenseRequestDTO(99.00, "categoryId", "expense comment", "userId")

    def "saveExpense - call save method on repository, update relationships and return expected entity"() {
        given: "create entities and dto"
            1 * expenseRepository.save(_) >> { ExpenseEntity entity -> return entity }

        when: "call the method"
            def result = expenseService.saveExpense(dto, category, user)

        then: "Verify returned entity matches expectations and updated relations"
            with(category.categoryExpenses) {
                size() == 1
                getFirst() == result
            }
            with(user.userExpenses) {
                size() == 1
                getFirst() == result
            }
            result.expenseId != null
            result.expenseCost == dto.expenseCost
            result.expenseComment == dto.expenseComment
            result.categoryId.categoryId == dto.categoryId
            result.userId.userId == dto.userId
    }

    def "saveExpense should propagate repository exception"() {
        given: "create entities and dto"
            def dtoMock = Mock(ExpenseRequestDTO)
            def categoryMock = Mock(CategoryEntity)
            def userMock = Mock(UserEntity)

            1 * expenseRepository.save(_) >> { throw new RuntimeException("error message") }

        when: "call the method"
            expenseService.saveExpense(dtoMock, categoryMock, userMock)

        then: "Expect RuntimeException"
            thrown(RuntimeException)

    }

    def "findAllExpenses should return #expectedSize DTOs when repository returns #repoSize expenses"() {
        given: "create list of entities"
            def expenses = (0..<repoSize).collect {
                new ExpenseEntity(
                        UUID.randomUUID().toString(),
                        BigDecimal.valueOf(10),
                        category,
                        "Comment $it",
                        Instant.now(),
                        user
                )
            }

            1 * expenseRepository.findAll() >> expenses

        when: "call the method"
            def result = expenseService.findAllExpenses()

        then: "Verify the result is a list of DTOs with correct mapping"
            result.size() == expectedSize
            result.eachWithIndex { dto, index ->
                dto.getExpenseId() == expenses.get(index).expenseId
                dto.getExpenseCost() == expenses.get(index).expenseCost
                dto.getCategoryId() == expenses.get(index).categoryId.getCategoryId()
                dto.getExpenseComment() == expenses.get(index).expenseComment
                dto.getExpenseDate() == expenses.get(index).expenseDate
                dto.getUserId() == expenses.get(index).userId.getUserId()
            }

        where:
            repoSize | expectedSize
            1        | 1
            4        | 4

    }

    def "findAllExpenses should return empty list if no expenses exist"() {
        given: "create empty list"
            def expenses = new ArrayList()
            1 * expenseRepository.findAll() >> expenses

        when: "call the method"
            def result = expenseService.findAllExpenses()

        then: "Verify the result is an empy list"
            result.isEmpty()
    }

    def "findExpenseById should return DTO with correct values when expense found"() {
        given: "create entity"
            def expense = new ExpenseEntity(
                    UUID.randomUUID().toString(),
                    BigDecimal.valueOf(10),
                    category,
                    "Comment",
                    Instant.now(),
                    user)
            1 * expenseRepository.findById(_) >> Optional.of(expense)

        when: "call the method"
            def result = expenseService.findExpenseById("expenseId")

        then: "Verify the result is a DTO with correct mapping"
            result.getCategoryId() == expense.categoryId.getCategoryId()
            result.getUserId() == expense.userId.getUserId()
            result.getExpenseDate() == expense.getExpenseDate()
            result.getExpenseCost() == expense.getExpenseCost()
            result.getExpenseComment() == expense.getExpenseComment()
            result.getExpenseId() == expense.getExpenseId()
    }

    def "findExpenseById should return NoSuchElementException when expense not found"() {
        given: "prepare empty response"
            1 * expenseRepository.findById(_) >> Optional.empty()

        when: "call the method"
            expenseService.findExpenseById("expenseId")

        then: "Expect NoSuchElementException"
            thrown(NoSuchElementException)
    }

    def "deleteExpenseById should deleteById method on repository when expense exist"() {
        given: "create entity"
            def expense = new ExpenseEntity(
                    UUID.randomUUID().toString(),
                    BigDecimal.valueOf(10),
                    category,
                    "Comment",
                    Instant.now(),
                    user)
            1 * expenseRepository.findById(_) >> Optional.of(expense)

        when: "call the method"
            expenseService.deleteExpenseById("expenseId")

        then: "Expect repository interaction"
            1 * expenseRepository.deleteById(_)
    }

    def "deleteExpenseById should return NoSuchElementException when expense not exist"() {
        given: "create entities and dto"
            1 * expenseRepository.findById(_) >> Optional.empty()

        when: "call the method"
            expenseService.deleteExpenseById("expenseId")

        then: "Expect NoSuchElementException"
            thrown(NoSuchElementException)
    }

}
