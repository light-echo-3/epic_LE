
## 测试hook线程
打印堆栈，可用于检测java层线程乱new问题

##[实现原理-我为Dexposed续一秒——论ART上运行时 Method AOP实现](https://weishu.me/2017/11/23/dexposed-on-art/)

进度：
dynamic callee-side rewriting

## hook线程方案
没有必要epic这样检测线程。
直接xHook art.so中 java.lang.Thread#nativeCreate 对应的函数即可。
[java_lang_Thread.cc]  
art::Thread_nativeCreate
[java_lang_Thread.h]