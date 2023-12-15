BITS 64

extern printf
extern exit

SECTION .data
STATIC Format
Format: db "%s %s", 0ah, 0h

STATIC FirstName0
FirstName0: db "John", 0h
STATIC FirstName1
FirstName1: db "Luke", 0h

STATIC LastName0
LastName0: db "Halo", 0h
STATIC LastName1
LastName1: db "Skywalker", 0h

SECTION .text

GLOBAL main
main:
    push rbp
    mov rbp, rsp

    sub rsp, 2 * 8 + 2 * 8 ; two arrays of two pointers

    ; no m64 -> m64 is so cringe
    lea rdi, [rel FirstName0]
    mov QWORD [rsp + 0 * 8], rdi
    lea rdi, [rel FirstName1]
    mov QWORD [rsp + 1 * 8], rdi

    lea rdi, [rel LastName0]
    mov QWORD [rsp + 2 * 8], rdi
    lea rdi, [rel LastName1]
    mov QWORD [rsp + 3 * 8], rdi

    lea rdi, [rel Format]
    mov rsi, QWORD [rsp + 0 * 8] ; FirstNames[0]
    mov rdx, QWORD [rsp + 2 * 8] ; LastNames[0]
    call printf wrt ..plt

    xor edi, edi
    call exit wrt ..plt

