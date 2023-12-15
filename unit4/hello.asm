BITS 64

%define NR_exit 60
%define NR_write 1
%define stdout 1

SECTION .data

Message: db "Hello, world!", 0ah, 0h
MessageSize: dq $ - Message

SECTION .text

GLOBAL _start
_start:
    push rbp
    mov rbp, rsp

    mov eax, NR_write
    mov edi, stdout
    lea rsi, [Message]
    mov rdx, QWORD [MessageSize]
    syscall

    mov eax, NR_exit
    xor edi, edi
    syscall
