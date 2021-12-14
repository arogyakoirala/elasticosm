import * as oh from 'opening_hours';
import * as aq from 'arquero';
import * as turf from '@turf/turf';

export const filterBasedOnConfig = (data, filterConfig) => {
  const matches = [];

  const matchesFilter = (item) => {
    const keys = Object.keys(filterConfig);
    let count = 0;
    for (var n = 0; n < keys.length; n++) {
      if (filterConfig[keys[n]].indexOf(item[keys[n]]) > -1) {
        count++;
      }
    }
    return count === Object.keys(filterConfig).length;
  };

  // Loop through each item in the array
  for (var i = 0; i < data.length; i++) {
    // Determine if the current item matches the filter criteria
    if (matchesFilter(data[i])) {
      matches.push(data[i]);
    }
  }

  return matches;
};

export const preprocess = (data, userCoords) => {
  const { aggregations, results } = data;

  // Reset openingHours to be open or close
  const newResults = results.map((item) => {
    if (item.openingHours && item.openingHours !== 'Not known') {
      try {
        const a = new oh(item.openingHours);
        const isOpen = a.getState();
        return { ...item, openingHours: isOpen ? 'Open' : 'Closed' };
      } catch (error) {
        return { ...item, openingHours: 'Not known' };
      }
    }

    return {
      ...item,
      openingHours: item.openingHours ? item.openingHours : 'Not applicable',
    };
  });

  // Reaggregate opening hours
  const openingHoursData = newResults.map((item) => {
    return { openingHours: item.openingHours };
  });

  const newOpeningHourCounts = aq
    .from(openingHoursData)
    .groupby('openingHours')
    .count()
    .objects();

  const newOpeningHourAggs = newOpeningHourCounts.map((item) => {
    return {
      facet: 'openingHours',
      error: 0,
      label: item.openingHours,
      value: item.count,
    };
  });

  // Geodistance aggregations
  const resultsWithDistance = newResults.map((item) => {
    const from = turf.point([userCoords.latitude, userCoords.longitude]);
    const to = turf.point([item.centroid.lat, item.centroid.lon]);
    const options = { units: 'miles' };
    const distance = turf.distance(from, to, options);
    const distanceInWords =
      distance < 1
        ? 'Less than a mile'
        : distance < 5
        ? 'Between 1 and 5 miles'
        : 'More than 5 miles';
    return {
      ...item,
      distance: distanceInWords,
    };
  });

  const distanceData = resultsWithDistance.map((item) => {
    return { distance: item.distance };
  });

  const newDistanceCounts = aq
    .from(distanceData)
    .groupby('distance')
    .count()
    .objects();

  const newDistanceAggs = newDistanceCounts.map((item) => {
    return {
      facet: 'distance',
      error: 0,
      label: item.distance,
      value: item.count,
    };
  });

  const newAggs = [
    ...newOpeningHourAggs,
    ...newDistanceAggs,
    ...aggregations.filter((item) => {
      return item.facet !== 'openingHours' && item.facet !== 'distance';
    }),
  ];

  return { results: resultsWithDistance, aggregations: newAggs };
};
