#!/usr/bin/env sh

ROOT=$(dirname $0)
OUT=$ROOT/out

mkdir -p $OUT
javac --release 11 -d $OUT/tool $ROOT/tool/*.java
java -cp $OUT/tool dev.randomcode.calculator.tool.AstGenerator $ROOT
javac --release 11 -d $OUT $ROOT/*.java
jar --create --file Calculator.jar --main-class=dev.randomcode.calculator.Calculator -C $OUT/ .
