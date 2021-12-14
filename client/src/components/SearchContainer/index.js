import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Reducer, {
  fetchResults,
  selectState,
  changeBounds,
  filterResults,
} from './index.slice';

import './Style.css';

import SearchBar from '../SearchBar';
import LocationResult from '../LocationResult';
import Map from '../Map';
import FacetControl from '../FacetControl';

function SearchContainer() {
  const dispatch = useDispatch();
  const { filteredResults, results, isLoading, bounds, aggregations } =
    useSelector(selectState).search;
  return (
    <div className="row m-0">
      <div
        className="col-md-3 p-3"
        style={{ maxHeight: '98vh', overflowY: 'scroll' }}
      >
        <SearchBar
          onSearch={(q) => {
            dispatch(fetchResults(q, bounds));
          }}
        />
        <div>
          {!isLoading &&
            filteredResults &&
            filteredResults.length > 0 &&
            filteredResults.map((item) => {
              return <LocationResult key={item.placeId} data={item} />;
            })}
        </div>
      </div>
      <div className="col-md-9">
        <Map
          isLoading={isLoading}
          startBounds={bounds}
          onMovement={(newBounds) => {
            dispatch(changeBounds(newBounds));
          }}
          pois={
            !filteredResults
              ? []
              : filteredResults.map((item) => {
                  return {
                    type: 'Feature',
                    geometry: {
                      type: 'Point',
                      coordinates: [item.lon, item.lat],
                    },
                    properties: {
                      ...item,
                    },
                  };
                })
          }
        >
          {!isLoading && filteredResults && (
            <FacetControl
              isLoading={isLoading}
              results={filteredResults}
              aggregations={aggregations}
              onToggle={(filterConfig) => {
                // const matches = filterBasedOnConfig(
                //   filteredResults,
                //   filterConfig
                // );
                // console.log(matches);
                dispatch(
                  filterResults({ filterConfig: filterConfig, data: results })
                );
              }}
            />
          )}
        </Map>
      </div>
    </div>
  );
}

export default SearchContainer;
