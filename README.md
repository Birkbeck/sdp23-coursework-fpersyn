# Simple (Small) Machine Language (SML)

### A coursework examining reflection and dependency injection

+ This assignment is to be completed **individually**.
+ The sample code mentioned in the text can be found in this repository.

<img src="sml.jpg" alt="sml"/>

The aim of this assignment is to give you practice with 

+ subclasses, 
+ modifying existing code, 
+ testing,
+ dependency injection,
+ and the use of reflection,

amongst other skills.

## Details

**Name**: Your name should appear here

**Your userid**: e.g., myname01

Please do not forget to complete this part as too often we have "orphaned" repositories,
which can require months to sort out.

## The problem

In this assignment you will write an interpreter for a simple machine language — `SML`. 

The general form of a machine language instruction is:

```
	[label:] opcode parameter-list
```
where

+ `label` - is the optional label for the line. It is a sequence of non-whitespace characters.  
	Other instructions might “jump” to that label.
+ `opcode` - is the actual instruction name (operation code).
	In `SML`, there are instructions for adding, multiplying and so on, for storing integers, 
    and for conditionally branching to other labels  (like an `if` statement).
+ `parameter-list` - is the list of parameters for the instruction. Parameters can be 
    integer numbers, labels or registers that the instruction manipulates.
	Registers are simple storage areas in computer memory, much like variables. 
	In SML, there are 8 registers, named
    `EAX`, `EBX`, `ECX`, `EDX`, `ESP`, `EBP`, `ESI` and `EDI`.

SML has the following types of instructions:

| Instruction | Interpretation                                                                                                                  |
|-----------|---------------------------------------------------------------------------------------------------------------------------------|
| `add r s` | Add the contents of registers `r` and `s` and store the result in register `r`                                                  |
| `sub r s` | Subtract the contents of register `s` from the contents of `r` and store  the result in register `r`                            |
| `mul r s` | Multiply the contents of registers `r` and `s` and store the result in register `r`                                             |
| `div r s` | Divide (Java integer division) the contents of register `r` by the contents of register `s` and store the result in register `r` |
| `out s`   | Print the contents of register `s` on the console                                                                               |
| `mov r x` | Store integer `x` in register `r`                                                                                               |
| `jnz s L` | If the contents of register `s` is not zero, then make the statement labeled `L` the next statement to execute                  |

where

+ `L` is a label. No two instructions can have the same label.
+ Each of `s` and `r` is register name.
	

Here is an example of an SML program to compute the factorial of 6:

```
    mov EAX 6
    mov EBX 1
    mov ECX 1
f3: mul EBX EAX
    sub EAX ECX
    jnz EAX f3
    out EBX
```
Note that labels end with a colon (but the colon is not part label's name),
and that the adjacent fields of an instruction 
(label, opcode, and operands) are separated by whitespace.

The instructions of a program are executed in order (starting with the first one), 
unless the order is changed by execution of a `jnz` instruction. 
Execution of a program terminates when its last instruction has been executed 
(and provided that the instruction does not change the order of execution).

Your interpreter will:

1. Read the name of a file that contains the program from the command line 
(via `String[] args` and the `Main` class).
2. Read the program from the file and translate it into the internal form.
3. Print the program out.
4. Execute the program. 
5. Print the final values of the registers.

This looks like a tall order, but have no fear; 
we provide you with some of the code, so you can concentrate on the interesting 
use of subclasses, dependency injection and reflection. 

Completing the worksheets really helps as preparation for this assignment.

## Design of the program

We provide some of the classes, specifications for a few, and leave a few others 
for you to write/complete. The code we provide does some of the dirty work of reading 
in a program and translating it to an internal form;  you can concentrate on the 
code that executes the program. 

We suggest that you examine the `Machine` class first, as it is the heart 
of the program (you can use the `main` method in the `Main` class to guide you as well).

## Studying the program

You are provided with some skeleton code which is on this repository.

Look at the fields of the `Machine` class, which contain exactly what is 
needed to execute an SML program:

+ the *labels* defined in the program,
+ the program itself in an *internal form*,
+ the *registers* of the machine, and
+ the *program counter* — the number of the next instruction to execute (an index in 
the *program* list).

Array-like structures are used for the program because there is no limit 
to the size of an SML program. A map is used for the *registers* and *labels* because 
they are accessed by their names.

Next examine the method `Machine.execute`, which executes the program. 
It is a typical *fetch-decode-execute* cycle that all machines have in some form. 

At each iteration, the instruction to execute is fetched, the program counter 
is incremented, and the instruction is executed. Some instructions (e.g., `jnz`) 
can change the program counter too.

The `Translator` class contains the methods that read in the program and translate 
it into an internal form; be warned, very little error checking goes on here. 
For example, there is no checking for duplicate label definitions, 
for the use of a label name that doesn't exist.

Finally, study the `main` method of the `Main` class (if you think it will help you).

## The `Instruction` class and its subclasses

All the programming that you do has to do with the `Instruction` class and it's subclasses. 
The specification of the class `Instruction` has been given to you — open the file

```
	Instruction.java
```
and examine it. This class is *abstract*, because it should not be instantiated.  
The method `execute` is also abstract, forcing every subclass to implement it. 
Every instruction has an optional *label* and an *operation* — that is exactly 
what is common to every instruction.  Therefore, these properties are maintained 
in the base class of all instructions.

## Tasks

There are two components to this coursework assignment.

##### Part I

1. Complete the methods in the `Instruction` class — this may require you to add some fields,  
   which should be *protected*, so that they are accessible in any subclasses.

2. Now create a subclass of `Instruction` for each kind of SML instruction and fix 
   the method `Translator.instruction` so that it properly translates that kind of instruction.
   *Recommended*: write one instruction at a time and test it out thoroughly, before proceeding to the next!

3. Start with the `add` instruction, because the code for translating it is already there — 
   in method `Translator.instruction`.  Initially, the program will not compile because there is no class 
   for the instruction `add`; once that class is written, the program will compile.

4. For each instruction, the subclass needs appropriate fields, a constructor, 
   `toString` method, and a method `execute`; `toString` and `execute` should override 
   the same methods in the `Instruction` class using appropriate annotations.

5. As you do this, you will see that each successive class can be written by 
   duplicating a previous one and modifying it (obviously avoiding too much repeated code).

6. After you finish writing a subclass for an SML instruction (except for `add`), 
   you will have to add code to the method `Translator.instruction` to translate 
   that instruction. The existing code for translating `add` should help you with this.

7. There are also a few places in the code with TODO: labels - follow the instructions to
   improve the provided code (or implement missing methods as required). 
   Use the Java Stream API whenever possible instead of loops.

##### Part II

1. Next, take the `switch` statement that decides which type of instruction is created 
   and modify the code so that it uses *reflection* to create the instances, i.e., 
   remove the explicit calls to the subclasses and the `switch` statement. 
   This will allow the SML language to be extended without having to modify the original code.

2. Modify the source code to use *dependency injection*, the *singleton* design pattern, 
   and *factory* classes, where you deem appropriate. (You are allowed to use other 
   design patterns if you consider them necessary.)

3. Apart from the specific code mentioned above you should not modify other classes.

All of these parts of the coursework should be fully tested (you do not need to provide 
tests for the original codebase).

## Submission

Your repository will be *cloned* at the appropriate due date and time.

------

###### Individual Coursework 2022-23
