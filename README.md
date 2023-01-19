# ElasticOSM: A reimagining of OpenStreetMap’s search user interface

In this project, I implement an alternative search user interface to the one that's currently available for use at the [OpenStreetMap website](https://osm.org). This is the official repository for my final project submission for INFO 202: Information Organization and Retrieval taught by Professor Marti Hearst at the UC Berkeley School of Information.

### Background
----
With nearly 7.8 million users having contributed over 7 billion data points over the last fifteen years, the OpenStreetMap project has grown to become one of the world’s largest sources of open geographical data today. Not surprisingly, a number of software development efforts, both from commercial stakeholders and from members in the open-source community, have taken place with the goal of making this data more accessible to everyone. 

However, despite all the progress in making information retrieval for OSM faster and more efficient, work on user interfaces that serve as a portal between the user and this data leave much to be desired.

In the sections that follow, I provide an overview of the OpenStreetMap data model, some of the challenges posed by this model to the development of search user interfaces, some potential solutions to tackle identified problems, and finally a proof of concept implementation of an alternate search user interface that aims to implement the same.

### Motivation
----
The purpose of this project is not to “solve” geospatial search, as doing so would be too complex of a challenge to take on for a class project. Geospatial search is highly situational, and its efficacy really depends on what the user’s needs are. The focus of this endeavor is to examine how we can take advantage of presently existing metadata in OSM and recent advances in technology and database systems to provide a subjectively “better” search experience for a general user. 

Through this project, the author therefore aims to develop a proof-of-concept that will help contribute to the conversation around:

- What are some of the challenges and limitations posed by OpenStreetMap’s data model on the design of search user interfaces for OSM data?
- What are some characteristics of metadata available crowdsourced geospatial information, and what are some challenges that arise when we try to organize such information?
- How can OSM metadata be repurposed to facilitate a better search experience for users of OSM data?
- How can recent developments in information retrieval technology support a better search experience for users of OSM data?

The author notes that the very nature of OpenStreetMap being a volunteer driven movement imposes a lot of constraints on developers of active open source projects in the OSM ecosystem. And user interface design, although highly critical to the user experience, is often prone to taking the hit during development with constrained resources. The purpose of this project is not to criticize and dismiss the work of hundreds of selfless volunteers who have brought OpenStreetMap to where it is today, but lay out guidelines for similar work in the future. 

[Click here to read full report](https://docs.google.com/document/d/1pUelazpyA-SWso69bkUQVvfx9Yieqy3Xq_dpC_dySmE/edit?usp=sharing)

### Installation
---

Docker must be installed on the system. After that: 

```
docker-compose up
```

### Troubleshooting and logs
---

#### Using Apple Silicon
Issues with nominatim-docker when running in Apple Silicon. The `docker-compose up` command is stuck at Creating Partition Tables. The reason is due to the incompatibility of postgis in arm-64 machines. More details [here](https://github.com/postgis/docker-postgis/issues/216).

Current workaround is to download the data dump for elastic search and populate the `./data-es` folder with it. 

### Acknowledgements
---
This project would not have been possible without the following open source projects.

- [osm-search/Nominatim](https://github.com/osm-search/Nominatim) is what makes the initial indexing of OSM data into a postgres database possible.
- [mediagis/nominatim-docker](https://github.com/mediagis/nominatim-docker) is a docker container for Nominatim.
- BBBike's [OSM data extract for Berkeley](https://download.bbbike.org/osm/bbbike/Berkeley/)
- [tonytw1/nominatim-ac](https://github.com/tonytw1/nominatim-ac) and their work on indexing the Nominatim DB to Elastic is the starting point for the code in the `pg2es` project.
- All of the volunteer data contributors to the OpenStreetMap project, without whose sustained contributions, none of this would have been possible in the first place.
- [@turf/turf](https://www.npmjs.com/package/@turf/turf)
- [opening_hours](https://github.com/opening-hours/opening_hours.js)
