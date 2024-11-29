import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Client } from "@stomp/stompjs";
import { Container, Row, Col } from "react-bootstrap";

import LiveParticipants from "./LiveParticipants";

import LiveChat from "./LiveChat";

function GameJoined() {
  let client = null;
  const { id } = useParams();
  const [participant, setParticipants] = useState({});
  console.log(id);
  var options = {
    transports: ["websocket", "jsonp"],
    // Enable origin check if desired
    // See https://github.com/sockjs/sockjs-client#available-options for more options
    origin: true,
  };

  useEffect(() => {
    client = new Client();
    console.log(client);

    const websocketUrl = `http://localhost:8080/ws-message?token=${encodeURIComponent(
      localStorage.getItem("jwtToken")
    )}`;
    try {
      // Create a new Stomp client
      client = new Client({
        brokerURL: websocketUrl,
        debug: function (str) {
          console.log("Debug statement: " + str);
        },
        //reconnectDelay: 5000,  // Automatically reconnect if the connection is lost
        onConnect: () => {
          const destination = `/play/gameid/${id}`;
          console.log("Subscribed to:", destination);
          client.subscribe(destination, (message) => {
            console.log("Received message:", message.body);

            setParticipants(JSON.parse(message.body));
          });
          client.publish({
            destination: destination, // Use the same destination as subscribed
            body: JSON.stringify({}), // Example data, modify as needed
          });
        },

        onStompError: (frame) => {
          console.error("Broker reported error: " + frame.headers["message"]);
          console.error("Additional details: " + frame.body);
        },
      });

      // If using SockJS (fallback for browsers that don't support WebSockets)
      client.webSocketFactory = function () {
        return new SockJS(websocketUrl);
      };

      // Activate the client
      client.activate();
    } catch (error) {
      console.error("Error setting up WebSocket client:", error);
    }

    return () => {
      client && client.deactivate();
    };
  }, [id]);

  useEffect(() => {
    console.log(participant);
  }, [participant]);

  return (
    <div style={{ height: "100vh" }}>
      {" "}
      {/* Full height */}
      <Container fluid>
        <Row className="h-100">
          {/* Main Section */}
          <Col xs={9} className="border-end">
            <h6>Main Section</h6>
          </Col>

          {/* Other Details */}
          <Col xs={3}>
            <Row className="h-50 border-bottom">
              <Col>
                <LiveParticipants gameId={id} />
              </Col>
            </Row>

            <Row className="h-50">
              <Col>
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
