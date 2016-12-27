#!/bin/bash
IMBOX_HOME=`dirname $0`/..
IMBOX_HOME=`(cd "$IMBOX_HOME"; pwd)`

P=":" # The default classpath separator
echo "Running on " $OS
# JAVA options
if [ -z "$JVM_OPTS" ]; then
    JVM_OPTS="-Xverify:none -XX:+TieredCompilation -XX:+UseBiasedLocking -XX:InitialCodeCacheSize=8m -XX:ReservedCodeCacheSize=32m -Dorg.terracotta.quartz.skipUpdateCheck=true"
fi
export JAVA_OPTS="$JAVA_OPTS $JVM_OPTS $NATIVE"

if [ -z "$IMBOX_MAINCLASS" ]; then
  export IMBOX_MAINCLASS=ShutdownServer
fi

if [ -z "$IMBOX_OPTS" ]; then
  export IMBOX_OPTS=9999
fi

for JAVA in "${JAVA_HOME}/bin/java" "${JAVA_HOME}/Home/bin/java" "/usr/bin/java" "/usr/local/bin/java"
do
  if [ -x "$JAVA" ]
  then
    break
  fi
done

if [ ! -x "$JAVA" ]
then
  echo "Unable to locate Java. Please set JAVA_HOME environment variable."
  exit
fi

export IMBOX_CLASSPATH="${IMBOX_HOME}/IM-server.jar${P}${IMBOX_HOME}/conf${P}${CLASSPATH}"

# start IMBOX
echo "Starting IMBOX"
exec "$JAVA" -DIMBOX.root="${IMBOX_HOME}" $JAVA_OPTS -cp "${IMBOX_CLASSPATH}" "$IMBOX_MAINCLASS" $IMBOX_OPTS

