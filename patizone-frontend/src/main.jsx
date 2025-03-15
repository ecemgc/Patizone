import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router";
import "./index.css";
import Homepage from "./pages/Homepage";
import Register from "./pages/Register";
import Login from "./pages/Login";
import PrivateRoute from "./components/auth/PrivateRoute";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
import { ToastContainer } from "react-toastify";
const root = document.getElementById("root");

const queryClient = new QueryClient();

ReactDOM.createRoot(root).render(
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
