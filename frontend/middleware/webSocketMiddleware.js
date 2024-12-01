import {
  CONNECT_WEBSOCKET,
  DISCONNECT_WEBSOCKET,
  updateParticipants,
  receiveMessage,
  isAllParticipantsJoined,
} from "../actions/webSocketActions";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

let client;

export const webSocketMiddleware = (store) => (next) => (action) => {
  switch (action.type) {
    case CONNECT_WEBSOCKET:
      const websocketUrl = `http://localhost:8080/ws-message?token=${encodeURIComponent(
        localStorage.getItem("jwtToken")
      )}`;

      client = new Client({
        brokerURL: websocketUrl,
        reconnectDelay: 5000,
        onConnect: () => {
          const destination = `/play/gameid/${action.payload}`;
          console.log("Subscribed to:", destination);
          client.subscribe(destination, (message) => {
            console.log("Received message:", message.body);

            store.dispatch(updateParticipants(JSON.parse(message.body)));
            store.dispatch(isAllParticipantsJoined(JSON.parse(message.body)));

            //setParticipants(JSON.parse(message.body));
          });
          client.publish({
            destination: destination, // Use the same destination as subscribed
            body: JSON.stringify({}), // Example data, modify as needed
          });
        },
        onStompError: (frame) => {
          console.error("Broker error: " + frame.headers["message"]);
        },
      });

      client.webSocketFactory = () => new SockJS(websocketUrl);
      client.activate();
      break;

    case DISCONNECT_WEBSOCKET:
      if (client) {
        client.deactivate();
      }
      break;

    default:
      break;
  }

  return next(action);
};
