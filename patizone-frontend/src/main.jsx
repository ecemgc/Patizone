import { QueryClient } from "@tanstack/react-query";
import ReactDOM from "react-dom/client";
import "./index.css";
import "mdb-react-ui-kit/dist/css/mdb.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";
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
