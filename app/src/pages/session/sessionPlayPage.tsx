import React, { useEffect, useState } from "react";
import Question from "../../types/question";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { quizApi } from "../config/axiosApi";
import { Alternative } from "../../types/alternative";

export default function SessionPlayPage() {
  const [question, setQuestion] = useState<Question | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    getCurrentQuestion();
  }, []);

  const getCurrentQuestion = () => {
    const sessionKey = Cookies.get("sessionKey");

    quizApi
      .get<Question>(`session/${sessionKey}/current-question`)
      .then((response) => {
        setQuestion(response.data);
      })
      .catch((error) => {
        console.error("Failed to get question:", error);
        alert("Failed to get question. Returning to lobby...");
        navigate("/lobby");
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
        alert(
          'Next question is ready... Click "Get Current Question" to load it.',
        );
      })
      .catch((error) => {
        console.error("Failed to change question:", error);
        alert("Failed to change questions...");
      });
  };

  const handleAlternativeClick = (alternative: Alternative) => {
    alert(`You selected alternative: ${alternative.alternativeText}`);
  };

  return (
    <div>
      <h1>Quiz :)</h1>
      {question ? (
        <div>
          <h2>{question.questionText}</h2>
          <div>
            {question.alternatives.map((alternative, index) => (
              <button
                key={index}
                onClick={() => handleAlternativeClick(alternative)}
              >
                {alternative.alternativeKey}: {alternative.alternativeText}
              </button>
            ))}
          </div>
        </div>
      ) : (
        <p>Loading question...</p>
      )}
      <button onClick={getCurrentQuestion}>Get Current Question</button>
      <button onClick={putNextQuestion}>Next Question</button>
    </div>
  );
}
