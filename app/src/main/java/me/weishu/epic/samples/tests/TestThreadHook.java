package me.weishu.epic.samples.tests;

import android.util.Log;

import de.robv.android.xposed.DexposedBridge;
import de.robv.android.xposed.XC_MethodHook;

/***
 * java_lang_Thread.cc
 * java_lang_Thread.h
 */
public class TestThreadHook {
    private static final String TAG = "------TestThreadHook";

    public static void hookAllConstructorsAndHookRun() {
        DexposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Thread thread = (Thread) param.thisObject;
                Class<?> clazz = thread.getClass();
                if (clazz != Thread.class) {//线程子类
                    Log.d(TAG, "found class extend Thread:" + clazz);
                    DexposedBridge.findAndHookMethod(clazz, "run", new ThreadMethodHook());
                }
                Log.i(TAG, "Thread: " + thread.getName() + " class:" + thread.getClass() + " is created.");
                onCreateThread(param);
            }
        });
        Log.w(TAG, "hookAllConstructors: ");
        DexposedBridge.findAndHookMethod(Thread.class, "run", new ThreadMethodHook());
        Log.w(TAG, "hookRun: ");

    }

    private static void onCreateThread(XC_MethodHook.MethodHookParam param) {
        Log.e(TAG, "追踪打印堆栈---onCreateThread: thread=" + param.thisObject + ",stack=" + Log.getStackTraceString(new Exception("test---onCreateThread---stack")));
    }


    static class ThreadMethodHook extends XC_MethodHook {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            Thread t = (Thread) param.thisObject;
            Log.i(TAG, "thread:" + t + ", started..");
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            Thread t = (Thread) param.thisObject;
            Log.i(TAG, "thread:" + t + ", exit..");
        }
    }

    public static void testThreadInvoke() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "run: thread 正常执行");
//            }
//        }, "test-new-thread-类").start();

        new Thread("test-new-thread—子类") {
            @Override
            public void run() {
                super.run();
                Log.d(TAG, "run: thread 子类 正常执行");
            }
        }.start();
    }

}
