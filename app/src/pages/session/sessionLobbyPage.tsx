import React, { useEffect, useState } from "react";
import { Session } from "../../types/session";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { quizApi } from "../config/axiosApi";

export default function SessionLobbyPage() {
  const [session, setSession] = useState<Session | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const sessionKey = Cookies.get("sessionKey");

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    quizApi
      .get<Session>(`session/info/${sessionKey}`)
      .then((response) => {
        setSession(response.data);
      })
      .catch((error) => {
        console.error("Failed to get session details:", error);
        alert("Failed to get session details. Returning to start...");
        navigate("/");
      });
  }, [navigate]);

  const startQuiz = () => {
    if (session) {
      navigate("/start");
    }
  };

  return (
    <div>
      {session ? (
        <div>
          <h1>{session?.sessionKey} lobby</h1>
          <p>Theme: {session.theme}</p>
          <button onClick={startQuiz}>Start Quiz</button>
        </div>
      ) : (
        <p>Loading session...</p>
      )}
    </div>
  );
}
