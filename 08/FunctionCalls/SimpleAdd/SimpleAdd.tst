
// File name: projects/08/FunctionCalls/SimpleAdd/SimpleAdd.tst

load SimpleAdd.asm,
output-file SimpleAdd.out,
compare-to SimpleAdd.cmp,
output-list RAM[0]%D1.6.1 RAM[1]%D1.6.1 RAM[2]%D1.6.1 
            RAM[3]%D1.6.1 RAM[4]%D1.6.1 RAM[310]%D1.6.1;

set RAM[0] 256,
set RAM[1] 1000,
set RAM[2] 2000,
set RAM[3] 3000,
set RAM[4] 4000,

repeat 300 {
  ticktock;
}

output;
