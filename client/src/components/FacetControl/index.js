import React, { useState } from 'react';
import './Style.css';
import Chip from '@mui/material/Chip';
import * as d3 from 'd3-array';
import f from 'compose-function';
import { statement } from '@babel/template';

const labels = {
  openingHours: 'Is the place open?',
  wheelChair: 'Wheelchair access?',
  amenitySubCategory: 'Category',
  distance: 'How far is it?',
  internetAccess: 'Internet access?',
  paymentOptions: 'Payment options',
  driveThrough: 'Drive through?',
  delivery: 'Delivery?',
  diet: 'Diet',
  cuisine: 'Cuisine',
  seating: 'Seating',
};

function FacetControl({ q, results, aggregations, onToggle, isLoading }) {
  const k = Array.from(
    d3.group(aggregations, (d) => d.facet),
    ([key, value]) => ({ key, value })
  );

  const initialState = {};
  k.forEach((group) => {
    // console.log(group);
    group.value.forEach((item) => {
      if (!initialState[group.key]) {
        initialState[group.key] = {};
      }
      initialState[group.key][item.label] = false;
    });
  });

  const [toggleStates, setToggleStates] = useState(initialState);
  const [isFiltered, setFiltered] = useState(false);

  return (
    <div className="">
      {!isLoading && (
        <div>
          <h4>{results.length} results</h4>

          {k.map((group, i) => {
            return (
              <div key={i}>
                <span
                  className="text-muted"
                  style={{
                    fontWeight: 600,
                    fontSize: '0.7rem',
                    textTransform: 'uppercase',
                  }}
                >
                  {labels[group.key]}
                </span>
                <br />
                <div>
                  {group.value.map((item, i) => {
                    return (
                      <Chip
                        key={i}
                        className="m-1"
                        size="small"
                        color={
                          toggleStates[group.key][item.label]
                            ? 'primary'
                            : 'default'
                        }
                        onClick={() => {
                          const woo = {
                            ...toggleStates,
                            [group.key]: {
                              ...toggleStates[group.key],
                              [item.label]:
                                !toggleStates[group.key][item.label],
                            },
                          };
                          setToggleStates(woo);

                          const filterConfig = {};
                          Object.keys(woo).forEach((group) => {
                            Object.keys(woo[group]).forEach((item) => {
                              if (woo[group][item]) {
                                if (!filterConfig[group]) {
                                  filterConfig[group] = [];
                                }
                                filterConfig[group].push(item);
                              }
                            });
                          });

                          if (Object.keys(filterConfig).length > 0) {
                            setFiltered(true);
                          } else {
                            setFiltered(false);
                          }
                          onToggle(filterConfig);
                        }}
                        label={
                          isFiltered
                            ? item.label
                            : item.label + ' (' + item.value + ')'
                        }
                      />
                    );
                  })}
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}

export default FacetControl;
