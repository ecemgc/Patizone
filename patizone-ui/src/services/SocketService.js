import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export class SocketService {
  static stompClient = null;

  static connect(token, onMessageCallback) {
    return new Promise((resolve, reject) => {
      const socket = new SockJS(`http://localhost:8080/ws?token=${token}`);
      const client = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        debug: (str) => console.log('[WebSocket]', str),
        onConnect: () => {
          console.log('✅ WebSocket bağlı');
          client.subscribe('/user/queue/messages', (msg) => {
            const payload = JSON.parse(msg.body);
            onMessageCallback(payload);
          });
          resolve();
        },
        onStompError: (frame) => {
          console.error('❌ STOMP hatası:', frame);
          reject(frame);
        }
      });

      client.activate();
      SocketService.stompClient = client;
    });
  }

  static disconnect() {
    if (SocketService.stompClient) {
      SocketService.stompClient.deactivate();
      SocketService.stompClient = null;
    }
  }

  static sendMessage(message) {
    if (!SocketService.stompClient?.connected) return;

    SocketService.stompClient.publish({
      destination: '/app/chat',
      body: JSON.stringify(message)
    });
  }
}
