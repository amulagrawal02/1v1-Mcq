import {
  CONNECT_WEBSOCKET,
  DISCONNECT_WEBSOCKET,
  UPDATE_PARTICIPANTS,
  RECEIVE_MESSAGE,
} from "../actions/webSocketActions";

const initialState = {
  isConnected: false,
  participants: [],
  messages: [],
};

export const webSocketReducer = (state = initialState, action) => {
  switch (action.type) {
    case CONNECT_WEBSOCKET:
      return { ...state, isConnected: true };
    case DISCONNECT_WEBSOCKET:
      return { ...state, isConnected: false, participants: [], messages: [] };
    case UPDATE_PARTICIPANTS:
      return { ...state, participants: action.payload };
    case RECEIVE_MESSAGE:
      return { ...state, messages: [...state.messages, action.payload] };
    default:
      return state;
  }
};
