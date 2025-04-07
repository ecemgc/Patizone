import axiosInstance from '../util/axiosInstance';

class AuthService {
  static async login(request) {
    const response = await axiosInstance.post(`/auth/login`, request);
    return response.data; // { user: {...}, token: "..." }
  }

  static async register(userData) {
    const response = await axiosInstance.post(`/auth/register`, userData);
    return response.data; // { message: "Kullanıcı oluşturuldu" }
  }
}

export default AuthService;
