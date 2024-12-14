import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import CreatePage from "../session/createPage";
import LobbyPage from "../session/lobbyPage";
import PlayPage from "../session/playPage";

export default function App() {
  return (
    <Router basename={"/pg3402_exam"}>
      <Routes>
        <Route path={"/"} element={<CreatePage />} />
        <Route path={"/lobby"} element={<LobbyPage />} />
        <Route path={"/play"} element={<PlayPage />} />
      </Routes>
    </Router>
  );
}
