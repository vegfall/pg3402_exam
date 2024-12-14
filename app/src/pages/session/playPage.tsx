import React, { useEffect, useState } from "react";
import Question from "../../types/question";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { quizApi } from "../config/axiosApi";
import { Alternative } from "../../types/alternative";

export default function PlayPage() {
  const [question, setQuestion] = useState<Question | null>(null);
  const [selectedAlternative, setSelectedAlternative] = useState<number | null>(
    null,
  );
  const navigate = useNavigate();

  useEffect(() => {
    const sessionKey = Cookies.get("sessionKey");

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    getCurrentQuestion(sessionKey);
  }, [navigate]);

  const getCurrentQuestion = (sessionKey: string) => {
    quizApi
      .get<Question>(`session/${sessionKey}/current-question`)
      .then((response) => {
        setQuestion(response.data);
        setSelectedAlternative(null); // Reset selected alternative for the new question
      })
      .catch((error) => {
        console.error("Failed to get question:", error);
        alert("Failed to get question. Returning to start...");
        navigate("/");
      });
  };

  const putNextQuestion = () => {
    const sessionKey = Cookies.get("sessionKey");

    if (!sessionKey) {
      alert("Missing session key. Returning to start...");
      navigate("/");
      return;
    }

    quizApi
      .put(`session/${sessionKey}/next-question`)
      .then(() => {
        getCurrentQuestion(sessionKey); // Fetch the next question
      })
      .catch((error) => {
        console.error("Failed to change question:", error);
        alert("Failed to change question. Returning to start...");
        navigate("/");
      });
  };

  const handleAlternativeClick = (alternative: Alternative) => {
    setSelectedAlternative(alternative.alternativeKey);

    alert(`You selected alternative: ${alternative.alternativeText}`);
  };

  const endSession = () => {
    navigate("/result");
  };

  return (
    <div>
      <h1>Quiz :)</h1>
      {question ? (
        <div>
          <h2>{question.questionText}</h2>
          <div>
            {question.alternatives.map((alternative) => (
              <button
                key={alternative.alternativeKey}
                onClick={() => handleAlternativeClick(alternative)}
                style={{
                  backgroundColor:
                    selectedAlternative === alternative.alternativeKey
                      ? "green"
                      : "",
                }}
              >
                {alternative.alternativeKey}: {alternative.alternativeText}
              </button>
            ))}
          </div>
          {selectedAlternative !== null && (
            <div>
              <button onClick={putNextQuestion}>Next Question</button>
              <button onClick={endSession}>Quit</button>
            </div>
          )}
        </div>
      ) : (
        <p>Loading question...</p>
      )}
    </div>
  );
}
