import { githubAuthConfig } from "./authConfig";

export const redirectToGitHub = () => {
  const { clientId, authorizationEndpoint, redirectUri, scopes } =
    githubAuthConfig;

  const params = {
    client_id: clientId,
    redirect_uri: redirectUri,
    scope: scopes.join(" "),
  };

  window.location.href = `${authorizationEndpoint}?${new URLSearchParams(params)}`;
};
