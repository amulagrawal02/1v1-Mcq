import {
  CONNECT_WEBSOCKET,
  DISCONNECT_WEBSOCKET,
  UPDATE_PARTICIPANTS,
  RECEIVE_MESSAGE,
  CHECK_ALL_PARTICIPANTS_JOINED,
} from "../actions/webSocketActions";

const initialState = {
  isConnected: false,
  participants: [],
  messages: [],
  allParticipantsJoined: false,
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
    case CHECK_ALL_PARTICIPANTS_JOINED:
      const allJoined = action.payload.every(
        (participant) => participant.status == "1"
      );
      return { ...state, allParticipantsJoined: allJoined };
    default:
      return state;
  }
};
