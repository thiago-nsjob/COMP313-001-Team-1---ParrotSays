import axios from "axios";

const POST_API_ROOT = process.env.REACT_APP_POSTS_API;

export const PostAPI = {
  getAllPosts: async () => {

    let result = await axios.get(POST_API_ROOT + "/post");
    return result.data.data;
  },
};
