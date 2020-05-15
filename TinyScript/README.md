# SeeleNote

🍧\
🍮\
🍒

这个本质上是编译原理的内容

词法分析的状态机，各个语言都提供正则表达式，从源代码中提取字符串容易，但是词法分析过程要提取大量字符串,需要一个预读的字符,不然下一个状态无法分析(因为是对流操作)。而在提取字符串的时候，是要看两个引号之间的内容，即读取到引号，对自动机而言不管是或者不是都是直接会读下一个字符串的，因此不需要像之前提取变量或关键字的时候需要提前看一个字符(即peek操作,看代码更直观,其实就是一个临界判断),而在3中也是同理的需要peek，但是相对的因为状态过多，如果使用peek的话有很多地方需要补上next，然而并不是所有地方需要写next，因此单独写的话处理起来又麻烦，因此不如使用next+putback。

1.用状态机提取词语(关键字/变量名/操作符/数字)
![状态机描述](https://github.com/JoyTsing/SeeleNote/blob/master/TinyScript/pic/1.png)

2.提取字符串
![状态机描述](https://github.com/JoyTsing/SeeleNote/blob/master/TinyScript/pic/2.png)

3.提取操作符
![状态机描述](https://github.com/JoyTsing/SeeleNote/blob/master/TinyScript/pic/3.svg)

4.提取数字

感觉状态机可以再化简一下
![状态机描述](https://github.com/JoyTsing/SeeleNote/blob/master/TinyScript/pic/4.svg)

词法分析器

![词法分析器](https://github.com/JoyTsing/SeeleNote/blob/master/TinyScript/pic/5.png)

JS版本先暂时鸽着(指还不会JS)，先完成JAVA版本

先上两个轮子哥的相关资料(赶上再看)

[如何设计一门语言](https://www.cnblogs.com/geniusvczh/category/152533.html)

[跟vczh看实例学编译原理](https://www.cnblogs.com/geniusvczh/category/551932.html)
