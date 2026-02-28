package com.MyApp.budgetControl.domain.user

import com.MyApp.budgetControl.domain.user.dto.UserRequestDTO
import spock.lang.Specification

class UserServiceTest extends Specification {

    def userRepository = Mock(UserRepository)
    def userService = new UserService(userRepository)

    def "saveUser - call save method on repository, update relationships and return expected entity"() {
        given: "create entities and dto"
            def dto = new UserRequestDTO(
                    "userName",
                    "user@emial.com")
            userRepository.save(_) >> { UserEntity entity -> return entity }

        when: "call the method"
            userService.saveUser(dto)

        then: "Verify repository save method called"
            1 * userRepository.save(_)
    }

    def "saveUser should propagate repository exception"() {
        given: "create entities and dto"
            def dtoMock = Mock(UserRequestDTO)

            1 * userRepository.save(_) >> { throw new RuntimeException("error message") }

        when: "call the method"
            userService.saveUser(dtoMock)

        then: "Expect RuntimeException"
            thrown(RuntimeException)
    }

    def "findAllUsers should return #expectedSize DTOs when repository returns #repoSize users"() {
        given: "create list of entities"
            def users = (0..<repoSize).collect {
                new UserEntity(
                        UUID.randomUUID().toString(),
                        "User $it",
                        "user@$it" + ".com",
                        Collections.emptyList()
                )
            }

            1 * userRepository.findAll() >> users

        when: "call the method"
            def result = userService.findAllUsers()

        then: "Verify the result is a list of DTOs with correct mapping"
            result.size() == expectedSize
            result.eachWithIndex { dto, index ->
                assert dto.getUserId() == users.get(index).userId
                assert dto.getUserEmail() == users.get(index).getUserEmail()
                assert dto.getUserExpenses().size() == users.get(index).getUserExpenses().size()
            }

        where:
            repoSize | expectedSize
            1        | 1
            4        | 4

    }

    def "findAllUsers should return empty list if no users exist"() {
        given: "create empty list"
            def users = new ArrayList()
            1 * userRepository.findAll() >> users

        when: "call the method"
            def result = userService.findAllUsers()

        then: "Verify the result is an empy list"
            assert result.isEmpty()
    }

    def "findUserById should return Entity with correct values when user found"() {
        given: "create entity"
            def user = new UserEntity(
                    UUID.randomUUID().toString(),
                    "userName",
                    "user@emial.com",
                    Collections.emptyList())
            1 * userRepository.findById(_) >> Optional.of(user)

        when: "call the method"
            def result = userService.findUserById("userId")

        then: "Verify the result is a DTO with correct mapping"
            assert result.getUserId() == user.getUserId()
            assert result.getUserName() == user.getUserName()
            assert result.getUserEmail() == user.getUserEmail()
            assert result.getUserExpenses() == user.getUserExpenses()
    }

    def "findUserById should return NoSuchElementException when user not found"() {
        given: "prepare empty response"
            1 * userRepository.findById(_) >> Optional.empty()

        when: "call the method"
            userService.findUserById("userId")

        then: "Expect NoSuchElementException"
            thrown(NoSuchElementException)
    }

    def "deleteUserById should deleteById method on repository when user exist"() {
        given: "create entity"
            def user = new UserEntity(
                    UUID.randomUUID().toString(),
                    "userName",
                    "user@emial.com",
                    Collections.emptyList())
            1 * userRepository.findById(_) >> Optional.of(user)

        when: "call the method"
            userService.deleteUserById("userId")

        then: "Expect repository interaction"
            1 * userRepository.deleteById(_)
    }

    def "deleteUserById should return NoSuchElementException when user not exist"() {
        given: "create entities and dto"
            1 * userRepository.findById(_) >> Optional.empty()

        when: "call the method"
            userService.deleteUserById("userId")

        then: "Expect NoSuchElementException"
            thrown(NoSuchElementException)
    }

}

