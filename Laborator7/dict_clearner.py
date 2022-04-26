import sys
file = "en_US-custom.dic"

with open(file, "r") as input:
    with open(file + ".clean", "w") as output:
        for line in input.readlines():
            word = line.strip().split('/')[0]
            if(word[0].isdigit()):
                continue; 
            output.write(word.lower())
            output.write("\n")
