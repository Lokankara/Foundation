[![Validator (equations)](https://github.com/Lokankara/foundation/actions/workflows/maven.yml/badge.svg)](https://github.com/Lokankara/foundation/actions/workflows/maven.yml)

Develop an application that will help a math teacher.
The application should provide the following features:

1. Enter mathematical equations that contain numbers (whole or decimal
   fractions), as well as the mathematical operations +, -, *, / and parentheses, the level of
   nesting of parentheses is arbitrary. In all equations, the unknown quantity
   is denoted by the English letter x.
2. Check the entered equation for the correct placement of brackets
3. Check the correctness of the entered expression (there should not be 2 signs of
   mathematical operations in a row, for example, the expression 3+*4 is not allowed,
   while the expression 4*-7 is acceptable).
   Examples of correct equations: `2*x+5=17` , `-1.3*5/x=1.2`
4. If the equation is correct, save it to the database.
5. Provide the ability to enter the roots of the equation, checking during the input,
   whether the given number is a root, and if so, save it to the database.
6. Implement the functions of searching for equations in the database by their roots.
   For example, a possible query: find all equations that have one specified roots or find all equations that have
   exactly one root.

##### TODOs:

6. Реалізувати функції пошуку рівнянь у БД за їхніми коренями.
   Наприклад, можливий запит: знайти всі рівняння, що мають один із
   зазначених коренів або знайти всі рівняння, які мають рівно один корінь.