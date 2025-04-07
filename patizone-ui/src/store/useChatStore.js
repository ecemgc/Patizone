import { create } from 'zustand';

export const useChatStore = create((set, get) => ({
  messages: {},
  lastMessagesByUser: {},

  setMessages: (userEmail, newMessages) => {
    set((state) => ({
      messages: { ...state.messages, [userEmail]: newMessages }
    }));
  },

  appendMessage: (userEmail, msg) => {
    set((state) => ({
      messages: {
        ...state.messages,
        [userEmail]: [...(state.messages[userEmail] || []), msg]
      }
    }));
  },
  getMessagesWith: (userEmail) => {
    if (!userEmail) return [];
    return get().messages[userEmail] || [];
  },
  putLastMessage: (email, lastMessage) => {
    set((state) => ({ ...state.lastMessagesByUser, [email]: lastMessage }));
  }
}));
