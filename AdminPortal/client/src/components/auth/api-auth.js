import axios from 'axios';
//API Manager for Authentication
const signin = ({username,password}) => {
    console.log({username,password});
    return  axios.post('/users/login',{username,password})
                .then((response) => {return response.data})
                .catch((err) => console.log(err))   
}

/*no api for sign out
const signout = () => {
return fetch('/auth/signout/', {
    method: 'GET',
}).then(response => {
    return response.json()
}).catch((err) => console.log(err))
}*/
  
export {
    signin
}
  