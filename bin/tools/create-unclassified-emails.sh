#!/bin/bash

# Used to extract 10% of the Enron emails for "reclassifying".

dir=$(dirname $0)
root=$(dirname $dir)

mkdir -p $root/data/enron_spam_ham/unclassified

let RANGE=100
let PERCENT=10

rand() {
  number=$RANDOM
  let "number %= $RANGE"
  echo $number
}

let i2=0
find $root/data/enron_spam_ham/{spam,ham} -type f | while read f
do
  let n=$(rand)
  if [ $n -lt $PERCENT ]
  then 
    let i2=$i2+1
    cp $f $root/data/enron_spam_ham/unclassified
  fi
done

echo "$i2 files copied"

