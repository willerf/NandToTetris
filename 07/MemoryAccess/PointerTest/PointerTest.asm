// processing: push constant 3030
@3030
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop pointer 0
@SP
M=M-1
A=M
D=M
@3
M=D
// processing: push constant 3040
@3040
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop pointer 1
@SP
M=M-1
A=M
D=M
@4
M=D
// processing: push constant 32
@32
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop this 2
@2
D=A
@THIS
A=M
D=D+A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
// processing: push constant 46
@46
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop that 6
@6
D=A
@THAT
A=M
D=D+A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
// processing: push pointer 0
@3
D=M
@SP
A=M
M=D
@SP
M=M+1
// processing: push pointer 1
@4
D=M
@SP
A=M
M=D
@SP
M=M+1
// processing: add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M+D
@SP
M=M+1
// processing: push this 2
@2
D=A
@THIS
A=M
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
// processing: sub
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M-D
@SP
M=M+1
// processing: push that 6
@6
D=A
@THAT
A=M
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
// processing: add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M+D
@SP
M=M+1
