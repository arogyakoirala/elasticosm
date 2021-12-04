# A reimagining of OpenStreetMap's Search User Interface

### Installation

Docker must be installed on the system. Afyter that: 

```
docker-compose up
```

### Acknowledgements

This project would not have been possible without the following open source projects.

- [osm-search/Nominatim](https://github.com/osm-search/Nominatim) is what makes the initial indexing of OSM data into a postgres database possible.
- [mediagis/nominatim-docker](https://github.com/mediagis/nominatim-docker) is a docker container for Nominatim.
- Geofabrik's daily OSM extracts [download server](https://download.geofabrik.de/), which makes downloading OSM data very easy for the purposes of our project.
- [tonytw1/nominatim-ac](https://github.com/tonytw1/nominatim-ac) and their work on indexing the Nominatim DB to Elastic is the starting point for the code in the `pg2es` project.
- All of the volunteer data contributors to the OpenStreetMap project, without whose sustained contributions, none of this would have been possible in the first place.
