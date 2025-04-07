import { useEffect } from 'react';
import { Navigate, Outlet } from 'react-router';
import { SocketService } from '../../services/SocketService';
import { useAuthStore } from '../../store/useAuthStore';
import { useChatStore } from '../../store/useChatStore';
import Navbar from '../Navbar';

const PrivateRoute = () => {
  const { user } = useAuthStore();
  const appendMessage = useChatStore((state) => state.appendMessage);

  useEffect(() => {
    if (!user?.token) return;

    SocketService.connect(user.token, (msg) => {
      const targetEmail = msg.senderEmail === user.email ? msg.receiverEmail : msg.senderEmail;
      appendMessage(targetEmail, msg);
    });

    return () => {
      SocketService.disconnect();
    };
  }, [user]);

  if (!user) {
    return <Navigate to="/login" />;
  }

  return (
    <>
      <Navbar />
      <div className="pb-1 pt-16 h-screen overflow-y-auto relative">
        <Outlet />
      </div>
    </>
  );
};

export default PrivateRoute;
