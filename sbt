#!/bin/sh
test -f ~/.sbtconfig && . ~/.sbtconfig
exec java -Xmx512M ${SBT_OPTS} -jar bin/sbt-launch.jar "$@"
