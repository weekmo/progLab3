#!/bin/bash
if [[ -z "$3" && -z "$4" ]];
	then
	java -cp handout8Ex2.jar exercise2.Exercise1 -i $1 -o $2
	java -cp handout8Ex2.jar exercise2.Exercise2 -i $1 -o $2 | java -cp handout8Ex2.jar exercise2.ThirdApplication
elif [[ -z "$4" ]];
	then
	java -cp handout8Ex2.jar exercise2.Exercise1 -i $1 -o $2
	java -cp handout8Ex2.jar exercise2.Exercise2 -i $2 -o $3 | java -cp handout8Ex2.jar exercise2.ThirdApplication
else
	java -cp handout8Ex2.jar exercise2.Exercise1 -i $1 -o $2 -f $4
	java -cp handout8Ex2.jar exercise2.Exercise2 -i $2 -o $1 | java -cp handout8Ex2.jar exercise2.ThirdApplication
fi
