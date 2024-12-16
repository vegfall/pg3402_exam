import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { quizApi } from "../config/axiosApi";
import { Session } from "../../types/session";
import { CreateSessionRequest } from "../../types/request/createSessionRequest";
import Cookie from "js-cookie";

//https://www.npmjs.com/package/js-cookie
export default function CreatePage() {
  const [theme, setTheme] = useState<string>("");
  const [numberOfAlternatives, setNumberOfAlternatives] = useState<number>(4);
  const navigate = useNavigate();

  const createSession = () => {
    const username = Cookie.get("username");

    if (!username) {
      alert("Missing username. Returning to start...");
      navigate("/");
      return;
    }

    if (!theme) {
      alert("Please provide a theme...");
      return;
    }

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
      <h1>Welcome! Please choose settings for your quiz...</h1>
      <div>
        <label>
          Theme:
          <input
            type="text"
            value={theme}
            onChange={(e) => setTheme(e.target.value)}
            placeholder="Quiz Theme"
          />
        </label>
      </div>
      <div>
        <label>
          Number of Alternatives:
          <input
            type="number"
            value={numberOfAlternatives}
            onChange={(e) => setNumberOfAlternatives(parseInt(e.target.value))}
            placeholder="4"
            min={2}
            max={6}
          />
        </label>
      </div>
      <button onClick={createSession}>Create Quiz</button>
    </div>
  );
}
