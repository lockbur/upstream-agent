#!/bin/bash

IMBOX_HOME=`dirname $0`/..
IMBOX_HOME=`(cd "$IMBOX_HOME"; pwd)`


IMBOX_BIN=`dirname $0`
IMBOX_BIN=`(cd "$IMBOX_BIN"; pwd)`

export JVM_OPTS="-Xmx1024m -Xms512m -Xss256k -XX:+AggressiveOpts -XX:+DisableExplicitGC -XX:ParallelGCThreads=4 -XX:+UseConcMarkSweepGC -Xverify:none -XX:+TieredCompilation -XX:+UseBiasedLocking -XX:+UseStringCache -XX:SurvivorRatio=16 -XX:TargetSurvivorRatio=90 -XX:MaxTenuringThreshold=31 -Djava.net.preferIPv4Stack=true -XX:InitialCodeCacheSize=8m -XX:ReservedCodeCacheSize=32m -Dorg.terracotta.quartz.skipUpdateCheck=true"

# start
echo "Setting Hi Performance Options"
exec $IMBOX_BIN/runner.sh >> /export/Logs/lc.jd.local/logs/jvm.stdout 2>&1 &
