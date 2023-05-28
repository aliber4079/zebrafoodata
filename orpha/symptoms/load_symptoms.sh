#!/bin/bash

curl -XPUT -H"Content-Type: application/json" http://localhost:9200/symptoms -d@symptomsmappings.json
 curl -XPUT -H"Content-Type: application/json" http://localhost:9200/symptoms/_bulk --data-binary @symptomsbulk.json
