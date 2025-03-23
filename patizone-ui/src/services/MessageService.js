import axiosInstance from '../util/axiosInstance';

class MessageService {
  static async getMessages(userId) {
    const response = await axiosInstance.get(`/messages/${userId}`);
    return response.data;
  }
}

export default MessageService;
