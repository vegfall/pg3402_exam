export const githubAuthConfig = {
  clientId: "Ov23li77pwP3brMTCMF7",
  authorizationEndpoint: "https://github.com/login/oauth/authorize",
  tokenEndpoint: "/api/github-oauth/token",
  redirectUri: "http://localhost:3000/oauth2/callback",
  scopes: ["read:user"],
};
