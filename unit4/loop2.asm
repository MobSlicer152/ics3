BITS 64

EXTERN exit
EXTERN printf

SECTION .data
GLOBAL Morgan
Morgan: db "Morgan", 0h
GLOBAL AndMorgan
AndMorgan: db "and Morgan", 0h

SECTION .text

GLOBAL main
main:
    push rbp
    mov rbp, rsp

    ; Print Morgan
    lea rdi, [rel Morgan]
    call printf wrt ..plt

    xor ebx, ebx
    .loop:
        cmp ebx, 100 ; check exit condition
        jae .loop_end
        inc ebx
        ; Print and Morgan
        lea rdi, [rel AndMorgan]
        call printf wrt ..plt
        jmp .loop ; loop
    .loop_end:

    ; Print \n
    push 0ah
    mov rdi, rsp
    call printf wrt ..plt

    xor edi, edi
    call exit wrt ..plt

