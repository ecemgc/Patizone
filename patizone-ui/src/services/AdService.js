// services/AdService.ts
import qs from 'qs';
import axiosInstance from '../util/axiosInstance';

const PAGE_SIZE = 10;

class AdService {
  static async save(request) {
    const response = await axiosInstance.post(`/ads`, request);
    return response.data;
  }

  static async getAllPaginated({ pageParam = 0 }) {
    const requestPage = {
      page: pageParam,
      pageSize: PAGE_SIZE,
      sortBy: 'createdAt',
      direction: 'DESC'
    };

    const queryString = qs.stringify(requestPage, { skipNulls: true });
    const response = await axiosInstance.get(`/ads/paginated?${queryString}`);
    console.log('resx', response);
    return response.data;
  }

  static async getAdTypes() {
    const response = await axiosInstance.get(`/ads/types`);
    return response.data;
  }
}

export default AdService;
