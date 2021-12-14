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
