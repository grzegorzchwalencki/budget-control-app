package com.MyApp.budgetControl.api.utils

import spock.lang.Shared

class TestsConstants extends ErrorValidator {

    def final CATEGORIES_PATH = "/categories"
    def final USERS_PATH = "/users"
    def final EXPENSES_PATH = "/expenses"

    @Shared
    def final CONTENT_TYPE_JSON = "application/json"
    @Shared
    def final MAX_CATEGORY_NAME_LENGTH = 64
    @Shared
    def final MAX_EXPENSE_COMMENT_LENGTH = 128

    @Shared
    def final EXPENSE_COST = 999
}
