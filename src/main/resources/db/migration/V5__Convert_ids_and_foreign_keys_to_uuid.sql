DO $$
    DECLARE r record;
    BEGIN
        FOR r IN (
            SELECT constraint_name
            FROM information_schema.table_constraints
            WHERE table_name = 'expenses'
              AND constraint_type = 'FOREIGN KEY'
        )
            LOOP
                EXECUTE format(
                        'ALTER TABLE expenses DROP CONSTRAINT IF EXISTS %I',
                        r.constraint_name
                        );
            END LOOP;
    END $$;

DO $$
    DECLARE r record;
    BEGIN
        FOR r IN (
            SELECT constraint_name, table_name
            FROM information_schema.table_constraints
            WHERE constraint_type = 'FOREIGN KEY'
              AND table_name IN ('app_user', 'categories')
        )
            LOOP
                EXECUTE format(
                        'ALTER TABLE %I DROP CONSTRAINT IF EXISTS %I',
                        r.table_name,
                        r.constraint_name
                        );
            END LOOP;
    END $$;

ALTER TABLE app_user
    ALTER COLUMN user_id TYPE uuid USING user_id::uuid;

ALTER TABLE categories
    ALTER COLUMN category_id TYPE uuid USING category_id::uuid;

-- 4. THEN convert child table columns
ALTER TABLE expenses
    ALTER COLUMN user_id_user_id TYPE uuid USING user_id_user_id::uuid;

ALTER TABLE expenses
    ALTER COLUMN category_id_category_id TYPE uuid USING category_id_category_id::uuid;

ALTER TABLE expenses
    ADD CONSTRAINT fk_expenses_user
        FOREIGN KEY (user_id_user_id)
            REFERENCES app_user(user_id);

ALTER TABLE expenses
    ADD CONSTRAINT fk_expenses_category
        FOREIGN KEY (category_id_category_id)
            REFERENCES categories(category_id);