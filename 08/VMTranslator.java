

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

class VMTranslator {

    private static int eqTags = 0;
    private static int gtTags = 0;
    private static int ltTags = 0;

    private static String fileName = "";
    private static Stack<String> stack = new Stack<>();
    private static int callNum = 0;


    public static ArrayList<String[]> parseFile(File file) throws IOException {
        FileReader reader = new FileReader(file);
        BufferedReader input = new BufferedReader(reader);
        ArrayList<String[]> lines = new ArrayList<>();

        while(input.ready()) {
            String line = input.readLine();
            line = line.trim();
            System.out.printf("\n%-110s", line);

            if(line.length() == 0 || line.indexOf("//") == 0) {
                continue;
            }
            String[] removeComments = line.split("//");
            String[] tokenList = removeComments[0].trim().split(" ");
            
            System.out.print(Arrays.toString(tokenList));

            lines.add(tokenList);
        }

        return lines;
    }

    public static ArrayList<String> pushCommand(String seg, int value) {
        ArrayList<String> assembly = new ArrayList<>();

        if(seg.equals("local") || seg.equals("argument") || seg.equals("this") || seg.equals("that")) {
            assembly.add("@" + value);
            assembly.add("D=A");
            if(seg.equals("local")) {
                assembly.add("@LCL");
            }
            if(seg.equals("argument")) {
                assembly.add("@ARG");
            }
            if(seg.equals("this")) {
                assembly.add("@THIS");
            }
            if(seg.equals("that")) {
                assembly.add("@THAT");
            }
            assembly.add("A=M");
            assembly.add("A=D+A");
            assembly.add("D=M");
            assembly.add("@SP");
            assembly.add("A=M");
            assembly.add("M=D");
            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        if(seg.equals("constant")) {
            assembly.add("@" + value);
            assembly.add("D=A");
            assembly.add("@SP");
            assembly.add("A=M");
            assembly.add("M=D");
            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        if(seg.equals("static")) {
            assembly.add("@" + fileName + "." + value);
            assembly.add("D=M");
            assembly.add("@SP");
            assembly.add("A=M");
            assembly.add("M=D");
            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        if(seg.equals("pointer")) {
            assembly.add("@" + (3 + value));
            assembly.add("D=M");
            assembly.add("@SP");
            assembly.add("A=M");
            assembly.add("M=D");
            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        if(seg.equals("temp")) {
            assembly.add("@5");
            assembly.add("D=A");
            assembly.add("@" + value);
            assembly.add("A=D+A");
            assembly.add("D=M");
            assembly.add("@SP");
            assembly.add("A=M");
            assembly.add("M=D");
            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        return assembly;
    }

    public static ArrayList<String> popCommand(String seg, int value) {
        ArrayList<String> assembly = new ArrayList<>();

        if(seg.equals("local") || seg.equals("argument") || seg.equals("this") || seg.equals("that")) {

            assembly.add("@" + value);
            assembly.add("D=A");
            if(seg.equals("local")) {
                assembly.add("@LCL");
            }
            if(seg.equals("argument")) {
                assembly.add("@ARG");
            }
            if(seg.equals("this")) {
                assembly.add("@THIS");
            }
            if(seg.equals("that")) {
                assembly.add("@THAT");
            }
            assembly.add("A=M");
            assembly.add("D=D+A");
            assembly.add("@R13");
            assembly.add("M=D");

            assembly.add("@SP");
            assembly.add("M=M-1");
            assembly.add("A=M");
            assembly.add("D=M");

            assembly.add("@R13");
            assembly.add("A=M");
            assembly.add("M=D");
        }

        if(seg.equals("static")) {

            assembly.add("@SP");
            assembly.add("M=M-1");
            assembly.add("A=M");
            assembly.add("D=M");

            assembly.add("@" + fileName + "." + value);
            assembly.add("M=D");
        }

        if(seg.equals("pointer")) {
                assembly.add("@SP");
                assembly.add("M=M-1");
                assembly.add("A=M");
                assembly.add("D=M");

                assembly.add("@" + (value + 3));
                assembly.add("M=D");

        }

        if(seg.equals("temp")) {
            assembly.add("@5");
            assembly.add("D=A");
            assembly.add("@" + value);
            assembly.add("D=D+A");
            assembly.add("@R13");
            assembly.add("M=D");

            assembly.add("@SP");
            assembly.add("M=M-1");
            assembly.add("A=M");
            assembly.add("D=M");

            assembly.add("@R13");
            assembly.add("A=M");
            assembly.add("M=D");
        }

        return assembly;

    }

    public static ArrayList<String> otherCommand(String seg) {
        ArrayList<String> assembly = new ArrayList<>();

        assembly.add("@SP");
        assembly.add("M=M-1");
        assembly.add("A=M");

        if(seg.equals("neg") || seg.equals("not")) {
            if(seg.equals("neg")) {
                assembly.add("M=-M");
            }
            else if(seg.equals("not")) {
                assembly.add("M=!M");
            }
        }
        else if(seg.equals("add") || seg.equals("sub") || seg.equals("and") || seg.equals("or")) {
            assembly.add("D=M");
            assembly.add("@SP");
            assembly.add("M=M-1");
            assembly.add("A=M");
            if(seg.equals("add")) {
                assembly.add("M=M+D");
            }
            else if(seg.equals("sub")) {
                assembly.add("M=M-D");
            }
            else if(seg.equals("and")) {
                assembly.add("M=D&M");
            }
            else if(seg.equals("or")) {
                assembly.add("M=D|M");
            }
        }
        else if(seg.equals("eq") || seg.equals("gt") || seg.equals("lt")) {
            assembly.add("D=M");
            assembly.add("@SP");
            assembly.add("M=M-1");
            assembly.add("A=M");
            assembly.add("D=M-D");

            if(seg.equals("eq")) {
                assembly.add("@EQTRUE" + eqTags);
                assembly.add("D;JEQ");
                
                assembly.add("@SP");
                assembly.add("A=M");
                assembly.add("M=0");
                assembly.add("@EQEND" + eqTags);
                assembly.add("0;JMP");

                assembly.add("(EQTRUE" + eqTags + ")");
                assembly.add("@SP");
                assembly.add("A=M");
                assembly.add("M=-1");
                
                assembly.add("(EQEND" + eqTags + ")");
                eqTags++;
            }
            if(seg.equals("gt")) {
                assembly.add("@GTTRUE" + gtTags);
                assembly.add("D;JGT");
                
                assembly.add("@SP");
                assembly.add("A=M");
                assembly.add("M=0");
                assembly.add("@GTEND" + gtTags);
                assembly.add("0;JMP");

                assembly.add("(GTTRUE" + gtTags + ")");
                assembly.add("@SP");
                assembly.add("A=M");
                assembly.add("M=-1");
                
                assembly.add("(GTEND" + gtTags + ")");
                gtTags++;
            }
            if(seg.equals("lt")) {
                assembly.add("@LTTRUE" + ltTags);
                assembly.add("D;JLT");
                
                assembly.add("@SP");
                assembly.add("A=M");
                assembly.add("M=0");
                assembly.add("@LTEND" + ltTags);
                assembly.add("0;JMP");

                assembly.add("(LTTRUE" + ltTags + ")");
                assembly.add("@SP");
                assembly.add("A=M");
                assembly.add("M=-1");
                
                assembly.add("(LTEND" + ltTags + ")");
                ltTags++;
            }
        }

        assembly.add("@SP");
        assembly.add("M=M+1");

        return assembly;

    }

    public static ArrayList<String> makeLabel(String labelName) {
        ArrayList<String> assembly = new ArrayList<>();

        if(stack.isEmpty()) {
            assembly.add("(" + labelName + ")");
        }
        else {
            assembly.add("(" + stack.peek() + "$" + labelName + ")");
        }

        return assembly;
    }

    public static ArrayList<String> goTo(String labelName) {
        ArrayList<String> assembly = new ArrayList<>();

        assembly.add("@" + labelName);
        assembly.add("0;JMP");

        return assembly;
    }

    public static ArrayList<String> ifGoTo(String labelName) {
        ArrayList<String> assembly = new ArrayList<>();

        assembly.add("@SP");
        assembly.add("M=M-1");
        assembly.add("A=M");
        assembly.add("D=M");

        assembly.add("@" + labelName);
        assembly.add("D;JNE");

        return assembly;
    }

    public static ArrayList<String> makeFunc(String funcName, int numLocals) {
        ArrayList<String> assembly = new ArrayList<>();

        assembly.add("(" + funcName + ")");
        assembly.add("@SP");
        for(int i = 0; i < numLocals; i++) {
            assembly.add("A=M");
            assembly.add("M=0");
            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        return assembly;
    }

    public static ArrayList<String> callFunc(String funcName, int numArgs) {
        ArrayList<String> assembly = new ArrayList<>();

        String returnAddr = funcName + callNum;
        callNum++;

        String[] save = {returnAddr, "LCL", "ARG", "THIS", "THAT"};

        for(int i = 0; i < save.length; i++) {
            assembly.add("@" + save[i]);
            assembly.add("D=A");

            assembly.add("@SP");
            assembly.add("A=M");
            assembly.add("M=D");

            assembly.add("@SP");
            assembly.add("M=M+1");
        }

        assembly.add("@" + (numArgs+5));
        assembly.add("D=A");

        assembly.add("@SP");
        assembly.add("MD=M-D");

        assembly.add("@LCL");
        assembly.add("M=D");

        assembly.add("@" + funcName);
        assembly.add("0;JMP");

        assembly.add("(" + returnAddr + ")");

        return assembly;
    }

    public static ArrayList<String> returnFunc() {
        ArrayList<String> assembly = new ArrayList<>();






        return assembly;
    }

    public static ArrayList<String> convertToASM(ArrayList<String[]> lines) {
        ArrayList<String> assembly = new ArrayList<>();

        for(int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i);

            String strLine = "";
            for(int j = 0; j < line.length; j++) {
                strLine += " " + line[j];
            }

            assembly.add("//" + strLine);
            if(line[0].equals("pop")) {
                assembly.addAll(popCommand(line[1], Integer.parseInt(line[2])));
            }
            else if(line[0].equals("push")) {
                assembly.addAll(pushCommand(line[1], Integer.parseInt(line[2])));
            }
            else if(line[0].equals("label")) {
                assembly.addAll(makeLabel(line[1]));
            }
            else if(line[0].equals("goto")) {
                assembly.addAll(goTo(line[1]));
            }
            else if(line[0].equals("if-goto")) {
                assembly.addAll(ifGoTo(line[1]));
            }
            else {
                assembly.addAll(otherCommand(line[0]));
            }
        }
        return assembly;
    }

    public static void printFile(String filePath, ArrayList<String> arr) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        BufferedWriter output = new BufferedWriter(writer);

        for(int i = 0; i < arr.size(); i++) {
            output.write(arr.get(i));
            output.newLine();
        }

        output.close();
    }

    public static void main(String[] args) throws Exception {

        File directory = new File(args[0]);
        File[] files = directory.listFiles();

        ArrayList<String> assembly = new ArrayList<>();

        for(int i = 0; i < files.length; i++) {
            if(files[i].isFile()) {
                String tempName = files[i].getName();
                String[] fileSplit = tempName.split("\\.");
                if(fileSplit.length > 0 && fileSplit[1].equals("vm")) {
                    fileName = fileSplit[0];
                    System.out.println("Found: " + fileName + ".vm");
                    ArrayList<String[]> lines = parseFile(files[i]);
                    assembly.addAll(convertToASM(lines));
                }
            }
        }
        
        String assemblyFilePath = args[0] + "/" + directory.getName() + ".asm";
        printFile(assemblyFilePath, assembly);

    }

    public static void printArr(ArrayList<String[]> lines) {
        for(int i = 0; i < lines.size(); i++) {
            String outStr = "[";
            for(int j = 0; j < lines.get(i).length; j++) {
                outStr += lines.get(i)[j] + ", ";
            }
            outStr = outStr.substring(0, outStr.length()-2) + "]";
            System.out.println(outStr);
        }
    }

}