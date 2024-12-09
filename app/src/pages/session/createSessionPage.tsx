import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { quizApi } from "../config/axiosApi";
import { Session } from "../../types/session";
import { CreateSessionRequest } from "../../types/createSessionRequest";

export default function CreateSessionPage() {
  const [theme, setTheme] = useState<string>("");
  const [numberOfAlternatives, setNumberOfAlternatives] = useState<number>(4);
  const navigate = useNavigate();

  const createSession = () => {
    const request: CreateSessionRequest = { theme, numberOfAlternatives };

    quizApi
      .post("create-session", request)
      .then((response) => {
        const session: Session = response.data;
        navigate(`/session/${session.sessionKey}`, { state: session });
      })
      .catch((error) => {
        console.error("Error creating session:", error);
      });
  };

  return (
    <div>
      <h1>Welcome! Please choose settings for quiz...</h1>
      <div>
        <label>
          Theme:
          <input
            type={"text"}
            value={theme}
            onChange={(e) => setTheme(e.target.value)}
            placeholder={"Quiz Theme"}
          />
        </label>
      </div>
      <div>
        <label>
          Number of alternatives:
          <input
            type="number"
            value={numberOfAlternatives}
            onChange={(e) => setNumberOfAlternatives(parseInt(e.target.value))}
            placeholder={"4"}
            min={2}
            max={6}
          />
        </label>
      </div>
      <button onClick={createSession}>Create Quiz</button>
    </div>
  );
}
