import React, { useState, useEffect } from "react";
import { PostAPI } from "./api-post";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import Badge from "react-bootstrap/Badge";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faThumbsUp,
  faThumbsDown,
  faTheaterMasks,
  faLink,
} from "@fortawesome/free-solid-svg-icons";

export const PostList = () => {
  const [data, setData] = useState([]);
  const [refresh, setRefresh] = useState(true);

  const fetch = async () => {
    if (refresh) {
      setRefresh(false);
      let res = await PostAPI.getAllPosts();
      console.log(res);
      setData(res);
    }
  };

  useEffect(() => {
    fetch();
  }, [data]);

  const buildHazardusData = (mlAnalyse) => {
    if (mlAnalyse)
      return (
        <div className="d-inline-flex ml-5">
          <FontAwesomeIcon className="ml-2" icon={faThumbsUp} color="green" />{" "}
          {Math.round(mlAnalyse.classification[1].score * 100)} %
          <FontAwesomeIcon className="ml-2" icon={faThumbsDown} color="red" />
          {Math.round(mlAnalyse.classification[0].score * 100)} %
        </div>
      );
    else
      return (
        <Badge className="ml-5" variant="secondary">
          No data
        </Badge>
      );
  };
  const buildSentiment = (mlAnalyse) => {
    if (mlAnalyse) {
      return (
        <div>
          <FontAwesomeIcon
            className="ml-2 mr-1"
            icon={faTheaterMasks}
            color="green"
          />
          {mlAnalyse.sentiment.sentiment.value}
        </div>
      );
    } else
      return (
        <Badge className="ml-5" variant="secondary">
          No data
        </Badge>
      );
  };

  return (
    <div
      className="mt-5 d-flex container-fluid flex-wrap"
      style={{ width: "100em", marginTop: "10em" }}
    >
      {data.map((post, idx) => {
        return (
          <Card
            key={idx}
            className="m-3"
            style={{ width: "30em", height: "25em" }}
          >
            <Card.Header style={{ backgroundColor: "#511845", color: "white" }}>
              Id: {post._id}
              <div className="d-inline-flex ml-5">
                <a href={post.postUrl}>
                  <FontAwesomeIcon
                    className="ml-2"
                    icon={faLink}
                    color="#05C3DD"
                  />
                </a>
              </div>
            </Card.Header>
            <Card.Body>
              <Card.Title> Tweet Analyse </Card.Title>
              <div className="d-inline-flex mb-3">
                {buildSentiment(post.mlAnalyse)}
                {buildHazardusData(post.mlAnalyse)}
              </div>
              <Card.Title> Tweet Content </Card.Title>
              <Card.Text>
                {post.text}
                <br />
                {post.hashTags.map((tag, idx_tag) => {
                  return (
                    <Badge
                      className="ml-1"
                      key={`${idx_tag}_${idx} `}
                      variant="dark"
                    >
                      #{tag}
                    </Badge>
                  );
                })}
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
