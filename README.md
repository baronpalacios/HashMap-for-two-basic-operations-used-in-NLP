# HashMap-for-two-basic-operations-used-in-NLP
HashMap for two basic operations used in NLP
you will implement two different HashMap classes to perform basic Natural Language Processing
operations. Specifically, you will read a text dataset consisting of multiple input files and keep the words in the Word
HashMap. The key for the word hashmap will be the words and the value will refer to another hashmap (file hashmap)
which keeps the occurrences of the word in different files. The key for the file hashmap is the filename and the value is
an arraylist containing the word positions in that file. In order to be able to traverse the word map in an efficient way, each
entry should keep a pointer to the next inserted entry, allowing the structure to be Iterable (implements Iterable interface).
By this way, the methods such as containsValue(), containsKey() and keySet() can be implemented in a more efficient
way, without the need for visiting empty cells in the table. In Figure 1 a sample snapshot of the hashmaps is presented.

