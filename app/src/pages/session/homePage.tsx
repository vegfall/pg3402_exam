import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { LoadSessionRequest } from "../../types/request/loadSessionRequest";
import { quizApi } from "../config/axiosApi";

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
    const request: LoadSessionRequest = { username };

    if (!username || !sessionKey) {
      alert("Please provide a username and session key...");
      return;
    }

    Cookies.set("username", username);

    quizApi
      .put(`/session/${sessionKey}/load`, request)
      .then((response) => {
        const session = response.data;

        Cookies.set("sessionKey", sessionKey);
        navigate("/lobby", { state: session });
      })
      .catch((error) => {
        console.error("Error loading existing session:", error);
        alert("Failed to load session. Please double-check session key...");
      });
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
