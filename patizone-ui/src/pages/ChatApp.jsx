import SendIcon from '@mui/icons-material/Send';
import { Avatar, Box, IconButton, TextField, Typography } from '@mui/material';
import { useEffect, useRef, useState } from 'react';
import CharUserList from '../components/chat/ChatUserList';
import MessageService from '../services/MessageService';
import { SocketService } from '../services/SocketService';
import { useChatStore } from '../store/useChatStore';
import dayjs from '../util/dayjs';

export default function ChatApp() {
  const [selectedUser, setSelectedUser] = useState(null);
  const [input, setInput] = useState('');
  const [isTyping, setIsTyping] = useState(false);
  const appendMessage = useChatStore((state) => state.appendMessage);
  const setMessages = useChatStore((state) => state.setMessages);
  const getMessagesWith = useChatStore((state) => state.getMessagesWith);
  const messages = useChatStore((state) => state.messages);
  const [localMessages, setMessagesLocal] = useState([]);
  const typingTimeoutRef = useRef(null);
  const typingDisplayTimeoutRef = useRef(null);
  const bottomRef = useRef(null);

  useEffect(() => {
    const fetchMessages = async () => {
      if (!selectedUser?.email) return;
      try {
        const dbMessages = await MessageService.getMessages(selectedUser.id);
        setMessages(selectedUser.email, dbMessages);
        setMessagesLocal(dbMessages);
      } catch (err) {
        console.error('Mesajlar çekilemedi:', err);
      }
    };
    fetchMessages();
  }, [selectedUser?.email, setMessages]);

  useEffect(() => {
    if (selectedUser?.email) {
      setMessagesLocal(getMessagesWith(selectedUser.email));
    }
  }, [messages, selectedUser?.email]);

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem('user'));
    SocketService.connect(user.token, (msg) => {
      if (msg.messageType === 'TYPING' && msg.senderEmail === selectedUser?.email) {
        setIsTyping(true);
        clearTimeout(typingDisplayTimeoutRef.current);
        typingDisplayTimeoutRef.current = setTimeout(() => setIsTyping(false), 3000);
      } else if (msg.messageType === 'MESSAGE') {
        const key = msg.senderEmail === user.email ? msg.receiverEmail : msg.senderEmail;
        appendMessage(key, msg);
      }
    });

    return () => SocketService.disconnect();
  }, [selectedUser?.email]);

  // 🧲 SCROLL TO BOTTOM WHEN MESSAGES OR USER CHANGES
  useEffect(() => {
    if (bottomRef.current) {
      bottomRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [localMessages, selectedUser?.id]);

  const handleSend = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!input.trim() || !user || !selectedUser) return;

    const message = {
      senderEmail: user.email,
      receiverEmail: selectedUser.email,
      content: input,
      messageType: 'MESSAGE',
      timestamp: new Date()
    };

    SocketService.sendMessage(message);
    appendMessage(selectedUser.email, message);
    setInput('');
  };

  const handleTyping = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user || !selectedUser) return;

    if (typingTimeoutRef.current) return;

    const typingMsg = {
      senderEmail: user.email,
      receiverEmail: selectedUser.email,
      content: '',
      messageType: 'TYPING',
      timestamp: new Date()
    };

    SocketService.sendMessage(typingMsg);

    typingTimeoutRef.current = setTimeout(() => {
      typingTimeoutRef.current = null;
    }, 3000);
  };

  const user = JSON.parse(localStorage.getItem('user'));

  return (
    <Box sx={{ display: 'flex', height: '100vh' }}>
      <CharUserList onSelectUser={setSelectedUser} selectedUserId={selectedUser?.id} />

      <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
        <Box
          sx={{
            p: 2,
            borderBottom: '1px solid #ddd',
            bgcolor: 'background.paper',
            position: 'sticky',
            top: 0,
            zIndex: 2
          }}>
          <Typography variant="h6">
            {selectedUser ? selectedUser.firstName : 'Select a User'}
          </Typography>
        </Box>

        <Box
          sx={{
            flex: 1,
            overflowY: 'auto',
            p: 2,
            display: 'flex',
            flexDirection: 'column'
          }}>
          {localMessages.map((msg, idx) => {
            const isMe = msg.senderEmail === user.email;
            const name = isMe ? user.firstName : selectedUser?.firstName;

            return (
              <Box
                key={idx}
                sx={{
                  display: 'flex',
                  justifyContent: isMe ? 'flex-end' : 'flex-start',
                  mb: 2,
                  animation: 'fadeIn 0.5s ease-in'
                }}>
                {!isMe && (
                  <Box
                    sx={{
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      mr: 1
                    }}>
                    <Avatar src={selectedUser.imageUrl || undefined} sx={{ width: 32, height: 32 }}>
                      {!selectedUser.imageUrl && selectedUser.firstName[0]}
                    </Avatar>
                    <Typography variant="caption" sx={{ mt: 0.5 }}>
                      {name}
                    </Typography>
                  </Box>
                )}

                <Box
                  sx={{
                    bgcolor: isMe ? 'blue' : 'grey.300',
                    color: isMe ? 'white' : 'black',
                    px: 2,
                    py: 1,
                    borderRadius: 3,
                    maxWidth: '60%'
                  }}>
                  <Typography>{msg.content}</Typography>
                  <Typography
                    variant="caption"
                    sx={{ display: 'block', textAlign: 'right', mt: 0.5 }}>
                    {dayjs(msg.timestamp).fromNow()}
                  </Typography>
                </Box>

                {isMe && (
                  <Box
                    sx={{
                      display: 'flex',
                      flexDirection: 'column',
                      alignItems: 'center',
                      ml: 1
                    }}>
                    <Avatar src={user.imageUrl || undefined} sx={{ width: 32, height: 32 }}>
                      {!user.imageUrl && user.firstName[0]}
                    </Avatar>
                    <Typography variant="caption" sx={{ mt: 0.5 }}>
                      {name}
                    </Typography>
                  </Box>
                )}
              </Box>
            );
          })}

          {isTyping && (
            <Box sx={{ mt: 1, ml: 1, display: 'flex', alignItems: 'center', gap: 1 }}>
              <Box
                sx={{
                  width: 8,
                  height: 8,
                  borderRadius: '50%',
                  bgcolor: 'text.secondary',
                  animation: 'pulse 1.2s infinite ease-in-out'
                }}
              />
              <Typography variant="body2" color="text.secondary">
                Yazıyor...
              </Typography>
            </Box>
          )}

          {/* Scroll hedefi */}
          <div ref={bottomRef} />
        </Box>

        {selectedUser && (
          <Box
            sx={{
              display: 'flex',
              p: 2,
              borderTop: '1px solid #ddd',
              bgcolor: 'background.paper',
              position: 'sticky',
              bottom: 0,
              zIndex: 1
            }}>
            <TextField
              fullWidth
              variant="outlined"
              size="small"
              placeholder="Mesaj yaz..."
              value={input}
              onChange={(e) => {
                setInput(e.target.value);
                handleTyping();
              }}
              onKeyDown={(e) => {
                if (e.key === 'Enter') handleSend();
              }}
            />
            <IconButton onClick={handleSend} color="primary">
              <SendIcon />
            </IconButton>
          </Box>
        )}
      </Box>
    </Box>
  );
}
