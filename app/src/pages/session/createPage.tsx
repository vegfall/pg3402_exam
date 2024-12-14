import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { quizApi } from "../config/axiosApi";
import { Session } from "../../types/session";
import { CreateSessionRequest } from "../../types/createSessionRequest";
import Cookie from "js-cookie";

//https://www.npmjs.com/package/js-cookie
export default function CreatePage() {
  const [theme, setTheme] = useState<string>("");
  const [numberOfAlternatives, setNumberOfAlternatives] = useState<number>(4);
  const [username, setUsername] = useState<string>("");
  const navigate = useNavigate();

  const createSession = () => {
    const request: CreateSessionRequest = {
      theme,
      numberOfAlternatives,
      username,
    };

    quizApi
      .post("session/create", request)
      .then((response) => {
        const session: Session = response.data;

        Cookie.set("sessionKey", session.sessionKey);

        navigate("/lobby", { state: session });
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
          Theme: (Recommended to use github username for consistency)
          <br />
          <input
            type={"text"}
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder={"Username"}
          />
        </label>
      </div>
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
