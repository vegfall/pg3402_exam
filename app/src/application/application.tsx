import React, { useState } from "react";
import axios from "axios";
import Question from "../types/question";

export default function Application() {
  const [quizData, setQuizData] = useState<Question | null>(null);

  const sessionKey = "1234";

  const getCurrentQuestion = () => {
    axios
      .get(`http://127.0.0.1:8080/quiz/${sessionKey}/current-question`)
      .then((response) => {
        setQuizData(response.data);
      })
      .catch((error) => {
        console.log("Error fetching question:", error);
      });
  };

  const changeQuestion = () => {
    axios
      .put(`http://127.0.0.1:8080/quiz/${sessionKey}/next-question`)
      .then(() => {
        console.log("Successfully changed question...");
      })
      .catch((error) => {
        console.error("Error changing to next question:", error);
      });
  };

  return (
    <div>
      <h1>Question:</h1>
      {quizData ? (
        <div>
          <p>{quizData.questionText}</p>
          <ul>
            {quizData.alternatives.map((alternative, index) => (
              <li key={index}>{alternative}</li>
            ))}
          </ul>
        </div>
      ) : (
        <p>Loading question...</p>
      )}
      <button onClick={getCurrentQuestion}>Get Question</button>
      <button onClick={changeQuestion}>Next Question</button>
    </div>
  );
}
