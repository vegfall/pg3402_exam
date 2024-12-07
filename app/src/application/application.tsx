import React, { useEffect, useState } from "react";
import axios from "axios";

export default function Application() {
  const [message, setMessage] = useState<string>("Getting message...");

  useEffect(() => {
    axios
      .get("http://127.0.0.1:8080/api/test")
      .then((response) => {
        setMessage(response.data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setMessage("Error fetching message");
      });
  }, []);

  return (
    <>
      <h1>Message from backend:</h1>
      <p>{message}</p>
    </>
  );
}
