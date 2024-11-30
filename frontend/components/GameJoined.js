import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Client } from "@stomp/stompjs";
import { Container, Row, Col } from "react-bootstrap";

import LiveParticipants from "./LiveParticipants";

import LiveChat from "./LiveChat";

function GameJoined() {
  const { id } = useParams();

  async function startGameHandler(e) {
    console.log("Start Game Handler called");

    const response = await axios(
      `http://localhost:8080/play/game/changestatus/${id}#${localStorage.getItem(
        "username"
      )}#1`,
      {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      }
    );

    console.log(response);
    // e.preventDefault();
  }
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
                  onClick={startGameHandler}
                >
                  Start Game
                </button>
              </Col>
              <Col>
                <button className="btn btn-danger w-50">Leave Lobby</button>
              </Col>
            </Row>

            {/* Question Bank */}
            <Row>
              <Col>
                <h6>Question Bank</h6>
              </Col>
            </Row>
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
