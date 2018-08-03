#!/bin/bash

java -classpath ~/.m2/repository/com/h2database/h2/1.3.176/h2-1.3.176.jar org.h2.tools.Server -tcpShutdown tcp://localhost:9092 

