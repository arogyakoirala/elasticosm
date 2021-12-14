import { configureStore } from '@reduxjs/toolkit';
import searchReducer from '../components/SearchContainer/index.slice';

export const store = configureStore({
  reducer: {
    search: searchReducer,
  },
});
