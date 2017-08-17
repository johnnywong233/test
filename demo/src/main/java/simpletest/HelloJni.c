#include <stdio.h>  
#include "HelloJni.h"  

/*该方法在HelloJni.h中声明
  JNIEXPORT和JNICALL都是JNI中的关键字
  JNIEnv对应java线程中调用的JNI环境，通过这个参数可以调用一些JNI函数
  jobject对应当前java线程中调用本地方法的对象
*/
JNIEXPORT void JNICALL Java_HelloJni_sayHello
  (JNIEnv * env, jobject obj)
{
    printf("HelloJni! This is my first jni call.");
}