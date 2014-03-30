
SET cp=src\main\resources:target\classes:lib\factorie-1.0-SNAPSHOT-jar-with-dependencies.jar

SET JAVA_COMMAND="java -Xmx3g -ea -Djava.awt.headless=true -Dfile.encoding=UTF-8 -server -classpath %cp%"

SET CMD=%1
shift
SET CLASS=
GOTO CASE_%CMD%
:CASE_nlp
  SET CLASS=cc.factorie.app.nlp.NLP
  GOTO END_SWITCH
:CASE_lda
  SET CLASS=cc.factorie.app.topics.lda.LDA
:CASE_classify
  SET CLASS=cc.factorie.app.classify.Classify
  GOTO END_SWITCH
:CASE_inter
  SET CLASS=cc.factorie.util.Interpreter
  GOTO END_SWITCH
:CASE_run 
  CLASS=%1
  shift
  GOTO END_SWITCH
:END_SWITCH

IF "%CLASS" == "" (
  ECHO Unrecognized command: %CMD%
  GOTO :EOF
)

echo running: %JAVA_COMMAND %CLASS %*
%JAVA_COMMAND %CLASS %*

