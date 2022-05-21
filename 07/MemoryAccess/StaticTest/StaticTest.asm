// processing: push constant 111
@111
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 333
@333
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 888
@888
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop static 8
@SP
M=M-1
A=M
D=M
@StaticTest.8
M=D
// processing: pop static 3
@SP
M=M-1
A=M
D=M
@StaticTest.3
M=D
// processing: pop static 1
@SP
M=M-1
A=M
D=M
@StaticTest.1
M=D
// processing: push static 3
@StaticTest.3
D=M
@SP
A=M
M=D
@SP
M=M+1
// processing: push static 1
@StaticTest.1
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
// processing: push static 8
@StaticTest.8
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
