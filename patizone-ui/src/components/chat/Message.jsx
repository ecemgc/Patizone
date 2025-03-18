function Message({ className, text, imageUrl, position }) {
  return (
    <div className={className}>
      <div className={`chat chat-${position === 'left' ? 'start' : 'end'}`}>
        <div className="chat-image avatar">
          <div className="w-10 rounded-full">
            <img src={imageUrl} />
          </div>
        </div>
        <div className="chat-bubble">{text}</div>
      </div>
    </div>
  );
}

export default Message;
