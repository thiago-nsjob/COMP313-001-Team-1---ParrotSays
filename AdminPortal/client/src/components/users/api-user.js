import axios from 'axios';

const user = {
    createUser(createUserDto,token) {
        return axios
          .post('/users/createuser',createUserDto,{ headers: {"Authorization" : `Bearer ${token}`} })
                    .then((response) => {return response.data})
                    .catch((err) => console.log(err));   
    }
}

export default user;