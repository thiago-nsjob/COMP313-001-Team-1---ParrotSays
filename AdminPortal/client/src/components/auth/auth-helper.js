import axios from "axios";

const authHelper = {
  isAuthenticated() {
    if (typeof window == "undefined") return false;

    if (sessionStorage.getItem("jwt"))
      return JSON.parse(sessionStorage.getItem("jwt"));
    else return false;
  },
  authenticate(jwt, next) {
    if (typeof window !== "undefined")
      sessionStorage.setItem("jwt", JSON.stringify(jwt));
    next();
  },
  signIn({ username, password }) {
    console.log({ username, password });
    return axios
      .post("http://parrotsays.tk:8167/api/users/login", { 
        "username": username, 
        "password": password 
      })
      .then((response) => {
        return response.data;
      })
      .catch((err) => console.log(err));
  },
  signOut(cb) {
    if (typeof window !== "undefined") {
      sessionStorage.removeItem("jwt");
    }
    document.cookie = "t=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    cb();
  },
};

export default authHelper;
