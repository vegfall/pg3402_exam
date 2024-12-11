import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import CreateSessionPage from "../session/createSessionPage";
import SessionLobbyPage from "../session/sessionLobbyPage";
import SessionPlayPage from "../session/sessionPlayPage";

export default function App() {
  return (
    <Router basename={"/pg3402_exam"}>
      <Routes>
        <Route path={"/"} element={<CreateSessionPage />} />
        <Route path={"/lobby"} element={<SessionLobbyPage />} />
        <Route path={"/play"} element={<SessionPlayPage />} />
      </Routes>
    </Router>
  );
}
