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
