import '@fortawesome/fontawesome-free/css/all.min.css';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router';
import { ToastContainer } from 'react-toastify';
import PrivateRoute from './components/auth/PrivateRoute';
import './index.css';
import ChatApp from './pages/ChatApp';
import CreateAd from './pages/CreateAd';
import Homepage from './pages/Homepage';
import Login from './pages/Login';
import Register from './pages/Register';

const queryClient = new QueryClient();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <QueryClientProvider client={queryClient}>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <BrowserRouter>
        <Routes>
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />

          {/* Secure Routes*/}
          <Route element={<PrivateRoute />}>
            <Route index path="/" element={<Homepage />} />
            <Route path="/ad" element={<CreateAd />} />
            <Route path="/messages" element={<ChatApp />} />
          </Route>

          {/* Varsayılan yönlendirme */}
          <Route path="*" element={<Homepage />} />
        </Routes>
      </BrowserRouter>
      <ToastContainer />
    </LocalizationProvider>
  </QueryClientProvider>
);
