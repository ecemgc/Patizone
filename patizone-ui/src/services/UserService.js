import axiosInstance from '../util/axiosInstance';

class UserService {
  static async getAll() {
    const response = await axiosInstance.get(`/users`);
    return response.data;
  }
}

export default UserService;
