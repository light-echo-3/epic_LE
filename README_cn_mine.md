
## 测试hook线程
打印堆栈，可用于检测java层线程乱new问题

##[实现原理-我为Dexposed续一秒——论ART上运行时 Method AOP实现](https://weishu.me/2017/11/23/dexposed-on-art/)

## 解释：callee side dynamic rewriting （调用测动态重写）
其实是inline hook的方案，使用trampoline实现。  
详见文章：[Android inline hook 浅析](https://www.sunmoonblog.com/2019/07/15/inline-hook-basic/)  
区别于xHook（Android PLT hook），详见：[Android PLT hook 概述](https://github.com/light-echo-3/xHook_LE/blob/master/docs/overview/android_plt_hook_overview.zh-CN.md)  


## hook线程方案
没有必要epic这样检测线程。
~~直接xHook art.so中 java.lang.Thread#nativeCreate 对应的函数即可。~~
[java_lang_Thread.cc]  
art::Thread_nativeCreate
[java_lang_Thread.h]
```agsl
上述方案行不通，原因：
xHook无法hook本so中函数。
方案1：如果用xHook，可以hook"pthread_create",
然后通过堆栈，判断是否是“art::Thread_nativeCreate”调用过来的，从而确定是否是java层create线程。。。
方案2：直接修改Android系统 & 编译Android系统

```



## TODO
trampoline实现细节，可参考[inline hook实现](https://github.com/light-echo-3/Android-Inline-Hook_LE)