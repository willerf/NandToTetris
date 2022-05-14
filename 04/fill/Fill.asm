// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

@color
M=0

(SETSCREEN)

    @SCREEN
    D=A

    @currAddr
    M=D

    @KBD
    D=A

    @endAddr
    M=D

(LOOPSCREEN)

    @color
    D=M
    
    @currAddr
    A=M
    M=D

    @currAddr
    M=M+1
    D=M

    @endAddr
    D=D-M

    @LOOPSCREEN
    D; JLT

(LISTENKEY)

    @KBD
    D=M

    @SETWHITE
    D; JEQ

    
(SETBLACK)
    @color
    M=-1
    
    @SETSCREEN
    0; JMP

(SETWHITE)
    @color
    M=0

    @SETSCREEN
    0; JMP


