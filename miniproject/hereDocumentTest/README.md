### java 实现 here document
Java 应用 APT（Annotation Processing Tool，JDK 1.5 引入，JDK1.6 后可操作性更强，可以使用 javac 带上 -processor 参数）实现 Here Document，
建立两个项目：HereDocument、HereDocumentTest，前者是实现，后者是对它的测试，
必须分成两个项目，因为编译后者的时候，前者的 Class 文件必须先存在。

Here Document 的这个实现用起来不方便。
1. 比如依赖的注解要求字符串必须是一个成员变量，不能用在任意地方，比如函数内
2. 在Eclipse下不能够格式代码(Ctrl+Shift+F), 会破坏注释的内容(要避免需用@formatter:on/off，很麻烦)
3. 编译的时候也需要设置processor
4. 如processor没有正常工作，容易出现空字符串, 而且还难以发现问题。

Java 原生不支持，其他变着法来弄的都优雅不起来。我现在对 Java 的 Here Doc 没有什么追求，因为可以在项目中使用 Scala。

hereDocument 项目的 pom 文件

[here](https://unmi.cc/java-implement-here-document/)