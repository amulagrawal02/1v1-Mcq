import { configureStore } from "@reduxjs/toolkit"; // Use this instead of createStore
import { webSocketReducer } from "./reducers/webSocketReducer";
import { webSocketMiddleware } from "./middleware/webSocketMiddleware";

// Combine reducers (even if you have only one, it's good practice)
const rootReducer = {
  webSocket: webSocketReducer,
};

// Configure the store
const store = configureStore({
  reducer: rootReducer, // Pass the root reducer object
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(webSocketMiddleware),
});

export default store;
