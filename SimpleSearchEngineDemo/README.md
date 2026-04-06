# simpleSearchEngineDemo

The program should be able to use such searching strategies as ALL, ANY, and NONE.

Take, for example, these six sample lines:

Dwight Joseph djo@gmail.com
Rene Webb webb@gmail.com
Katie Jacobs
Erick Harrington harrington@gmail.com
Myrtle Medina
Erick Burgess

If the strategy is ALL, the program should print lines containing all the words from the query.
Query:

Harrington Erick

Result:

Erick Harrington harrington@gmail.com


If the strategy is ANY, the program should print the lines containing at least one word from the query.
Query:

Erick Dwight webb@gmail.com

Result:

Erick Harrington harrington@gmail.com
Erick Burgess
Dwight Joseph djo@gmail.com
Rene Webb webb@gmail.com


If the strategy is NONE, the program should print lines that do not contain words from the query at all:
Query:

djo@gmail.com ERICK

Result:

Katie Jacobs
Myrtle Medina
Rene Webb webb@gmail.com

All listed operations are implemented in the inverted index. The results should not contain duplicates.
