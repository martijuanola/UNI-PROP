#include <iostream>
#include <fstream>
#include <string>

using namespace std;

int main() {
    ifstream e("items.txt");
    ofstream s("strings.txt");

    string c;
    while(getline(e, c)) {
        s << "c = new ArrayList<>(Arrays.asList(\"";
        
        int i = 0;
        while (i < c.size()) {
            if (c[i] == ',') {
                if (i < c.size()-1 and i > 0 and (c[i-1] == ' ' or c[i+1] == ' ')) s << c[i];
                else if (i == c.size()-1)s << "\"" << c[i] << "\"\"";
                else s << "\"" << c[i] << "\"";
            }
            else {
                if (c[i] == '\'' or c[i] == '\"') s << "\\";
                s << c[i];
            }
            ++i;
        }
        long pos = s.tellp();
        s.seekp(pos - 1);
        s << "\"));" << endl;
        s << "s.add(c);" << endl;
    }
    e.close();
    s.close();
}
