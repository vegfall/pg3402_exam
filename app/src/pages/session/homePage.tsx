import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

export default function HomePage() {
  const [username, setUsername] = useState<string>("");
  const [sessionKey, setSessionKey] = useState<string>("");
  const navigate = useNavigate();

  const handleNewQuiz = () => {
    if (!username) {
      alert("Please provide a username...");
      return;
    }

    Cookies.set("username", username);
    navigate("/create");
  };

  const handleExistingQuiz = () => {
    if (!username || !sessionKey) {
      alert("Please provide a username and session key...");
      return;
    }

    Cookies.set("username", username);
    Cookies.set("sessionKey", sessionKey);
    navigate("/lobby");
  };

  return (
    <div>
      <h1>Welcome to the Quiz Game!</h1>
      <div>
        <label>
          Username:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Enter your username"
          />
        </label>
      </div>
      <div>
        <button onClick={handleNewQuiz}>Start a New Quiz</button>
      </div>
      <div>
        <label>
          Session Key:
          <input
            type="text"
            value={sessionKey}
            onChange={(e) => setSessionKey(e.target.value)}
            placeholder="Enter an existing session key"
          />
        </label>
      </div>
      <div>
        <button onClick={handleExistingQuiz}>Join an Existing Quiz</button>
      </div>
    </div>
  );
}
