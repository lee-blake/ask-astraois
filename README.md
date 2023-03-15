Nick Hively <nrhively@bsu.edu>
Justyn Fox <jjfox@bsu.edu> 
Lee Blake

# Building and Running
## Troubleshooting
### Unicode
If the program builds or runs with warnings or errors about character mappings, or if the degree character does not
render correctly in the help menus or 'view' subcommand, check whether your system or IntelliJ are using a non-UTF-8 
encoding. Furthermore, consider replacing the degree character in the code with its escape sequence `\u00b0` - this
should enable it to run correctly in IntelliJ and if display issues persist, it is likely caused by system
configuration.

# Warnings
As part of this project, some warnings have been suppressed globally:
- "unused": This inspection has been suppressed whenever there is one of the following annotations, as PicoCLI is responsible for injecting them at runtime:
    - `@picocli.CommandLine.Parameters`
    - `@picocli.CommandLine.Option`