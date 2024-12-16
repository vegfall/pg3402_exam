import React, { useEffect, useState } from "react";
import { RevealScore } from "../../types/conclusion/revealScore";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { quizApi } from "../config/axiosApi";
import { RevealQuestion } from "../../types/conclusion/revealQuestion";
import { RevealAlternative } from "../../types/conclusion/revealAlternative";

export default function ResultPage() {
  const [score, setScore] = useState<RevealScore | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const sessionKey = Cookies.get("sessionKey");
    const username = Cookies.get("username");

    if (!sessionKey || !username) {
      alert("Missing session or username. Returning to start...");
      navigate("/");
      return;
    }

    fetchScore(sessionKey, username);
  }, [navigate]);

  const fetchScore = (sessionKey: string, username: string) => {
    quizApi
      .get<RevealScore>(`session/${sessionKey}/${username}/score`)
      .then((response) => {
        setScore(response.data);
      })
      .catch((error) => {
        console.error("Failed to fetch score:", error);
        alert("Filed to get score. Returning to start...");
        navigate("/");
      });
  };

  const getAlternativeStyle = (
    alternative: RevealAlternative,
    selectedKey: number,
  ) => {
    if (alternative.correct)
      return { backgroundColor: "green", color: "white" };
    if (alternative.alternativeKey === selectedKey)
      return { backgroundColor: "red", color: "white" };
    return {};
  };

  const navigateHome = () => {
    Cookies.remove("sessionKey");
    Cookies.remove("username");
    navigate("/");
  };

  return (
    <div>
      <h1>Quiz Results</h1>
      {score ? (
        <div>
          <h2>Overall Score: {score.score}</h2>
          <div>
            {score.questions.map((question: RevealQuestion, index) => (
              <div key={index} style={{ marginBottom: "20px" }}>
                <h3>
                  {index + 1}. {question.questionText}
                </h3>
                <ul>
                  {question.alternatives.map((alternative) => (
                    <li key={alternative.alternativeKey}>
                      <button
                        style={getAlternativeStyle(
                          alternative,
                          question.chosenAlternativeKey,
                        )}
                        disabled
                      >
                        {alternative.alternativeText}
                      </button>
                    </li>
                  ))}
                </ul>
              </div>
            ))}
          </div>
          <button onClick={navigateHome}>Home</button>
        </div>
      ) : (
        <p>Loading results...</p>
      )}
    </div>
  );
}
