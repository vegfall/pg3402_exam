import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import CreateSessionPage from "../session/createSessionPage";

export default function App() {
  return (
    <Router basename={"/pg3402_exam"}>
      <Routes>
        <Route path={"/"} element={<CreateSessionPage />} />
      </Routes>
    </Router>
  );
}
