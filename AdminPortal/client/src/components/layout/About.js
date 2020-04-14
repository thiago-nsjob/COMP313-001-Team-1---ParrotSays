import React, { Component } from "react";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import Badge from "react-bootstrap/Badge";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPortrait,
  faMailBulk,
  faUserCircle,
  faCode,
  faIdCard,
} from "@fortawesome/free-solid-svg-icons";

const About = () => {
  const add = (name, email, githubUser, studentId, programName, rolName, avatar) => {
    if (avatar === "F" || avatar === "M") {
      avatar = avatar === "F" ? "img_avatar_female.png" : "img_avatar_male.png";
    }
    return { name, email, githubUser, studentId, programName, rolName, avatar };
  };

  const teamMembersList = [
    add(
      "Eduardo Santana",
      "esantan3@my.centennialcollege.ca",
      "github.com/EduardoSantanaSeverino",
      "301048660",
      "Software Engineering Technology",
      "Student",
      "M"
    ),
    add(
      "Andrea de la Isla Portilla",
      "adelaisl@my.centennialcollege.ca",
      "https://github.com/adelaisla",
      "301039987",
      "Software Engineering Technology",
      "Student",
      "F"
    ),
    add(
      "Julio Azevedo de Carvalho",
      "jazeved5@my.centennialcollege.ca",
      "https://github.com/JViniX",
      "301016383",
      "Software Engineering Technology",
      "Student",
      "M"
    ),
    add(
      "Leonardo de Oliveira",
      "ldeoliv4@my.centennialcollege.ca",
      "https://github.com/leonardoaugustu",
      "300978172",
      "Software Engineering Technology",
      "Student",
      "M"
    ),
    add(
      "Sophia Bhullar",
      "sbhull18@my.centennialcollege.ca",
      "https://github.com/SophiaBhullar",
      "301048344",
      "Software Engineering Technology",
      "Student",
      "F"
    ),
    add(
      "Thiago Silva",
      "tsilva8@my.centennialcollege.ca",
      "https://github.com/thiago-nsjob",
      "301024379",
      "Software Engineering Technology",
      "Student",
      "M"
    ),
    add(
      "Trinh Quang Trung",
      "ttrinh22@my.centennialcollege.ca",
      "https://github.com/kelvintrinh174",
      "301025717",
      "Software Engineering Technology",
      "Student",
      "M"
    ),
    add(
      "Ydelma Rangel",
      "yrangelr@my.centennialcollege.ca",
      "https://github.com/ydelmarangel",
      "300997098",
      "Software Engineering Technology",
      "Student",
      "F"
    ),
  ];

  return (
    <div
      className="container "
      style={{ marginTop: "125px", maxWidth: "1200px" }}
    >
      <div className="jumbotron">
        <img
          src="/images/ic_launcher_foreground.png"
          alt="logo"
          className="card rounded-circle"
          style={{ marginLeft: "auto", marginRight: "auto" }}
        />
        <h1
          style={{
            textAlign: "center",
            marginTop: "10px",
            fontWeight: "bold",
          }}
        >
          About
        </h1>
        <h3
          style={{
            textAlign: "center",
            marginTop: "10px",
            marginBottom: "25px",
          }}
        >
          Parrot Says Team:
        </h3>

        <div className="mt-4 d-flex flex-wrap">
          {teamMembersList.map((member, i) => {
            return (
              <Card
                key={i}
                className="card bg-light mb-3 mr-3 border-secondary"
                style={{
                  width: "22em",
                  color: "#5a5a5a",
                  float: "left",
                }}
              >
                <div className="pict" style={{ marginTop: "21px" }}>
                  <img
                    src={"/images/" + member.avatar}
                    alt="Avatar"
                    className="card rounded-circle"
                    style={{
                      marginLeft: "auto",
                      marginRight: "auto",
                      width: "136px",
                    }}
                  />
                </div>

                <Card.Body>
                  <Card.Title
                    style={{ fontWeight: "bold", textAlign: "center" }}
                  >
                    {member.name}
                  </Card.Title>
                  <Card.Text>
                    <FontAwesomeIcon className="ml-2" icon={faMailBulk} />{" "}
                    {member.email}
                    <br />
                    <FontAwesomeIcon className="ml-2" icon={faCode} />{" "}
                    {member.githubUser}
                    <br />
                    <FontAwesomeIcon className="ml-2" icon={faIdCard} />{" "}
                    {member.studentId}
                    <br />
                    <FontAwesomeIcon className="ml-2" icon={faPortrait} />{" "}
                    {member.programName}
                    <br />
                    <FontAwesomeIcon className="ml-2" icon={faPortrait} />{" "}
                    {member.rolName}
                  </Card.Text>
                </Card.Body>
              </Card>
            );
          })}
        </div>

        <div>
          <p
            style={{
              textAlign: "center",
              marginTop: "20px",
              fontWeight: "bold",
            }}
          >
            For Software Development Project 2 (COMP313)
          </p>
        </div>
      </div>
    </div>
  );
};

export default About;
