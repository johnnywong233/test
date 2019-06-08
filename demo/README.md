### Heap
[Heap](http://www.cnblogs.com/jwongo/p/datastructure-heap.html), 堆，是一种特殊的树形数据结构，它满足所有堆的特性：父节点的值大于等于子节点的值(max heap)，或者小于等于子节点的值(min heap)。
对于大多数应用来说，Java堆是JVM所管理的内存中最大的一块。Java堆是被所有线程共享的一块内存区域，在虚拟机启动时创建。此内存区域的唯一目的就是存放对象实例，
几乎所有的对象实例都在这里分配内存。根据JVM规范的规定，Java堆可以处于物理上不连续的内存空间中，只要逻辑上是连续的即可，就像磁盘空间一样。
如果在堆中没有内存完成实例分配，并且堆也无法再扩展时，将会抛出OutOfMemoryError异常。
不像其他的树形结构，例如二叉查找树，采用链表的形式实现，Heap一般用数组实现。这种数组采用自上至下，自左至右的形式从树中添加元素。
仅用一个数组是不足以表示一个堆，程序在运行时的操作可能会超过数组的大小。因此需要一个更加动态的数据结构，满足以下特性：
- 1.可以指定数组的初始化大小。
- 2.这种数据结构封装了自增算法，当程序需要时，能够增加数组的大小以满足需求。
ArrayList的实现，正是采用这种数据结构。本文就采用了ArrayList的自增算法。
因为使用数组，需要知道如何计算指定节点(index)的父节点、左孩子和右孩子的索引。
```
parent index : (index - 1) / 2
left child : 2 * index + 1
right child : 2 * index + 2
```