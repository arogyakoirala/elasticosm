import { createSlice } from '@reduxjs/toolkit';
import { api } from './index.api';
import parseJson from 'parse-json';
import { filterBasedOnConfig, preprocess } from './utils';

const slice = createSlice({
  name: 'search',
  initialState: {
    results: [],
    filteredResults: [],
    aggregations: [],
    q: null,
    bounds: {
      bottom: 37.83312415698251,
      left: -122.2307519314997,
      right: -122.32405776353889,
      top: 37.917525240169,
    },
    isLoading: false,
    error: false,
  },
  reducers: {
    startLoading: (state) => {
      console.log('Starting to load bruv');
      state.isLoading = true;
    },
    hasError: (state, action) => {
      state.error = action.payload;
      state.isLoading = false;
    },
    searchSuccess: (state, action) => {
      state.results = action.payload.results;
      state.filteredResults = action.payload.results;
      state.aggregations = action.payload.aggregations;
      state.isLoading = false;
    },
    changeBounds: (state, action) => {
      state.bounds = action.payload;
    },
    updateResults: (state, action) => {
      console.log('Payload', action);
      state.filteredResults = action.payload;
      state.isLoading = false;
    },
  },
});

export default slice.reducer;

// Actions
export const {
  searchSuccess,
  startLoading,
  hasError,
  changeBounds,
  updateResults,
} = slice.actions;

export const fetchResults = (q, bounds, userCoords) => async dispatch => { //eslint-disable-line
  dispatch(startLoading());
  try {
    // await api
    //   .get(`/search2`, {
    //     body: JSON.stringify({
    //       q: q,
    //       bounds: {
    //         bottom: 37.814657853741494,
    //         left: -122.18568206641964,
    //         right: -122.28819613596696,
    //         top: 37.907406360720756,
    //       },
    //     }),
    //   })
    await api
      .request({
        url: `/search2`,
        params: {
          q: q,
          ...bounds,
        },
      })
      .then((response) =>
        dispatch(searchSuccess(preprocess(response.data, userCoords)))
      );
  } catch (e) {
    dispatch(hasError(e.message));
  }
};

export const filterResults =
  ({ data, filterConfig }) => async dispatch => { //eslint-disable-line
    dispatch(startLoading());
    const updatedResults = filterBasedOnConfig(data, filterConfig);
    console.log('Here in filterResults', updatedResults);

    dispatch(updateResults(updatedResults));
  };

export const selectState = (state) => state;
