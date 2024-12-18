import axios from "axios";

const apiUrl = "http://gateway:8000/";

export const quizApi = axios.create({
  baseURL: `${apiUrl}quiz/`,
});
