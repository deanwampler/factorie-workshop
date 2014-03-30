
MKDIR data\out
MKDIR data\out\classifier

SET CMD=%1
shift
SET FLAG=
GOTO CASE_%CMD%
:CASE_train
  SET FLAG=true
  SET args=--write-vocabulary data\out\classifier\vocab --write-classifier data\out\classifier\enron_email --training-portion 0.8 --validation-portion 0.1 --trainer new cc.factorie.app.classify.backend.SVMMulticlassTrainer --print-infogain true --read-text-encoding ISO-8859-1 --read-text-dirs "data\enron_spam_ham\spam data\enron_spam_ham\ham"
  GOTO END_SWITCH
:CASE_classify)
  SET FLAG=true
  SET args=--read-vocabulary data\out\classifier\vocab --read-classifier data\out\classifier\enron_email --read-text-encoding ISO-8859-1 --write-classifications data\out\classifier\results.txt --read-text-dirs "data\unclassified\spam data\unclassified\ham"
  GOTO END_SWITCH
:END_SWITCH

IF "%FLAG" == "" (
  ECHO Unrecognized command: %CMD
  GOTO :EOF
)

$root\bin\fac classify %args %*
