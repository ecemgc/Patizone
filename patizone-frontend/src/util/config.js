class Config {
  getApiUrl() {
    return import.meta.env.VITE_API_URL || "http://localhost:8080";
  }
}

export default new Config();
