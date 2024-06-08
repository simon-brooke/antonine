# Introduction to antonine

A calculator to aid military quantity surveyors

## Brief

Right, Troops!

You're appointed to the engineering staff for the project of building the Antonine wall; specifically you will be quantity surveyors. Unfortunately, it is CXLII AD, and Arabic numerals have not yet been invented.

In order to do your work, you will need a calculator. The calculator must accept input in Roman numerals, and produce output in Roman numerals.

The wall is to be XXXXII Roman miles long, and will be protected by XVI towers. You'll be relieved to know that the wall is to be build mainly of turf, which you can cut locally, and the walls of the towers can be built with local fieldstone. But each tower will need LXIV masoned corner stones.

How many corner stones do you need altogether?

You have MMMD roof tiles. How many roof tiles can you allocate to each tower? If each tower needs DC roof tiles, how many towers will you have to thatch?

To write your calculator you may use any language you like (Latin is not mandatory). However, if your language already has libraries to parse and print Roman numerals, you may not use those libraries (you may use any other libraries; you may specifically use parser generators).  You should allow addition, subtraction, multiplication and division, but you aren't required to handle fractions. You may expect well formed input (i.e. the input will be valid Roman numerals), but for extra credit you could detect and reject ill formed input. User interface is optional.

There are solutions to katas very similar to this one on the 'net. Please don't search for these before the session - it will spoil the fun. Looking up the rules for Roman numerals on Wikipedia or other sites is completely legitimate, provided you don't look at code.

## Usage

Substantially rewritten for version 0.2.0, Antonine now has an interactive mode. You can download it as a jar file from here, and invoke the jar file:

```bash
java -jar antonine-0.2.0-standalone.jar
```

The following command line options are recognised:

```
  -b, --banner BANNER    ANTONIVS ORNARE | SIMON RIVVLVS HOC FECIT | MMXXIV  The welcome banner to display
  -p, --prompt PROMPT    COMPVTARE |                                         The prompt to display
  -s, --stop-word WORD   FINIS                                               The stop word to use at the end of a session
  -v, --verbosity LEVEL  0                                                   Verbosity level
```

An expression to evaluate may be passed as command line arguments, in which case options are ignored, that expression is evaluated, the result printed, and the program exits with an exit status of zero:

```
simon@mason:~/workspace/antonine$ java -jar target/antonine-0.2.0-standalone.jar MMMD / XVI
CCXVIII
```

Otherwise, Antonine will print its banner and enter a read-eval-print loop, exiting with a status of 0 when

1. The `stop-word`, by default `FINIS`, is entered as an input line; or
2. A blank input line is entered.

Thus:

```
simon@mason:~/workspace/antonine$ java -jar target/antonine-0.2.0-standalone.jar
ANTONIVS ORNARE | SIMON RIVVLVS HOC FECIT | MMXXIV

COMPVTARE |  MMMD / XIV
CCL
COMPVTARE |  iii + iv
VII
COMPVTARE |  iii + iv * v
XXIII
COMPVTARE |  IIII + IV
VIII
COMPVTARE |  FINIS

VALE
simon@mason:~/workspace/antonine$ 
```

## License

Copyright © MMXV-MMXXIV Simon Brooke (simon@journeyman.cc)

Distributed under the GNU General Public License either version 2.0 or (at
your option) any later version.
