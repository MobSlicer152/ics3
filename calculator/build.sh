#!/usr/bin/env sh

ROOT=$(dirname $0)
OUT=$ROOT/out

mkdir -p $OUT
javac --target 11 -d $OUT $ROOT/*.java
jar --create --file Calculator.jar --main-class=calculator.Calculator -C $OUT/ .
