# Race Condition and Synchronization in Java

## 📌 Overview

<img width="1100" height="309" alt="image" src="https://github.com/user-attachments/assets/283b946a-536c-47bf-a367-08b3688449c1" />


When multiple threads access and modify the same shared resource at the same time, a **race condition** can occur.

Synchronization is used to make sure that only one thread can access the critical section at a time.

## ⚠️ Race Condition

Without synchronization, multiple threads may update the same variable simultaneously, which can produce an incorrect result.

Example:

```java
class Counter {
    int count = 0;

    void increment() {
        count++;
    }
}
```

If multiple threads call `increment()` at the same time, the final value may not be what we expect.

## 🔒 Using Synchronization

The `synchronized` keyword allows only one thread at a time to execute the synchronized method.

```java
class Counter {
    int count = 0;

    synchronized void increment() {
        count++;
    }
}
```
<img width="1540" height="712" alt="image" src="https://github.com/user-attachments/assets/24ebd135-c34c-4bb0-b07c-56d3649f1f48" />

## Every object will have one object lock.

## If any method called on the particular object had required that object lock to enter into synchronized method. While a particular thread is executing.     That same object will not have any other lock, so other thread cannot acquire it, they will be waiting outside.

Now, when multiple threads access `increment()`, the operation is protected from a race condition.

## 🧵 Example 2

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Count: " + counter.count);
    }
}
```

### Expected Output

```text
Final Count: 2000
```


## 🎯 Key Points

* **Race Condition:** Multiple threads access shared data simultaneously and produce unpredictable results.
* **Synchronization:** Controls access to shared resources.
* **synchronized:** Allows only one thread at a time to execute the protected code.
* **join():** Makes the main thread wait until the other threads finish.


# Synchronized Block in Java

A **synchronized block** allows only one thread at a time to execute a critical section of code.

## Object Lock

In our `BankAccount` example:

```java
synchronized(this) {
    if (balance >= amount) {
        balance = balance - amount;
    }
}
```

Here, `this` refers to the current `BankAccount` object.

Since Krishna and Rohan are using the **same BankAccount object**, both threads compete for the same object lock.

```java
BankAccount account = new BankAccount();

Customer t1 = new Customer(account, "Krishna", 700);
Customer t2 = new Customer(account, "Rohan", 800);
```

Only one thread can execute the synchronized block at a time.

## Class Lock

A **class lock** is used to synchronize at the class level.

```java
synchronized(BankAccount.class) {
    // critical section
}
```

The lock belongs to the `BankAccount` class, not to a particular object.

Class locks are commonly used when working with **static variables or static methods**.

Example:

```java
class BankAccount {

    private static int balance = 1000;

    public void withdraw(int amount) {

        synchronized(BankAccount.class) {

            if (balance >= amount) {
                balance = balance - amount;
            }
        }
    }
}
```

## Object Lock vs Class Lock

| Object Lock                                | Class Lock                            |
| ------------------------------------------ | ------------------------------------- |
| `synchronized(this)`                       | `synchronized(BankAccount.class)`     |
| Lock belongs to an object                  | Lock belongs to the class             |
| Different objects can have different locks | All objects share the same class lock |
| Usually used for instance data             | Usually used for static data          |

# Another Way of synchronizing the block:

# Reentrance in Java (Reentrant Locks / Synchronized Blocks)

## What is Reentrance?

**Reentrance** (or *re-entrancy*) is a property of synchronization mechanisms that allows a thread which already holds a lock to **acquire the same lock again** without deadlocking itself. In Java, both the `synchronized` keyword and `java.util.concurrent.locks.ReentrantLock` are reentrant by default.

Without reentrance, a thread calling into a method it already holds the lock on (e.g., a recursive call, or one synchronized method calling another on the same object) would block forever waiting on a lock it itself is holding — a self-deadlock.

---

## 1. Reentrant `synchronized` Block

```java
public class Counter {

    private int count = 0;

    public synchronized void increment() {
        count++;
        logIncrement(); // calls another synchronized method on 'this'
    }

    // Same lock (this) is re-acquired here — allowed because
    // synchronized is reentrant.
    public synchronized void logIncrement() {
        System.out.println("Count is now: " + count);
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        counter.increment(); // works fine, no deadlock
    }
}
```

### How it works internally
- Every Java object has an intrinsic lock (monitor) with an associated **acquisition count**.
- When a thread enters a `synchronized` block/method, the JVM checks: *does this thread already own the lock?*
  - If **no**, it acquires the lock and sets the count to 1.
  - If **yes** (reentrant case), it just increments the count.
- Each time the thread exits a synchronized block, the count is decremented.
- The lock is only released (available to other threads) when the count reaches **0**.

---

## 2. Reentrant `ReentrantLock`

```java
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        lock.lock();
        try {
            count++;
            logIncrement(); // re-acquires the same lock
        } finally {
            lock.unlock();
        }
    }

    public void logIncrement() {
        lock.lock();
        try {
            System.out.println("Count is now: " + count);
        } finally {
            lock.unlock();
        }
    }
}
```

### Key points
- `ReentrantLock` maintains an internal **hold count**, accessible via `lock.getHoldCount()`.
- Every `lock()` call by the owning thread increments the count; every `unlock()` decrements it.
- **You must call `unlock()` exactly as many times as `lock()`** — mismatched calls are a common bug. Always unlock in a `finally` block.
- Unlike `synchronized`, `ReentrantLock` supports:
  - `tryLock()` — non-blocking attempt to acquire the lock.
  - `lockInterruptibly()` — acquire while allowing thread interruption.
  - Fair locking mode via `new ReentrantLock(true)`.
  - Explicit `Condition` objects (like `wait()`/`notify()`, but more flexible).

---

## 3. Why Reentrance Matters

| Without Reentrance | With Reentrance |
|---|---|
| A thread re-entering a locked section deadlocks itself | A thread can freely call other locked methods on the same object |
| Recursive synchronized methods would hang | Recursive synchronized methods work correctly |
| Harder to compose synchronized code | Synchronized/locking code composes safely |

---

## 4. Common Pitfall

```java
public void increment() {
    lock.lock();
    // ... forgot to unlock in a finally block, or unlock count mismatched
    count++;
    lock.unlock();
    logIncrement(); // if an exception is thrown before this line,
                     // the lock leaks and is never released!
}
```

**Best practice:** always pair `lock()`/`unlock()` calls using `try { ... } finally { lock.unlock(); }`, exactly mirroring how `synchronized` guarantees release even on exceptions.

---

## 5. Summary

- **Reentrant** = a thread can re-acquire a lock it already holds, without blocking.
- Java's `synchronized` keyword is reentrant by default (based on object monitors and an internal count).
- `ReentrantLock` is the explicit, more flexible equivalent, with the same reentrant guarantee plus extra features (fairness, tryLock, interruptible locking, conditions).
- Always release exactly as many times as you acquire — use `finally` blocks with `ReentrantLock`.
