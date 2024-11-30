import React, { useEffect, useState } from "react";

import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Client } from "@stomp/stompjs";
import {
  connectWebSocket,
  disconnectWebSocket,
} from "../actions/webSocketActions";
function LiveParticipants(props) {
  const participants = useSelector((state) => state.webSocket.participants);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(connectWebSocket(props.gameId)); // connect to websocket when component mounts

    return () => {
      dispatch(disconnectWebSocket);
    };
  }, [dispatch, props.gameId]);

  return (
    <div>
      Live Participants for gameID: {props.gameId}
      {participants.length > 0 ? (
        <ol>
          {participants.map((item, index) => (
            <li key={index}>
              {item.participant}
              <span
                style={{
                  width: "10px",
                  height: "10px",
                  borderRadius: "50%",
                  backgroundColor: item.status == 1 ? "#66BB6A" : "#E57373",
                  display: "inline-block",
                  marginLeft: "5px",
                }}
              ></span>
            </li>
          ))}
        </ol>
      ) : (
        <p>No participants connected</p> // Optional placeholder for empty state
      )}
    </div>
  );
}

export default LiveParticipants;
