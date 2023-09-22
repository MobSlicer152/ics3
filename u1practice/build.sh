#!/usr/bin/env sh

nasm -felf64 Question4.asm
ld -o Question4 Question4.o
./Question4

