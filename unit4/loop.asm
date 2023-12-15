BITS 64

EXTERN exit
EXTERN printf

SECTION .data
GLOBAL Format
Format: db "%d", 0ah, 0h

SECTION .text

GLOBAL main
main:
    push rbp
    mov rbp, rsp

    xor ebx, ebx
    inc ebx
    .loop:
        cmp ebx, 100 ; check exit condition
        jae .loop_end
        inc ebx
        mov edx, ebx
        and edx, 1 ; check if even
        cmp edx, 0
        jne .loop ; print if even
        lea rdi, [rel Format]
        mov esi, ebx
        call printf wrt ..plt
        jmp .loop ; loop
    .loop_end:

    xor edi, edi
    call exit wrt ..plt

