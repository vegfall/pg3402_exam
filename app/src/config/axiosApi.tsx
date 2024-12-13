import axios from "axios";

const apiUrl = "http://127.0.0.1:8000/";

export const quizApi = axios.create({
  baseURL: `${apiUrl}quiz/`,
});

export const userApi = axios.create({
  baseURL: `${apiUrl}user/`,
});
