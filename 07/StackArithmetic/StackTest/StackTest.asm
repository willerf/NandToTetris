// processing: push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: eq
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@EQTRUE0
D;JEQ
@SP
A=M
M=0
@EQEND0
0;JMP
(EQTRUE0)
@SP
A=M
M=-1
(EQEND0)
@SP
M=M+1
// processing: push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: eq
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@EQTRUE1
D;JEQ
@SP
A=M
M=0
@EQEND1
0;JMP
(EQTRUE1)
@SP
A=M
M=-1
(EQEND1)
@SP
M=M+1
// processing: push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: eq
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@EQTRUE2
D;JEQ
@SP
A=M
M=0
@EQEND2
0;JMP
(EQTRUE2)
@SP
A=M
M=-1
(EQEND2)
@SP
M=M+1
// processing: push constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: lt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@LTTRUE0
D;JLT
@SP
A=M
M=0
@LTEND0
0;JMP
(LTTRUE0)
@SP
A=M
M=-1
(LTEND0)
@SP
M=M+1
// processing: push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: lt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@LTTRUE1
D;JLT
@SP
A=M
M=0
@LTEND1
0;JMP
(LTTRUE1)
@SP
A=M
M=-1
(LTEND1)
@SP
M=M+1
// processing: push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: lt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@LTTRUE2
D;JLT
@SP
A=M
M=0
@LTEND2
0;JMP
(LTTRUE2)
@SP
A=M
M=-1
(LTEND2)
@SP
M=M+1
// processing: push constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: gt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@GTTRUE0
D;JGT
@SP
A=M
M=0
@GTEND0
0;JMP
(GTTRUE0)
@SP
A=M
M=-1
(GTEND0)
@SP
M=M+1
// processing: push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: gt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@GTTRUE1
D;JGT
@SP
A=M
M=0
@GTEND1
0;JMP
(GTTRUE1)
@SP
A=M
M=-1
(GTEND1)
@SP
M=M+1
// processing: push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: gt
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
D=M-D
@GTTRUE2
D;JGT
@SP
A=M
M=0
@GTEND2
0;JMP
(GTTRUE2)
@SP
A=M
M=-1
(GTEND2)
@SP
M=M+1
// processing: push constant 57
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 31
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: push constant 53
@53
D=A
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
// processing: push constant 112
@112
D=A
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
// processing: neg
@SP
M=M-1
A=M
M=-M
@SP
M=M+1
// processing: and
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=D&M
@SP
M=M+1
// processing: push constant 82
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
// processing: or
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=D|M
@SP
M=M+1
// processing: not
@SP
M=M-1
A=M
M=!M
@SP
M=M+1
