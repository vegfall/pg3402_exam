CREATE TABLE sessions (
      session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      session_key VARCHAR(255) NOT NULL,
      theme VARCHAR(255) NOT NULL,
      number_of_alternatives INT NOT NULL,
      username VARCHAR(255) NOT NULL,
      current_question_key INT NOT NULL
);