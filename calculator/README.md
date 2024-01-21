## Program structure

The program is made of these classes:

- `Calculator`: This is the driver and main class of the program, it handles the menu loop
- `Associativity`: This is an enum that represents the associativities that operators can have (not fully used)
- `DegreesMode`: This is an enum for whether the calculator uses degrees or radians
- `ExpressionParser`: This class implements a crude (but functional) parser which converts a string into
  tokens which can be evaluated
- `MainMenuOptions`: This enum has the options for the main menu of the calculator
- `MathFunction`: This enum contains mathematical functions, such as trigonometry and square roots
- `Operator`: This enum contains arithmetic and other operators
- `ReversePolishNotation`: This class implements the [shunting yard algorithm](https://en.wikipedia.org/wiki/Shunting_yard_algorithm),
  and a [reverse Polish notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation) evaluator
- `Token`: This class represents a token such as a number, function, operator, or parenthesis, and stores the
  type as well as data for it (if any is needed)
- `TokenType`: This enum has the types a token can be
- `Util`: This implements the `displayMenu` function, which makes a menu for the user from an enum
  and functions for reading numbers from standard input

The `MathFunction` and `Operator` classes each take two lambda functions in their constructors, one
to read their inputs from standard input, and one to perform their calculation. These are accessible
through the `getInputs` and `execute` functions.
