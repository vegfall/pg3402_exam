CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS scores (
    score_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    session_key VARCHAR(255) NOT NULL,
    total_score INT NOT NULL,
    chosen_alternatives VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);