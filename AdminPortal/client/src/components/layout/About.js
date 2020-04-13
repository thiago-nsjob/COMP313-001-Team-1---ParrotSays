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
  const add = (name, email, githubUser, studentId, programName, rolName) => {
    return { name, email, githubUser, studentId, programName, rolName };
  };

  const teamMembersList = [
    add(
      "Eduardo Santana",
      "esantan3@my.centennialcollege.ca",
      "github.com/EduardoSantanaSeverino",
      "301048660",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Andrea de la Isla Portilla",
      "adelaisl@my.centennialcollege.ca",
      "https://github.com/adelaisla",
      "301039987",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Julio Azevedo de Carvalho",
      "jazeved5@my.centennialcollege.ca",
      "https://github.com/JViniX",
      "301016383",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Leonardo de Oliveira",
      "ldeoliv4@my.centennialcollege.ca",
      "https://github.com/leonardoaugustu",
      "300978172",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Sophia Bhullar",
      "sbhull18@my.centennialcollege.ca",
      "https://github.com/SophiaBhullar",
      "301048344",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Thiago Silva",
      "tsilva8@my.centennialcollege.ca",
      "https://github.com/thiago-nsjob",
      "301024379",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Tring Quang Trung",
      "ttrinh22@my.centennialcollege.ca",
      "https://github.com/thiago-nsjob",
      "301025717",
      "Software Engineering Technology",
      "Student"
    ),
    add(
      "Ydelma Rangel",
      "yrangelr@my.centennialcollege.ca",
      "https://github.com/thiago-nsjob",
      "300997098",
      "Software Engineering Technology",
      "Student"
    ),
  ];

  return (
    <div className="container " style={{ marginTop: "125px", maxWidth: "1200px" }}>
      <div className="jumbotron">
        <img
          src="/images/ic_launcher_foreground.png"
          alt="logo"
          className="card rounded"
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
                  height: "12em",
                  color: "#5a5a5a",
                  float: "left",
                }}
              >
                <Card.Body>
                  <Card.Title style={{ fontWeight: "bold" }}>
                    {" "}
                    <FontAwesomeIcon
                      className="ml-2"
                      icon={faUserCircle}
                    />{" "}
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
