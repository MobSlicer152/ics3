## Program structure

The program is made of these classes:

- [`Calculator`](Calculator.java): This is the driver and main class of the program, it handles the menu loop
- [`Associativity`](Associativity.java): This is an enum that represents the associativities that operators can have (not fully used)
- [`DegreesMode`](DegreesMode.java): This is an enum for whether the calculator uses degrees or radians
- [`MainMenuOptions`](MainMenuOptions.java): This enum has the options for the main menu of the calculator
- [`MathFunction`](MathFunction.java): This enum contains mathematical functions, such as trigonometry and square roots
- [`Operator`](Operator.java): This enum contains arithmetic and other operators
- [`Token`](Token.java): This class represents a token such as a number, function, operator, or parenthesis, and stores the
  type as well as data for it (if any is needed)
- [`TokenScanner`](TokenScanner.java): This class implements a parser which converts a string into
  tokens which can be evaluated
- [`TokenType`](TokenType.java): This enum has the types a token can be
- [`Util`](Util.java): This implements the `displayMenu` function, which makes a menu for the user from an enum
  and functions for reading numbers from standard input

The `MathFunction` and `Operator` classes each take two lambda functions in their constructors, one
to read their inputs from standard input, and one to perform their calculation. These are accessible
through the `getInputs` and `execute` functions.

Additionally, the [`tool.AstGenerator`](tool/AstGenerator.java) class implements a separate program for generating
the classes used in the abstract syntax tree.
