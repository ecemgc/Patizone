import '@fortawesome/fontawesome-free/css/all.min.css';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router';
import { ToastContainer } from 'react-toastify';
import PrivateRoute from './components/auth/PrivateRoute';
import './index.css';
import Homepage from './pages/Homepage';
import Login from './pages/Login';
import Register from './pages/Register';

const queryClient = new QueryClient();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <QueryClientProvider client={queryClient}>
    <BrowserRouter>
      <Routes>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />

        {/* Secure Routes*/}
        <Route element={<PrivateRoute />}>
          <Route index path="/" element={<Homepage />} />
        </Route>

        {/* Varsayılan yönlendirme */}
        <Route path="*" element={<Homepage />} />
      </Routes>
    </BrowserRouter>
    <ToastContainer />
  </QueryClientProvider>
);
