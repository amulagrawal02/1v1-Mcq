import axios from "axios";
import React, { useState } from "react";
import { Container, Row, Col, Button, Card, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const Play = () => {
  const navigate = useNavigate();

  const [gameId, setGameId] = useState("");

  const handleCreateRoom = async (e) => {
    e.preventDefault();
    const formData = {
      owner: localStorage.getItem("username"),
    };
    const response = await axios("http://localhost:8080/game/create-room", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
      data: formData,
    });
    console.log(response);

    if (response.data) {
      console.log(
        "Time to redirect the page after joining or creating the room"
      );
      const url = `/play/gameid/${response.data}`;
      console.log(url);
      navigate(url);
    }
  };

  const handleJoinRoom = async (e) => {
    e.preventDefault();
    if (gameId.trim() === "") {
      alert("Please enter a game ID.");
      return;
    }

    const formData = {
      owner: localStorage.getItem("username"),
    };

    try {
      const response = await axios(
        `http://localhost:8080/game/join-room?gameId=${gameId}`,
        {
          method: "POST",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );
      console.log(reponse);
      if (response.status == 200) {
        alert("User Joined the room with roomID: " + gameId);
        const url = `/play/gameid/${gameId}`;
        navigate(url);
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status == 409) {
          alert("User Already Joined the room with roomID: " + gameId);
          console.log("User already Joined the group");
          const url = `/play/gameid/${gameId}`;
          navigate(url);
        }
      }
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Row className="justify-content-center w-100">
        <Col md={6} lg={5}>
          <Card className="text-center">
            <Card.Body>
              <Card.Title>Play a Game</Card.Title>
              <Form onSubmit={handleJoinRoom}>
                <Form.Group className="mb-3" controlId="formGameId">
                  <Form.Label>Enter Game ID to Join a Room</Form.Label>
                  <Form.Control
                    type="text"
                    placeholder="Enter Game ID"
                    value={gameId}
                    onChange={(e) => setGameId(e.target.value)}
                  />
                </Form.Group>
                <Button
                  variant="primary"
                  className="me-2"
                  onClick={handleCreateRoom}
                >
                  Create Room
                </Button>
                <Button variant="secondary" type="submit">
                  Join Room
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default Play;
