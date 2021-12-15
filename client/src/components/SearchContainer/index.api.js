import axios from 'axios';

export const api = axios.create({
  // baseURL: 'http://localhost:8080',
  baseURL: 'http://api:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});
