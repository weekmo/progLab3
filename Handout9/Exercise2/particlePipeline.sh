#!/bin/bash
java -cp handout8Ex2.jar exercise2.Exercise1 -i $1 -o $2 -f png
java -cp handout8Ex2.jar exercise2.Exercise2 -i $1 -o $2 | java -cp handout8Ex2.jar exercise2.ThirdApplication 

