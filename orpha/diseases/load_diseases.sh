#!/bin/bash

curl -XPUT -H"Content-Type: application/json" http://localhost:9200/diseases -d@diseasesmappings.json
for i in `ls bulk_*.json`; do
 curl -XPUT -H"Content-Type: application/json" http://localhost:9200/diseases/_bulk --data-binary @$i
 done
