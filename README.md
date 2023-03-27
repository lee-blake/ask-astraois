# Ask Astraios
Ask Astraios is a command-line utility for amateur astronomers. Version 0.1.0 can maintain a journal of astronomcal
objects to observe, with their names, RA/Dec coordinates, and date of completion. The journal is saved in CSV format
and can be read by other programs; however, editing with other programs is not recommended at the present time as 
many common CSV-editing programs do not use the same formatting as our backend.

## Authors
Nick Hively <nrhively@bsu.edu>  
Justyn Fox <jjfox@bsu.edu>  
Lee Blake

## Dependencies
- Java 17+
- Gradle 7.5.1+
- Gradle-managed dependencies
    - PicoCLI 4.7.1+
    - Commons CSV 1.10.0+
    - Jansi 2.4+

## Building, Installing, and Running
### Building
To build the project, complete the following steps:
- Clone the project from GitHub into IntelliJ 
- Suppress the warnings detailed below
- Run the following Gradle tasks in order to ensure the distribution is correct:
  1. `clean` (only if not the first time building)
  2. `build`
  3. `startScripts`
  4. `distZip`

A .zip file will be produced in 'build/distributions' and is needed for installation.

### Installing
1. Extract the .zip file at a known location.
2. Navigate to the extracted folder.
3. On the same level as `bin` and `lib`, create a new folder called `data`. This is where the journal is saved.
4. Verify installation by running the program.

### Running
Open a new terminal and navigate to the folder that was extracted. The output of `ls` or `dir` should list `bin`, 
`lib`, and `data`. It is important that you execute the program from this directory or the journal will not save to
the correct location. Run the program by either `./bin/ask-astraios` (Linux) or `.\bin\ask-astraios.bat` (Windows). A
help menu with details on the various subcommands should display in the terminal.

#### First-Time Use
The journal file does not exist initially. There is no need to create it manually - adding the first object to it will
create the file. If this creation fails, verify that the program can write to the `data` directory.

### Project Warnings
As part of this project, some warnings have been suppressed globally:
- "unused" and "not initialized": This inspection has been suppressed whenever there is one of the following
  annotations, as PicoCLI is responsible for injecting them at runtime:
  - `@picocli.CommandLine.Parameters`
  - `@picocli.CommandLine.Option`

### Troubleshooting
#### Unicode
If the program builds or runs with warnings or errors about character mappings, or if the degree character does not
render correctly in the help menus or 'view' subcommand, check whether your system or IntelliJ are using a non-UTF-8 
encoding. Furthermore, consider replacing the degree character in the code with its escape sequence `\u00b0` - this
should enable it to run correctly in IntelliJ and if display issues persist, it is likely caused by system
configuration.
#### Class Versions
If the program builds but returns an error of the form "Main has been compiled by a more recent version of the 
Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 
<some number here>", this is not a problem with the project. This error occurs when Java attempts to run a program
that was compiled with a later version of the JDK than the version of the JVM that is attempting to run the program. 
To resolve this error, verify your `PATH` environment variable points to the correct version of Java, and if need be,
install a version of Java at least as late as the one used in IntelliJ to build the project.


