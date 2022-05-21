

import java.io.*;
import java.util.ArrayList;

class VMTranslator {

    private static int eqTags = 0;
    private static int gtTags = 0;
    private static int ltTags = 0;

    private static String progName = "";

    public static ArrayList<String[]> parseFile(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        BufferedReader input = new BufferedReader(reader);
        ArrayList<String[]> lines = new ArrayList<>();

        while(input.ready()) {
            String line = input.readLine();
            line = line.trim();
            if(line.contains("//") || line.length() == 0) {
                continue;
            }
            String[] strList = line.split(" ");
            lines.add(strList);
        }

        return lines;
    }

    public static ArrayList<String> pushCommand(String seg, int value) {
        ArrayList<String> assembly = new ArrayList<>();

        assembly.add("// processing: push " + seg + " " + value);

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
            assembly.add("@" + progName + "." + value);
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

        assembly.add("// processing: pop " + seg + " " + value);

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

            assembly.add("@" + progName + "." + value);
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

        assembly.add("// processing: " + seg);

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

    public static ArrayList<String> convertToASM(ArrayList<String[]> lines) {
        ArrayList<String> assembly = new ArrayList<>();
        for(int i = 0; i < lines.size(); i++) {
            if(lines.get(i)[0].equals("pop")) {
                assembly.addAll(popCommand(lines.get(i)[1], Integer.parseInt(lines.get(i)[2])));
            }
            else if(lines.get(i)[0].equals("push")) {
                assembly.addAll(pushCommand(lines.get(i)[1], Integer.parseInt(lines.get(i)[2])));
            }
            else {
                assembly.addAll(otherCommand(lines.get(i)[0]));
            }
        }
        return assembly;
    }

    public static void printFile(String fileName, ArrayList<String> arr) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        BufferedWriter output = new BufferedWriter(writer);

        for(int i = 0; i < arr.size(); i++) {
            output.write(arr.get(i));
            output.newLine();
        }

        output.close();
    }

    public static void main(String[] args) throws Exception {
        String filePath = args[0];
        ArrayList<String[]> lines = parseFile(filePath);

        progName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));

        String newFile = filePath.substring(0, filePath.lastIndexOf(".")) + ".asm";
        //System.out.println(newFile);

        ArrayList<String> assembly = convertToASM(lines);
        printFile(newFile, assembly);

        //printArr(lines);
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