import axios from "axios";
import Config from "../util/config";

const API_URL = Config.getApiUrl();

class AuthService {
  async login(request) {
    const response = await axios.post(`${API_URL}/auth/login`, request);
    return response.data; // { user: {...}, token: "..." }
  }

  async register(userData) {
    const response = await axios.post(`${API_URL}/auth/register`, userData);
    return response.data; // { message: "Kullanıcı oluşturuldu" }
  }
}

export default new AuthService();
