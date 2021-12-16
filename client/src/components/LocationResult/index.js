import React from 'react';
import PropTypes from 'prop-types';
import Paper from '@mui/material/Paper';
import './Style.css';

function LocationResult({ data }) {
  return (
    <div className="row mb-3 ">
      <div className="col-md-12 pl-0 ">
        <Paper variant="outlined" className="p-3">
          <span>{data.onlyName}</span>

          <br />
          <span style={{ fontSize: '0.8rem', color: '#a5a5a5' }}>
            {data.amenitySubCategory}
          </span>
          <br />
          <span style={{ fontSize: '0.8rem', color: '#a5a5a5' }}>
            {data.onlyAddress}
          </span>
          <br />
          <span style={{ fontSize: '0.8rem', color: '#a5a5a5' }}>
            {data.cuisine}
          </span>
        </Paper>
      </div>
    </div>
  );
}

LocationResult.propTypes = {
  data: PropTypes.object,
};

export default LocationResult;
