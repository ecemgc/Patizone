// 📁 store/useSocketStore.js
import { create } from 'zustand';
import { SocketService } from '../services/SocketService';
import axiosInstance from '../util/axiosInstance';

export const useSocketStore = create((set, get) => ({
  messages: {}, // { userId: Message[] }
  isConnected: false,

  connect: async () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user?.token) return;

    await SocketService.connect(user.token, (msg) => {
      const email = msg.senderEmail === user.email ? msg.receiverEmail : msg.senderEmail;
      set((state) => ({
        messages: {
          ...state.messages,
          [email]: [...(state.messages[email] || []), msg]
        }
      }));
    });

    set({ isConnected: true });
  },

  disconnect: () => {
    SocketService.disconnect();
    set({ isConnected: false });
  },

  loadMessages: async (targetUserId) => {
    const res = await axiosInstance.get(`/messages/${targetUserId}`);
    set((state) => ({
      messages: {
        ...state.messages,
        [targetUserId]: res.data
      }
    }));
  },

  sendMessage: (message) => {
    SocketService.sendMessage(message);
    const targetMail = message.receiverEmail;
    set((state) => ({
      messages: {
        ...state.messages,
        [targetMail]: [...(state.messages[targetMail] || []), message]
      }
    }));
  },

  getMessagesWith: (targetEmail) => {
    return get().messages[targetEmail] || [];
  }
}));
