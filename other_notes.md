### Other exploration
---

#### Class and type counts

To get a distribution of `class` and `type` values, use the following commands once Nominatim DB is populated.

```sh
# get nominatimContainerId
docker ps -a
docker exec -it [nominatimContainerId] su - nominatim

# enter postgres, run query, and save csv
psql
COPY(SELECT
   class, type, count(1) as total,
   count(name) as total_with_name,
   count(1) - count(name) as total_without_name,
   100.0 * count(name) / count(1) as percent_with_name
FROM
   placex group by 1,2 order by 3 desc, 6) to '/tmp/counts.csv' With CSV DELIMITER ',' HEADER;

COPY(SELECT
   class, count(1) as total,
   count(name) as total_with_name,
   count(1) - count(name) as total_without_name,
   100.0 * count(name) / count(1) as percent_with_name
FROM
   placex group by 1 order by 2 desc, 5) to '/tmp/counts_class.csv' With CSV DELIMITER ',' HEADER;


# copy saved csv into host machine (run this from host machine) 
docker cp [nominatimContainerId]:/tmp/*.csv .
docker cp [nominatimContainerId]:/tmp/counts_class.csv .

```

#### Names that are Null and not null by class and type

```sql
SELECT
   class, type, count(1) as total,
   count(name) as total_with_name,
   count(1) - count(name) as total_without_name,
   100.0 * count(name) / count(1) as percent_with_name
FROM
   placex group by 1,2 order by 3 desc, 6;
```



### Implementation
---

#### Improving search results

##### `lowercasing`
Reducing the penalty on users to match the cases.

Fields that are lowercased:



##### `synonymns`
Associating similar categories.
Providing the way to reconcile OSM's strict standards for metadata, with search behaviour in the real world. Prototype theory: what is a bar to one user can be a pub to another.

##### Lemmatization using `porter-stemmer`
Reducing the penalty on the user. Adding support for plurals singulars and variants.


##### `metadata for faceting`

https://stackoverflow.com/questions/67377195/how-to-get-value-from-aggregation-buckets-in-java-for-elasticsearch-aggregation
https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/_bucket_aggregations.html#java-aggs-bucket-geodistance


#### Improving the search experience

##### `fuzzy querying`
Reducing the penalty and the onus on the user to use the exact spelling of the place.

##### `edge-ngrams`
Introducing autocomplete to help users complete their queries.

##### `faceting using query aggregators`
Will help users on the next stage of search, lower their 


### Areas for future work

- How well does this scale?
- Can we test the interface in some way?


https://www.rosehosting.com/blog/how-to-install-lets-encrypt-on-ubuntu-20-04-with-apache/
