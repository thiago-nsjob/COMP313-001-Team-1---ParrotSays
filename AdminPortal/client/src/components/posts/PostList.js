import React, { useState, useEffect } from 'react';
import { PostAPI } from './api-post';


export const PostList =() =>{
    
    useEffect(() => {
         PostAPI.getAllPosts();
      }, []); 

    const [data, setData] = useState([]);
  
      return (
    
          
      <div>

      </div>
    );

    
}




export default PostList;