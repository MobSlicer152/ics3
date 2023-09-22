	bits 64

	%define stdin 0
	%define stdout 1

	%define SYS_read 0
	%define SYS_write 1
	%define SYS_exit_group 231

	%define BUFFER_LENGTH 48	; Numbers get up to about 33 characters with a
					; sign, so this is the closest multiple of 8

	section .text
	
	global _start
_start:
	push rbp
	mov rbp, rsp

					; Allocate memory on the stack for the user's input
	sub rsp, BUFFER_LENGTH
	mov r13, rsp			; Store the buffer's start address in R13 (R12 is used in the
					; loop and R11 is clobbered by syscall)

					; Zero the buffer (equivalent to memset(rdi, rax, rcx);
	cld 				; Go forward (this is clear direction flag, which determines which
	    				; direction to go)
	mov rdi, r13			; start address
	xor eax, eax			; set RAX to zero
	mov ecx, BUFFER_LENGTH		; number of bytes to set
	rep stosq			; Store 8 bytes at a time

					; Print the prompt
	mov edi, stdout			; Write to stdout
	lea rsi, [prompt]		; Load the address of the string
	mov rdx, [promptSize]		; Load the length of the string
	mov eax, SYS_write		; write system call number
	syscall

					; Read user input into the buffer
	mov edi, stdin			; Read from stdin
	mov rsi, r13			; Read to the buffer
	mov edx, BUFFER_LENGTH		; Size of the buffer
	mov eax, SYS_read		; read system call number
	syscall

	mov rdi, rax			; read returns the number of bytes it read, -1 for the newline
					; that should stay where it is
	dec rdi

					; Check for a minus and other things
	xor esi, esi			; ESI is start
	cmp rdi, rsi			; If no length, don't do the loop
	je .reverseloopend
	mov r12b, byte [r13 + rsi]
	cmp r12b, '-'
	jne .reverseloop		; Increment RSI if equal, skip to the loop if not
	inc rsi

					; Reverse the bytes of the input
					; Start index already set up earlier
.reverseloop:
	cmp rdi, rsi
	jle .reverseloopend		; Jump if the destination index is less than the source
					; index
	dec rdi
					; Swap the bytes (take a byte from each and store it (xchg could be used
					; but I don't feel like it)
	mov r12b, byte [r13 + rsi]
	mov r14b, byte [r13 + rdi]
	mov byte [r13 + rsi], r14b
	mov byte [r13 + rdi], r12b
	inc rsi
	jmp .reverseloop
.reverseloopend:

					; Write the message and then the buffer
	mov edi, stdout
	lea rsi, [message]
	mov rdx, [messageSize]
	mov eax, SYS_write
	syscall
	mov rsi, r13
	mov rdx, rax
	mov eax, SYS_write
	syscall

					; Exit so the program doesn't crash
	xor edi, edi
	mov eax, SYS_exit_group
	syscall

	section .data
prompt:
	db "Please enter a three digit number: "
promptSize:
	dq $ - prompt			; $ is the current address, so that minus the start of the string is its 
					; size
					; This only needs a word, because small number

message:
	db "Reversed: "
messageSize:
	dq $ - message
