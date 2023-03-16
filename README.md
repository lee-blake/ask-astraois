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



## Building and Running
### Building

### Running
#### First-Time Use

### Warnings
As part of this project, some warnings have been suppressed globally:
- "unused": This inspection has been suppressed whenever there is one of the following annotations, as PicoCLI is 
- responsible for injecting them at runtime:
  - `@picocli.CommandLine.Parameters`
  - `@picocli.CommandLine.Option`
### Troubleshooting
#### Unicode
If the program builds or runs with warnings or errors about character mappings, or if the degree character does not
render correctly in the help menus or 'view' subcommand, check whether your system or IntelliJ are using a non-UTF-8 
encoding. Furthermore, consider replacing the degree character in the code with its escape sequence `\u00b0` - this
should enable it to run correctly in IntelliJ and if display issues persist, it is likely caused by system
configuration.

