
#include <iostream>
#include <fstream>
#include <unordered_map>
#include <algorithm>

using namespace std;

unordered_map<string, string> initializeDest() {
    unordered_map<string, string> dest;

    dest["null"] = "000";
    dest["M"] = "001";
    dest["D"] = "010";
    dest["MD"] = "011";
    dest["A"] = "100";
    dest["AM"] = "101";
    dest["AD"] = "110";
    dest["AMD"] = "111";

    return dest;
}

unordered_map<string, string> initializeJump() {
    unordered_map<string, string> jump;

    jump["null"] = "000";
    jump["JGT"] = "001";
    jump["JEQ"] = "010";
    jump["JGE"] = "011";
    jump["JLT"] = "100";
    jump["JNE"] = "101";
    jump["JLE"] = "110";
    jump["JMP"] = "111";

    return jump;
}

unordered_map<string, string> initializeOpCodes() {
    unordered_map<string, string> op_codes;

    op_codes["0"] = "0101010";
    op_codes["1"] = "0111111";
    op_codes["-1"] = "0111010";
    op_codes["D"] = "0001100";
    op_codes["A"] = "0110000";
    op_codes["!D"] = "0001101";
    op_codes["!A"] = "0110001";
    op_codes["-D"] = "0001111";
    op_codes["-A"] = "0110011";
    op_codes["D+1"] = "0011111";
    op_codes["A+1"] = "0110111";
    op_codes["D-1"] = "0001110";
    op_codes["A-1"] = "0110010";
    op_codes["D+A"] = "0000010";
    op_codes["D-A"] = "0010011";
    op_codes["A-D"] = "0000111";
    op_codes["D&A"] = "0000000";
    op_codes["D|A"] = "0010101";

    op_codes["M"] = "1110000";
    op_codes["!M"] = "1110001";
    op_codes["-M"] = "1110011";
    op_codes["M+1"] = "1110111";
    op_codes["M-1"] = "1110010";
    op_codes["D+M"] = "1000010";
    op_codes["D-M"] = "1010011";
    op_codes["M-D"] = "1000111";
    op_codes["D&M"] = "1000000";
    op_codes["D|M"] = "1010101";

    return op_codes;
}

unordered_map<string, int> initializeSymbols() {
    unordered_map<string, int> symbol_table;
    for(int i = 0; i < 16; i++) {
        symbol_table["R"+to_string(i)] = i;
    }
    
    symbol_table["SCREEN"] = 16384;
    symbol_table["KBD"] = 24576;
    symbol_table["SP"] = 0;
    symbol_table["LCL"] = 1;
    symbol_table["ARG"] = 2;
    symbol_table["THIS"] = 3;
    symbol_table["THAT"] = 4;

    return symbol_table;
}

string getBinary(int num) {
    string result(16, '0');
    
    for(int i = 15; i >= 0; i--) {
        int bit = num%2;
        num /= 2;
        result[i] = char(bit + '0');
    }

    return result;
}


void firstPass(ifstream &assemblyFile, unordered_map<string, int> &symbol_table) {
    string line;
    int num = 0;

    while(!assemblyFile.eof()) {
        getline(assemblyFile, line);
        line.erase(remove_if(line.begin(), line.end(), ::isspace), line.end());

        if(line.size() <= 1 || (line[0] == '/' && line[1] == '/')) {
            continue;
        }
        else if(line[0] == '(') {
            string new_symbol = line.substr(1, line.find(')')-1);
            symbol_table[new_symbol] = num;
        }
        else {
            num++;
        }
    }
}

void buildMachineCode(ifstream &assemblyFile, ofstream &outputFile, unordered_map<string, int> &symbol_table) {
    unordered_map<string, string> op_codes = initializeOpCodes();
    unordered_map<string, string> dest = initializeDest();
    unordered_map<string, string> jump = initializeJump();
    
    string line;
    int ram_index = 16;
    while(!assemblyFile.eof()) {
        getline(assemblyFile, line);

        int comment = line.find("//");
        line = line.substr(0, comment);
        line.erase(remove_if(line.begin(), line.end(), ::isspace), line.end());

        cout << "Processing |" << line << endl;
        
        if(line.size() <= 1 || (line[0] == '/' && line[1] == '/') || line[0] == '(') {
            continue;
        }
        else if(line[0] == '@') {
            string binLine;
            if(isdigit(line[1])) {
                string numStr = "";
                int index = 1;
                while(index < line.size() && isdigit(line[index])) {
                    numStr += (char)(line[index]);
                    index++;
                }
                binLine = getBinary(stoi(numStr));
            }
            else {
                string symbol = line.substr(1);

                if(symbol_table.find(symbol) == symbol_table.end()) {
                    symbol_table[symbol] = ram_index;
                    ram_index++;
                }
                
                binLine = getBinary(symbol_table[symbol]);
            }

            binLine[0] = '0';
            outputFile << binLine << endl;
        }
        else {
            outputFile << "111";

            int equalsIndex = line.find('=');
            int semicolon = line.find(';');

            if(equalsIndex != string::npos && semicolon != string::npos) {

                string operation = line.substr(equalsIndex+1, semicolon-equalsIndex);
                outputFile << op_codes[operation];

                string target_reg = line.substr(0, equalsIndex);
                outputFile << dest[target_reg];

                string jump_loc = line.substr(semicolon+1);
                outputFile << jump[jump_loc] << endl;

            }
            else if(equalsIndex != string::npos) {
                string operation = line.substr(equalsIndex+1);
                outputFile << op_codes[operation];

                string target_reg = line.substr(0, equalsIndex);
                outputFile << dest[target_reg];

                outputFile << jump["null"] << endl;

            }
            else if(semicolon != string::npos) {
                string operation = line.substr(0, semicolon);
                outputFile << op_codes[operation];

                outputFile << dest["null"];

                string jump_loc = line.substr(semicolon+1);
                outputFile << jump[jump_loc] << endl;
            }
        }
    }
}

int main(int argc, char *argv[]) {

    cout << getBinary(125) << endl;

    if(argc <= 1) {
        cout << "Missing file path." << endl;
        return 0;
    }

    ifstream assemblyFile;
    assemblyFile.open(argv[1]);

    unordered_map<string, int> symbol_table = initializeSymbols();

    cout << "\n\nStart first pass!\n\n";
    firstPass(assemblyFile, symbol_table);

    for(auto const &pair : symbol_table) {
        cout << pair.first << ": " << pair.second << endl;
    }
    
    assemblyFile.close();

    cout << "\n\nEnd first pass!\n\n";


    assemblyFile.open(argv[1]);
    ofstream outputFile;
    outputFile.open("compiled.txt");

    cout << "\n\nStart building machine code!\n\n";

    buildMachineCode(assemblyFile, outputFile, symbol_table);

    cout << "\n\nDone building machine code!\n\n";

    assemblyFile.close();
    outputFile.close();

    for(auto const &pair : symbol_table) {
        cout << pair.first << ": " << pair.second << endl;
    }

    return 0;
}