// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/FunctionCalls/SimpleAdd/SimpleAddVME.tst

load,
output-file SimpleAdd.out,
compare-to SimpleAdd.cmp,
output-list RAM[0]%D1.6.1 RAM[1]%D1.6.1 RAM[2]%D1.6.1 
            RAM[3]%D1.6.1 RAM[4]%D1.6.1 RAM[310]%D1.6.1;

set sp 256,
set local 1000,
set argument 2000,
set this 3000,
set that 4000,

repeat 10 {
  vmstep;
}

output;
