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
  const {
    q,
    filteredResults,
    results,
    isLoading,
    bounds,
    aggregations,
    filtering,
  } = useSelector(selectState).search;
  return (
    <div className="row m-0">
      <div className="col-md-3 py-3 m-0 text-left" style={{}}>
        <b>ElasticOSM</b>
        <br />
        Search for places in Berkeley
      </div>
      <div className="col-md-9 p-3">
        <SearchBar
          onSearch={(q, userCoords) => {
            dispatch(fetchResults(q, bounds, userCoords));
          }}
        />
      </div>
      <div
        className={
          filteredResults && (filteredResults.length > 0 || filtering)
            ? 'col-md-9'
            : 'col-md-12'
        }
        style={{ transition: '0.5s ease-in' }}
      >
        <Map
          isLoading={isLoading}
          startBounds={bounds}
          onMovement={(newBounds) => {
            dispatch(changeBounds(newBounds));
          }}
          filtering={filtering}
          q={q}
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
      <div
        className={
          filteredResults && (filteredResults.length > 0 || filtering)
            ? 'col-md-3'
            : 'col-md-3 d-none'
        }
        style={{ maxHeight: '89vh', overflowY: 'scroll' }}
      >
        {!isLoading &&
          filteredResults &&
          filteredResults.length > 0 &&
          filteredResults.map((item) => {
            return <LocationResult key={item.placeId} data={item} />;
          })}
      </div>
    </div>
  );
}

export default SearchContainer;
