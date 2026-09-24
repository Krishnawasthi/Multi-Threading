# Sleep() vs wait() :
## Sleep()
### sleep will go to waiting state but holding the lock, when the sleep time will over it directly start executing again. 
## wait()
### wait will release the object lock and will go the waiting state when time will be over it directly can not start executing, when it releases the lock other thread will acquire the lock so it needs to wait until the other thread releases the lock, By using wait() thread will be in the runnable state when the time gets over. It(wait) must be called from Synchronize method.

<img width="1002" height="262" alt="image" src="https://github.com/user-attachments/assets/c3386858-4a12-4c0c-a55b-47294d1b9b43" />

<img width="1102" height="323" alt="image" src="https://github.com/user-attachments/assets/29090b27-9195-46a1-bf6d-6e4f20555eb7" />

# Thread.yield()
<img width="1088" height="250" alt="image" src="https://github.com/user-attachments/assets/7fff3967-3923-4a26-b0d8-d971bae3f182" />

`Thread.yield()` is a method provided by the `Thread` class in Java. It is used when the currently running thread wants to give other threads a chance to execute.

### What does `yield()` mean?

Suppose we have two threads:

* **Thread 1 (T1)** is currently running.
* **Thread 2 (T2)** is ready to run.

When T1 calls:

```java
Thread.yield();
```

T1 tells the thread scheduler:

> "I have done some work. If you want, you can give another thread a chance to run."

The scheduler may allow T2 to run, but it is **not guaranteed**.

### Important Concept

`yield()` is only a **request or hint** to the JVM's thread scheduler. The scheduler decides which thread will actually run.

So, calling:

```java
Thread.yield();
```

does **not mean** that the current thread will definitely stop running.

### State of the Thread

After calling `yield()`, the thread does **not** go into the `WAITING` or `BLOCKED` state.

It remains in the:

**RUNNABLE state**

This means the thread is still ready to run and can be selected again by the scheduler.

### Does `yield()` release the lock?

**No.**

If a thread owns a synchronized lock and calls `yield()`, it does not release that lock.

```java
synchronized void method() {
    Thread.yield();
}
```

The thread still owns the monitor/lock.

### Is `yield()` guaranteed?

**No.**

The following can happen:

```text
T1 running
   ↓
T1 calls yield()
   ↓
Scheduler decides to run T1 again
   ↓
T1 continues
```

Or:

```text
T1 running
   ↓
T1 calls yield()
   ↓
Scheduler gives T2 a chance
   ↓
T2 runs
```

Therefore, the output of a program using `yield()` can be different each time.

### Key Points

* `yield()` is a **static method** of the `Thread` class.
* Syntax: `Thread.yield();`
* It gives a **hint** to the thread scheduler.
* It may allow another runnable thread to execute.
* It does **not guarantee** a context switch.
* The current thread remains in the **RUNNABLE** state.
* It does **not release locks**.
* It does not cause the thread to sleep.
* It does not guarantee any particular execution order.
* It is generally useful for **testing and experimenting with thread scheduling**, rather than for implementing synchronization.

### Remember

**`yield()` = "I am willing to give another thread a chance, but the scheduler decides."**

