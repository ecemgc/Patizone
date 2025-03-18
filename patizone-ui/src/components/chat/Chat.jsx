import { Input } from '@mui/material';
import { Client } from '@stomp/stompjs';
import { useEffect, useRef, useState } from 'react';
import { MessageList } from 'react-chat-elements';
import 'react-chat-elements/dist/main.css';
import SockJS from 'sockjs-client';

export default function Chat() {
  const [messages, setMessages] = useState([
    { position: 'left', type: 'text', text: 'Merhaba!', date: new Date() }
  ]);
  const stompClientRef = useRef(null); // TypeScript olmayan doğru kullanım

  useEffect(() => {
    connectWebSocket();

    return () => {
      disconnectWebSocket();
    };
  }, []);

  function connectWebSocket() {
    try {
      const token =
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiZXJkYW5uLmFrYnVsdXRAZ21haWwuY29tIiwiaWF0IjoxNzQxODIwNDc4LCJleHAiOjE3NDE4MjEwNzh9.JF_TRGNgAys1038uxOjGbwdisJie778OzmwQ94WIm6Y';
      const socket = new SockJS(`http://localhost:8080/ws?token=${token}`);
      const stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000, // Bağlantı koparsa tekrar bağlanır
        debug: (str) => console.log(str)
      });

      stompClient.onConnect = (frame) => {
        console.log('✅ WebSocket Bağlandı:', frame);

        // Mesajları dinle
        stompClient.subscribe('/user/queue/messages', (message) => {
          const receivedMessage = JSON.parse(message.body);
          console.log('📩 Yeni mesaj:', receivedMessage);

          const myEmailFromLocalStor = 'berdann.akbulut@gmail.com';
          if (myEmailFromLocalStor === receivedMessage.senderEmail) {
            return;
          }
          setMessages((prevMessages) => [
            ...prevMessages,
            {
              position: 'left',
              type: 'text',
              text: receivedMessage.content,
              date: new Date()
            }
          ]);
        });
      };

      stompClient.activate();
      stompClientRef.current = stompClient;
    } catch (error) {
      console.error('WebSocket Bağlantı Hatası:', error);
    }
  }

  function disconnectWebSocket() {
    if (stompClientRef.current) {
      stompClientRef.current.deactivate();
      console.log('❌ WebSocket Bağlantısı Kesildi');
    }
  }

  function sendMessage(text) {
    if (!text.trim() || !stompClientRef.current || !stompClientRef.current.connected) {
      console.error('WebSocket bağlı değil, mesaj gönderilemedi.');
      return;
    }

    const message = {
      senderEmail: 'senin-email@example.com',
      receiverEmail: 'berdann.akbulut@gmail.com',
      content: text,
      timestamp: new Date()
    };

    stompClientRef.current.publish({
      destination: '/app/chat',
      body: JSON.stringify(message)
    });

    setMessages((prevMessages) => [
      ...prevMessages,
      { position: 'right', type: 'text', text, date: new Date() }
    ]);
  }

  return (
    <div className="flex flex-col w-full max-w-md mx-auto h-[500px] border rounded-lg shadow-lg p-4 bg-white">
      {/* 📜 Mesaj Listesi */}
      <div className="flex-1 overflow-y-auto">
        <MessageList
          className="message-list"
          lockable={true}
          toBottomHeight={'100%'}
          dataSource={messages}
        />
      </div>

      {/* 💬 Mesaj Giriş Alanı */}
      <div className="border-t p-2 flex">
        <Input
          placeholder="Mesajınızı yazın..."
          multiline={false}
          onKeyPress={(e) => {
            if (e.key === 'Enter') {
              sendMessage(e.target.value);
              e.target.value = ''; // Input'u temizle
            }
          }}
          rightButtons={
            <button
              className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-lg"
              onClick={() => {
                const inputField = document.querySelector('input');
                sendMessage(inputField.value);
                inputField.value = ''; // Input'u temizle
              }}>
              Gönder
            </button>
          }
        />
      </div>
    </div>
  );
}
