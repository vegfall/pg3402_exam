import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LobbyPage from "../session/lobbyPage";
import PlayPage from "../session/playPage";
import ResultPage from "../session/resultPage";
import HomePage from "../session/homePage";
import CreatePage from "../session/createPage";

export default function App() {
  return (
    <Router basename={"/pg3402_exam"}>
      <Routes>
        <Route path={"/"} element={<HomePage />} />
        <Route path={"/create"} element={<CreatePage />} />
        <Route path={"/lobby"} element={<LobbyPage />} />
        <Route path={"/play"} element={<PlayPage />} />
        <Route path={"/result"} element={<ResultPage />} />
      </Routes>
    </Router>
  );
}
