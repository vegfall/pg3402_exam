import React, { useEffect, useState } from "react";
import axios from "axios";
import Question from "../types/question";

export default function Application() {
  const [quizData, setQuizData] = useState<Question | null>(null);

  useEffect(() => {
    axios
      .get("http://127.0.0.1:8080/api/test")
      .then((response) => {
        setQuizData(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  alert(quizData?.questionText);

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
    </div>
  );
}
