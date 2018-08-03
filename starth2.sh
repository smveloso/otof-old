#!/bin/bash

java -classpath ~/.m2/repository/com/h2database/h2/1.3.176/h2-1.3.176.jar org.h2.tools.Server -tcp -tcpPort 9092 -web -webPort 8082 -baseDir ~/h2

