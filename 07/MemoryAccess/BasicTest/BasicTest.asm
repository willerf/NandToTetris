// processing: push constant 10
@10
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop local 0
@0
D=A
@LCL
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
// processing: push constant 21
@21
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 22
@22
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop argument 2
@2
D=A
@ARG
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
// processing: pop argument 1
@1
D=A
@ARG
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
// processing: push constant 36
@36
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop this 6
@6
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
// processing: push constant 42
@42
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 45
@45
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop that 5
@5
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
// processing: pop that 2
@2
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
// processing: push constant 510
@510
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: pop temp 6
@5
D=A
@6
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
// processing: push local 0
@0
D=A
@LCL
A=M
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
// processing: push that 5
@5
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
// processing: push argument 1
@1
D=A
@ARG
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
// processing: push this 6
@6
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
// processing: push this 6
@6
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
// processing: push temp 6
@5
D=A
@6
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
