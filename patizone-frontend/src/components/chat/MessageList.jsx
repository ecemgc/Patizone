import React from "react";
import Message from "./Message";

function MessageList({ messageList }) {
  if (!messageList || !messageList.length) {
    return <></>;
  }
  return (
    <div>
      {messageList.map((message) => (
        <Message
          className={message.className || ""}
          imageUrl={message.imageUrl}
          text={message.text}
          position={message.position}
        />
      ))}
    </div>
  );
}

export default MessageList;
