import Cookies from "js-cookie";
import { redirectToGitHub } from "../utils/authRedirect";
import { JSX } from "react";

export default function AuthenticationRequired({
  children,
}: {
  children: JSX.Element;
}) {
  const token = Cookies.get("githubToken");

  if (!token) {
    redirectToGitHub();
    return null;
  }

  return children;
}
