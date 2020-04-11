import React, { useState, useEffect } from "react";
import { PostAPI } from "./api-post";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
export const PostList = () => {
  const [data, setData] = useState([]);
  const [refresh, setRefresh] = useState(true);

  const fetch = async () => {
    if(refresh){
        let res = await PostAPI.getAllPosts();
        console.log(res);
        setData(res);
        setRefresh(false);
    }
    
  };

  useEffect(() => {
    fetch();
  }, [data]);

  return (
    <div className="container-fluid mt-5" style={{width:"70em", marginTop:"10em"}}>
      {data.map((post, idx) => {
          return (
            <Card
              key={idx}
              className="m-3"
               
            >
              <Card.Header style={{backgroundColor:"rgb(216, 27, 96)" }}> Id: ${post._id}</Card.Header>
              <Card.Body >
                <Card.Title> Card Title </Card.Title>
                <Card.Text>
                  {post.text}
                </Card.Text>
                  <Button variant="danger">Create Report</Button>  
              </Card.Body>
            </Card>
          );
        })}
    </div>
  );
};

export default PostList;
