import axios from 'axios';

export const api = axios.create({
  // baseURL: 'http://localhost:8080',
  baseURL: 'https://i202-api.arogyakoirala.com',
  headers: {
    'Content-Type': 'application/json',
  },
});
