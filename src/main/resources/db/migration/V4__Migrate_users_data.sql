INSERT INTO users (user_id, user_name, user_email)
SELECT
    user_id::uuid,
    user_name,
    user_email
FROM app_user;