import { useEffect, useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

function App() {
  const [count, setCount] = useState(0);

  useEffect(() => {
    connectWebSocket();
  }, []);

  function connectWebSocket() {
    const socket = new SockJS("http://localhost:8080/ws");
    const token =
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlY2VAZS5jb20iLCJpYXQiOjE3NDEzNzY0MTMsImV4cCI6MTc0MTM3NzAxM30.K3__BKlmbjdUFn8qWnDV7cQBfJ1QXtCSbIW-g7HQC1o";
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      connectHeaders: {
        Authorization: "Bearer " + token,
      },
    });

    stompClient.onConnect = (frame) => {
      console.log("Connected:", frame);

      stompClient.subscribe("/user/queue/messages", (message) => {
        console.log("New message:", JSON.parse(message.body));
      });

      stompClient.publish({
        destination: "/app/chat",
        headers: { Authorization: "Bearer " + token },
        body: JSON.stringify({
          senderEmail: "ece@e.com",
          receiverEmail: "ece@e.com",
          content: "Merhaba!",
          timestamp: new Date()
        }),
      });
    };

    stompClient.activate();
  }

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  );
}

export default App;
