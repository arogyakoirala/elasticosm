import React from 'react';
import './Style.css';

import mapboxgl from 'mapbox-gl/dist/mapbox-gl';
import 'mapbox-gl/dist/mapbox-gl.css';
import { geolocated } from 'react-geolocated';
import { update } from 'lodash';

class MapView extends React.Component {
  constructor(props) {
    super(props);
    this.updateBounds = this.updateBounds.bind(this);
    this.state = {
      // statusMessage: 'Search for a place',
      // currentMarkers: [],
      // center: null,
    };
  }

  componentDidMount() {
    this.addMap();
  }
  componentDidUpdate() {
    // if (this.props.coords) {
    //   console.log('Found', this.props.coords);
    //   this.map.flyTo({
    //     center: [this.props.coords.longitude, this.props.coords.latitude],
    //   });
    // }
    if (!this.props.isLoading) {
      if (this.map.getLayer('pois')) {
        this.map.removeLayer('pois');
      }

      if (this.map.getLayer('poi-labels')) {
        this.map.removeLayer('poi-labels');
      }

      if (this.map.getSource('pois')) {
        this.map.removeSource('pois');
      }

      if (this.props.pois.length > 0) {
        const data = {
          type: 'geojson',
          data: {
            type: 'FeatureCollection',
            features: this.props.pois,
          },
        };

        this.map.addSource('pois', data);
        this.map.addLayer({
          id: 'pois',
          type: 'circle',
          source: 'pois',
          paint: {
            'circle-radius': 8,
            'circle-stroke-width': 2,
            'circle-color': 'red',
            'circle-stroke-color': 'white',
          },
        });

        this.map.addLayer({
          id: 'poi-labels',
          type: 'symbol',
          source: 'pois',
          layout: {
            'text-field': ['get', 'onlyName'],
            'text-variable-anchor': ['top', 'bottom', 'left', 'right'],
            'text-radial-offset': 0.9,
            'text-justify': 'auto',
            'text-size': 13,
            'text-font': [
              'literal',
              ['DIN Offc Pro Italic', 'Arial Unicode MS Regular'],
            ],
            // 'icon-image': ['concat', ['get', 'icon'], '-15'],
          },
        });
      }
    }
  }

  updateBounds() {
    const bounds = this.map.getBounds();

    const boundsObject = {
      top: bounds._ne.lat,
      bottom: bounds._sw.lat,
      left: bounds._sw.lng,
      right: bounds._ne.lng,
    };

    // console.log('A moveend event occurred.');
    this.props.onMovement(boundsObject);
  }

  addMap() {
    mapboxgl.accessToken =
      'pk.eyJ1IjoiYXJrb2Jsb2ciLCJhIjoiY2pmZ2RsNGpqNDE1OTJxazdrNzVxNnl2ZSJ9.Qj1ryjt2_OWUmlTKlcEmtA';
    const map = new mapboxgl.Map({
      container: this.node,
      style: 'mapbox://styles/arkoblog/ckx2oiz5e1kih14oinzz1ic45',
      // center: [-122.4194, 37.7749],
      zoom: 12,
      // attributionControl: false,
    });
    const { startBounds } = this.props;
    map.fitBounds([
      [startBounds.right, startBounds.bottom],
      [startBounds.left, startBounds.top],
    ]);

    this.map = map;
    const { updateBounds } = this;

    this.map.on('zoomend', () => {
      updateBounds();
    });
    this.map.on('dragend', () => {
      updateBounds();
    });
  }

  render() {
    return (
      <div
        ref={node => this.node = node} //eslint-disable-line
        style={{
          maxHeight: '100vh',
          minHeight: '100vh',
        }}
      >
        <div
          style={{
            position: 'absolute',
            top: '10px',
            bottom: '100px',
            right: '10px',
            width: '30%',
            zIndex: 100,
            padding: '10px',
            backgroundColor: 'rgba(255,255,255,0.8)',
            overflowY: 'scroll',
            borderRadius: '10px',
          }}
        >
          {this.props.children}
        </div>
      </div>
    );
  }
}

export default geolocated({
  positionOptions: {
    enableHighAccuracy: false,
  },
  userDecisionTimeout: 5000,
})(MapView);
