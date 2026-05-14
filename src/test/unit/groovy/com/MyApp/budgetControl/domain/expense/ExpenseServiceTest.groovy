package com.MyApp.budgetControl.domain.expense

import com.MyApp.budgetControl.domain.category.CategoryEntity
import com.MyApp.budgetControl.domain.expense.dto.ExpenseRequestDTO
import com.MyApp.budgetControl.domain.user.UserEntity
import spock.lang.Specification

import java.time.Instant

class ExpenseServiceTest extends Specification {

    def expenseRepository = Mock(ExpenseRepository)
    def expenseService = new ExpenseService(expenseRepository)

    def catID = UUID.randomUUID()
    def usrID = UUID.randomUUID()
    def category = new CategoryEntity(catID, "testCategory", new ArrayList<>())
    def user = new UserEntity(usrID, "userName", "userId@email.com", new ArrayList<>())
    def dto = new ExpenseRequestDTO(99.00, catID, "expense comment", usrID)

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
                        UUID.randomUUID(),
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
                assert dto.expenseId == expenses.get(index).expenseId
                assert dto.expenseCost== expenses.get(index).expenseCost
                assert dto.categoryId == expenses.get(index).categoryId.getCategoryId()
                assert dto.expenseComment == expenses.get(index).expenseComment
                assert dto.expenseDate == expenses.get(index).expenseDate
                assert dto.userId == expenses.get(index).userId.getUserId()
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
            def expenseId = UUID.randomUUID()
            def expense = new ExpenseEntity(
                    expenseId,
                    BigDecimal.valueOf(10),
                    category,
                    "Comment",
                    Instant.now(),
                    user)
            1 * expenseRepository.findById(_) >> Optional.of(expense)

        when: "call the method"
            def result = expenseService.findExpenseById(expenseId)

        then: "Verify the result is a DTO with correct mapping"
            result.categoryId == expense.categoryId.getCategoryId()
            result.userId == expense.userId.getUserId()
            result.expenseDate == expense.getExpenseDate()
            result.expenseCost == expense.getExpenseCost()
            result.expenseComment == expense.getExpenseComment()
            result.expenseId == expense.getExpenseId()
    }

    def "findExpenseById should return NoSuchElementException when expense not found"() {
        given: "prepare empty response"
            1 * expenseRepository.findById(_) >> Optional.empty()

        when: "call the method"
            expenseService.findExpenseById(UUID.randomUUID())

        then: "Expect NoSuchElementException"
            thrown(NoSuchElementException)
    }

    def "deleteExpenseById should deleteById method on repository when expense exist"() {
        given: "create entity"
            def expenseId = UUID.randomUUID()
            def expense = new ExpenseEntity(
                    expenseId,
                    BigDecimal.valueOf(10),
                    category,
                    "Comment",
                    Instant.now(),
                    user)
            1 * expenseRepository.findById(_) >> Optional.of(expense)

        when: "call the method"
            expenseService.deleteExpenseById(expenseId)

        then: "Expect repository interaction"
            1 * expenseRepository.deleteById(_)
    }

    def "deleteExpenseById should return NoSuchElementException when expense not exist"() {
        given: "create entities and dto"
            1 * expenseRepository.findById(_) >> Optional.empty()

        when: "call the method"
            expenseService.deleteExpenseById(UUID.randomUUID())

        then: "Expect NoSuchElementException"
            thrown(NoSuchElementException)
    }

}
