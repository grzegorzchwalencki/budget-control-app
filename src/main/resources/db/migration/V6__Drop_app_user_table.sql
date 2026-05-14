ALTER TABLE expenses
    DROP CONSTRAINT IF EXISTS fk_expenses_user;

ALTER TABLE expenses
    DROP CONSTRAINT IF EXISTS fk_expenses_category;

DROP TABLE IF EXISTS app_user;