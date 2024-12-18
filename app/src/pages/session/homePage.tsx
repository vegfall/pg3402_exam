import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { LoadSessionRequest } from "../../types/request/loadSessionRequest";
import { quizApi } from "../config/axiosApi";
import { Session } from "../../types/session";

export default function HomePage() {
  const [username, setUsername] = useState<string>("");
  const [sessionKey, setSessionKey] = useState<string>("");
  const [sessions, setSessions] = useState<Session[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    quizApi
      .get("/session/get-sessions")
      .then((response) => {
        setSessions(response.data);
      })
      .catch((error) => {
        console.error("Error fetching sessions:", error);
        alert("Failed to load sessions. Please try again later.");
      });
  }, []);

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

  const handleSelectSession = (key: string) => {
    setSessionKey(key);
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

      <h2>Available Sessions</h2>
      <ul>
        {sessions.map((session) => (
          <li key={session.sessionKey}>
            <span>
              <strong>Session Key:</strong> {session.sessionKey} |{" "}
              <strong>Theme:</strong> {session.theme} |{" "}
            </span>
            <button onClick={() => handleSelectSession(session.sessionKey)}>
              Select
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}
