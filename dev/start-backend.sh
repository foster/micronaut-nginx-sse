#!/bin/bash

backend_root=$( cd "$(dirname "$0")" ; pwd -P )/../backend
(cd $backend_root && ./mvnw mn:run)
