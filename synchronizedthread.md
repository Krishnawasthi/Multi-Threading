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

# Reentrance in Java

**Reentrance** means that a thread which already owns a lock can acquire the **same lock again** without getting blocked.

Java's `synchronized` mechanism is **reentrant**.

## Example

```java
class BankAccount {

    public synchronized void withdraw() {
        System.out.println("Withdraw method");

        checkBalance();
    }

    public synchronized void checkBalance() {
        System.out.println("Checking balance");
    }
}
```

When a thread calls:

```java
withdraw();
```

The thread gets the lock of the `BankAccount` object.

Inside `withdraw()`, it calls:

```java
checkBalance();
```

`checkBalance()` is also synchronized, but the **same thread already owns the lock**.

Because Java locks are reentrant, the thread can enter `checkBalance()` without waiting for itself to release the lock.

## Why is it called Reentrant?

The same thread can **re-enter** a synchronized method/block that uses the same lock.

```text
Thread
  ↓
withdraw() → gets lock
  ↓
checkBalance() → gets the same lock again
  ↓
returns from checkBalance()
  ↓
returns from withdraw()
  ↓
lock released
```

## Important Point

A lock is associated with the **thread that owns it**.

The same thread can acquire the same lock multiple times, but another thread must wait until the lock is completely released.

**In short:**

> Reentrance = A thread can acquire the same lock again if it already owns that lock.

