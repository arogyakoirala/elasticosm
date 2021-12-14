import React, { useState } from 'react';
import './Style.css';

import Paper from '@mui/material/Paper';
import InputBase from '@mui/material/InputBase';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import InfoIcon from '@mui/icons-material/Info';
import { geolocated } from 'react-geolocated';
import PropTypes from 'prop-types';

function SearchBar({ onSearch, coords }) {
  const [q, setQ] = useState(null);
  return (
    <div>
      <Paper
        variant="outlined"
        // component="form"
        sx={{ p: '2px 4px', display: 'flex', alignItems: 'center' }}
      >
        <InputBase
          sx={{ ml: 1, flex: 1 }}
          placeholder="Search for a place"
          inputProps={{ 'aria-label': 'search ' }}
          onChange={(e) => {
            setQ(e.target.value);
          }}
        />
        <IconButton
          disabled={q == null || coords == null}
          onClick={() => {
            onSearch(q, coords);
          }}
          sx={{ p: '10px' }}
          aria-label="search"
        >
          <SearchIcon />
        </IconButton>
        <Divider sx={{ height: 28, m: 0.5 }} orientation="vertical" />
        <IconButton color="primary" sx={{ p: '10px' }} aria-label="info">
          <InfoIcon />
        </IconButton>
      </Paper>
    </div>
  );
}

SearchBar.propTypes = {
  onSearch: PropTypes.func,
  coords: PropTypes.object,
};

export default geolocated({
  positionOptions: {
    enableHighAccuracy: false,
  },
  userDecisionTimeout: 5000,
})(SearchBar);
