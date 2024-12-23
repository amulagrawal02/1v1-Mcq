import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Client } from "@stomp/stompjs";
import { Container, Row, Col } from "react-bootstrap";
import axios from "axios";

import { useDispatch, useSelector } from "react-redux";

import LiveParticipants from "./LiveParticipants";

import LiveChat from "./LiveChat";
import QuestionBank from "./QuestionBank";
import { updateParticipants } from "../actions/webSocketActions";

function GameJoined() {
  const { id } = useParams();

  const [useComponent, setComponent] = useState(false);

  const isAllParticipantsJoined = useSelector(
    (state) => state.webSocket.allParticipantsJoined
  );
  const dispatch = useDispatch();

  const navigate = useNavigate();

  async function GameHandler(e) {
    console.log("event handler", e.target.id);

    var status = 0;
    if (e.target.id == "leaveGmeHandler") {
      status = 0;
    } else if (e.target.id == "srtGmeHandler") {
      status = 1;
    }

    var updateURL = `http://localhost:8080/game/play/gameid/changestatus/${id}$${localStorage.getItem(
      "username"
    )}$${status}`;
    console.log("url we try to hitting: " + updateURL);
    const response = await axios(updateURL, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
    });

    if (response.status == 200) {
      if (e.target.id == "leaveGmeHandler") {
        navigate("/play");
      } else if ((e.target.id = "srtGmeHandler")) {
      }
    }
  }

  useEffect(() => {
    const blockLobby = async () => {
      if (isAllParticipantsJoined === true) {
        try {
          const response = await axios(
            `http://localhost:8080/game/blockLobby/${id}`,
            {
              method: "POST",
              headers: {
                Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
              },
            }
          );

          if (response.status === 200) {
            console.log("Lobby Blocked Successfully");
          } else {
            console.log("Error while blocking the lobby");
          }
        } catch (error) {
          console.error("Error:", error);
        }
      }
    };

    blockLobby(); // Call the async function
  }, [isAllParticipantsJoined, id]); // Now it will run when `isAllParticipantsJoined` or `id` changes

  return (
    <div style={{ height: "100vh" }}>
      {" "}
      <Container fluid>
        <Row className="h-100">
          {/* Main Section */}
          <Col xs={9} className="border-end px-4 py-3">
            <h5 className="mb-4">Lobby Joined: {id}</h5>

            {/* Action Buttons */}
            <Row className="mb-4">
              <Col>
                <button
                  className="btn btn-primary w-50"
                  id="srtGmeHandler"
                  onClick={GameHandler}
                >
                  Start Game
                </button>
              </Col>
              <Col>
                <button
                  className="btn btn-danger w-50"
                  id="leaveGmeHandler"
                  onClick={GameHandler}
                >
                  Leave Lobby
                </button>
              </Col>
            </Row>

            <Row>
              <Col>
                {isAllParticipantsJoined === false ? (
                  <p>All participants have not started the game yet.......</p>
                ) : (
                  <>
                    <p>All participants are ready to start the game!</p>
                    {useComponent === true ? <QuestionBank /> : null}
                  </>
                )}
              </Col>
            </Row>

            {/* Question Bank */}
          </Col>

          {/* Other Details */}
          <Col xs={3} className="px-3 py-3">
            {/* Live Participants */}
            <Row className="h-50 border-bottom mb-3">
              <Col>
                <h6>Live Participants</h6>
                <LiveParticipants gameId={id} />
              </Col>
            </Row>

            {/* Live Chat */}
            <Row className="h-50 overflow-auto">
              <Col>
                <h6>Live Chat</h6>
                <LiveChat gameId={id} />
              </Col>
            </Row>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

export default GameJoined;
