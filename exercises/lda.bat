SET DATA=%1
shift
SET D=
GOTO CASE_%DATA%
:CASE_shakespeare
  SET D=data/shakespeare
  GOTO END_SWITCH
:CASE_enron)
  SET D=data/enron_spam_ham/ham
  GOTO END_SWITCH
:END_SWITCH

IF "%D" == "" (
  ECHO Unrecognized data set: %DATA
  GOTO :EOF
)

bin\fac lda --read-dirs %D %*