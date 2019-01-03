#!/usr/bin/bash
/usr/bin/docker run -d -p 9093:1521 -p 8083:81 -v /home/sergio/Env/code/otof-old/h2:/opt/h2-data oscarfonts/h2 java -cp /opt/h2/bin/h2-1.4.197.jar org.h2.tools.Server -trace -web -webAllowOthers -webPort 81  -tcp -tcpAllowOthers -tcpPort 1521  -baseDir /opt/h2-data
