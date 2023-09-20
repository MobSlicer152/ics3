bits        64          

    %define     stdin       0           
    %define     stdout      1           

    %define     SYS_read    0           
    %define     SYS_write   1           
    %define     SYS_exit_group231         

    %define     BUFFER_LENGTH48          ;           Numbers     get         up          to          about       33          characters  with        a           
    ;           sign,       so          this        is          the         closest     multiple    of          8           

    section         .text 

    global      _start      
_start:
    push        rbp         
    mov         rbp,        rsp         

    ;           Allocate    memory      on          the         stack       for         the         user's      input       
    sub         rsp,        BUFFER_LENGTH
    mov         r11,        rsp         ;           Store       the         buffer's    start       address     in          R11         

    ;           Zero        the         buffer      (equivalent to          memset(rdi, rax,        rcx);       
    cld         ;           Go          forward     (this       is          clear       direction   flag,       which       determines  which       
    ;           direction   to          go)         
    mov         rdi,        r11         ;           start       address     
    xor         eax,        eax         ;           set         RAX         to          zero        
    mov         ecx,        BUFFER_LENGTH;           number      of          bytes       to          set         
    rep         stosq       ;           Store       8           bytes       at          a           time        

    ;           Print       the         prompt      
    mov         edi,        stdout      ;           Write       to          stdout      
    lea         rsi,        [prompt]    ;           Load        the         address     of          the         string      
    mov         rdx,        [promptSize];           Load        the         length      of          the         string      
    mov         eax,        SYS_write   ;           write       system      call        number      
    syscall     

    ;           Read        user        input       into        the         buffer      
    mov         edi,        stdin       ;           Read        from        stdin       
    mov         rsi,        r11         ;           Read        to          the         buffer      
    mov         edx,        BUFFER_LENGTH;           Size        of          the         buffer      
    mov         eax,        SYS_read    
    syscall     

    ;           Exit        so          the         program     doesn't     crash       
    neg         eax         
    mov         edi,        eax         
    mov         eax,        SYS_exit_group
    syscall     

    section         .data 
prompt:
    db          "Please enter a three digit number: "
promptSize:
dq $ - prompt			; $ is the current address, so that minus the start of the string is its
; size
; This only needs a word, because small number

message:
db "Reversed: "
messageSize:
dq $ - prompt
