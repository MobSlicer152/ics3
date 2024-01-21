#!/usr/bin/env sh

ROOT=$(dirname $0)
OUT=$ROOT/out

mkdir -p $OUT
javac --release 11 -d $OUT/tool $ROOT/tool/*.java
mkdir -p $ROOT/ast
java -cp $OUT/tool dev.randomcode.calculator.tool.AstGenerator $ROOT/ast
javac --release 11 -d $OUT $ROOT/*.java $ROOT/ast/*.java
jar --create --file Calculator.jar --main-class=dev.randomcode.calculator.Calculator -C $OUT/ .
