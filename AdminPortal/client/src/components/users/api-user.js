import axios from "axios";

const user = {
  createUser(createUserDto, token) {
    return axios
      .post("http://parrotsays.tk:8167/api/users/createuser", createUserDto, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        return response.data;
      })
      .catch((err) => console.log(err));
  },
};

export default user;
