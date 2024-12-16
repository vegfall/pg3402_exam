-- Insert session with session_key "1234"
INSERT INTO sessions (session_key) VALUES ('1234');

-- Get the session_id for the newly inserted session
SET @session_id = LAST_INSERT_ID();

-- Insert questions for session "1234"
INSERT INTO questions (question_key, question_text, session_id) VALUES
    (1, 'What is the capital of Norway?', @session_id),
    (2, 'Who was the first emperor of Rome?', @session_id),
    (3, 'Does this project follow microservice architecture?', @session_id);

-- Get question IDs for the newly inserted questions
SET @question1_id = (SELECT question_id FROM questions WHERE question_key = 1 AND session_id = @session_id);
SET @question2_id = (SELECT question_id FROM questions WHERE question_key = 2 AND session_id = @session_id);
SET @question3_id = (SELECT question_id FROM questions WHERE question_key = 3 AND session_id = @session_id);

-- Insert alternatives for question 1
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id) VALUES
    (1, 'Oslo', true, 'Yes', @question1_id),
    (2, 'Stockholm', false, 'No', @question1_id),
    (3, 'Copenhagen', false, 'No', @question1_id),
    (4, 'Reykjavik', false, 'No', @question1_id);

-- Insert alternatives for question 2
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id) VALUES
    (1, 'Cato', false, 'No', @question2_id),
    (2, 'Caesar', false, 'No', @question2_id),
    (3, 'Augustus', true, 'Yes', @question2_id);

-- Insert alternatives for question 3
INSERT INTO alternatives (alternative_key, alternative_text, correct, alternative_explanation, question_id) VALUES
    (1, 'Yes', true, 'Yes', @question3_id),
    (2, 'No', false, 'No', @question3_id);
