import { Navigate, Outlet } from 'react-router';
import { useAuthStore } from '../../store/useAuthStore';

const PrivateRoute = () => {
  const { user } = useAuthStore();

  return user ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;
