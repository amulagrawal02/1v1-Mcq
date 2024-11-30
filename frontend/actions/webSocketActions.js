export const CONNECT_WEBSOCKET = "CONNECT_WEBSOCKET";
export const DISCONNECT_WEBSOCKET = "DISCONNECT_WEBSOCKET";
export const UPDATE_PARTICIPANTS = "UPDATE_PARTICIPANTS";
export const RECEIVE_MESSAGE = "RECEIVE_MESSAGE";

// Action to initiate WebSocket connection
export const connectWebSocket = (gameId) => ({
  type: CONNECT_WEBSOCKET,
  payload: gameId, // The gameId passed to connect to the WebSocket server
});

// Action to disconnect WebSocket connection
export const disconnectWebSocket = () => ({
  type: DISCONNECT_WEBSOCKET,
});

// Action to update the participants list in the store
export const updateParticipants = (participants) => ({
  type: UPDATE_PARTICIPANTS,
  payload: participants, // The list of participants received via WebSocket
});

// Action to handle incoming WebSocket messages
export const receiveMessage = (message) => ({
  type: RECEIVE_MESSAGE,
  payload: message, // The message body received from the server
});
