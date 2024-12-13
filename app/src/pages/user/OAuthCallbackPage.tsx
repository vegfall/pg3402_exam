import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Cookies from "js-cookie";

export default function OAuthCallbackPage() {
  const navigate = useNavigate();

  useEffect(() => {
    const handleCallback = async () => {
      const queryParams = new URLSearchParams(window.location.search);
      const code = queryParams.get("code");

      if (!code) {
        alert("Authorization failed. Please try again...");
        navigate("/");
        return;
      }

      try {
        const response = await axios.post("/api/github-oauth/token", { code });
        const { accessToken } = response.data;

        Cookies.set("githubToken", accessToken, {
          secure: true,
          sameSite: "strict",
        });

        navigate("/");
      } catch (error) {
        console.error("Error getting code for token:", error);
        alert("Authorization failed. Please try again...");
        navigate("/");
      }
    };

    handleCallback();
  }, [navigate]);

  return <div>Authenticating... Please wait.</div>;
}
