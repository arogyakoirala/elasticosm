import React from 'react';
import './Style.css';

class SearchView extends React.Component {

    constructor(props) {
        super(props);
        this.onResult = this.onResult.bind(this);
        this.state = {
            // statusMessage: 'Search for a place',
            // currentMarkers: [],
            // center: null,
        };
    }

    componentDidMount() {
        this.addMap();
    }
    
    addMap() {
        const map = new mapboxgl.Map({
            container: this.node,
            style: 'mapbox://styles/mapbox/streets',
            center: [-122.4194, 37.7749],
            zoom: 12,
            attributionControl: false,
          });

          this.map = map
      
          
    }

    render() {
        return(
        <div
            ref={node => this.node = node} //eslint-disable-line
            style={{
            maxHeight: '100%',
            minHeight: '100%',
            borderRadius: '10px',
        }}
      />)

    }



}

export default SearchView;
