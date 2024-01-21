## Program structure

The program is made of these classes:

- [`AstPrinter`](AstPrinter.java): This class implements a visitor for expressions that prints them out
- [`Calculator`](Calculator.java): This is the driver and main class of the program, it handles the menu loop
- [`DegreesMode`](DegreesMode.java): This is an enum for whether the calculator uses degrees or radians
- [`Evaluator`](Evaluator.java): This is class implements a visitor which evaluates expressions
- [`MainMenuOptions`](MainMenuOptions.java): This enum has the options for the main menu of the calculator
- [`MathFunction`](MathFunction.java): This enum contains mathematical functions, such as trigonometry and square roots
- [`Operator`](Operator.java): This enum contains arithmetic and other operators
- [`Parser`](Parser.java): This class implements a recursive descent parser that turns expressions into abstract syntax trees,
  which can be evaluated
- [`Token`](Token.java): This class represents a token such as a number, function, operator, or parenthesis, and stores the
  type as well as data for it (if any is needed)
- [`TokenScanner`](TokenScanner.java): This class implements a parser which converts a string into
  tokens which can be turned into an abstract syntax tree
- [`TokenType`](TokenType.java): This enum has the types a token can be
- [`Util`](Util.java): This implements the `displayMenu` function, which makes a menu for the user from an enum
  and functions for reading numbers from standard input

`Parser`, `TokenScanner`, and `tool.AstGenerator` are based on a [tutorial I found](https://craftinginterpreters.com/contents.html).
I tried to implement similar functionality myself using a very bad tokenizer and the shunting yard algorithm,
but I was only partially successful. I wanted my calculator to not be fragile, and the code I wrote just wasn't
working on some expressions. I do mostly understand the code from the tutorial, and I didn't directly copy and paste
from it (although some functions I typed out unchanged).

The `MathFunction` and `Operator` classes each take two lambda functions in their constructors, one
to read their inputs from standard input, and one to perform their calculation. These are accessible
through the `getInputs` and `execute` functions.

Additionally, the [`tool.AstGenerator`](tool/AstGenerator.java) class implements a separate program for generating
the classes used in the abstract syntax tree. The AST uses the visitor pattern, where you have an interface that
has a visit method for each type, and an accept method that calls the right one for an object. New operations
are defined by making a class that implements the interface. This makes it more convenient to do operations
on expressions.
