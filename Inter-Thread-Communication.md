# Inter-Thread Communication in Java: `wait()`, `notify()`, `notifyAll()`

A practical guide to how threads "talk" to each other in Java using the wait/notify mechanism, with a Producer-Consumer implementation.

---

## Table of Contents

1. [Why Threads Need to Communicate](#why-threads-need-to-communicate)
2. [wait()](#wait)
3. [notify()](#notify)
4. [notifyAll()](#notifyall)
5. [Why These Methods Live on Object, Not Thread](#why-these-methods-live-on-object-not-thread)
6. [The Golden Rule: Always Use a Loop, Not `if`](#the-golden-rule-always-use-a-loop-not-if)
7. [Producer-Consumer Problem](#producer-consumer-problem)
8. [Full Working Example](#full-working-example)
9. [notify() vs notifyAll(): Which to Use](#notify-vs-notifyall-which-to-use)
10. [Common Pitfalls](#common-pitfalls)
11. [Modern Alternatives](#modern-alternatives)

---

## Why Threads Need to Communicate

When multiple threads share data, they often need to coordinate:
- One thread may need to **wait** until a certain condition becomes true (e.g., "wait until the queue has an item").
- Another thread may need to **signal** that the condition has changed (e.g., "I just added an item, wake up!").

Busy-waiting (looping and repeatedly checking a condition) wastes CPU. Java solves this with **`wait()`/`notify()`/`notifyAll()`**, built into every object via intrinsic locks (monitors).

---

## `wait()`

- Causes the current thread to **release the lock** it holds on the object and go to sleep (the "wait set" of that object's monitor).
- The thread stays asleep until another thread calls `notify()` or `notifyAll()` on the same object, or the wait times out (if a timeout was specified).
- **Must be called from within a `synchronized` block/method** on the object being waited on — otherwise `IllegalMonitorStateException` is thrown.

```java
synchronized (lock) {
    while (!conditionMet) {
        lock.wait(); // releases lock, sleeps, re-acquires lock when woken
    }
    // proceed once condition is true
}
```

**Key point:** `wait()` releases the monitor lock while sleeping — this is what allows other threads to enter the synchronized block and eventually call `notify()`.

---

## `notify()`

- Wakes up **a single, arbitrary thread** that is waiting on the object's monitor.
- The woken thread doesn't run immediately — it moves from the wait set to being eligible for the lock, and must re-acquire the lock before continuing (so it waits until the notifying thread exits its synchronized block).
- If no thread is waiting, `notify()` does nothing (the "wake-up" is not queued/remembered).

```java
synchronized (lock) {
    conditionMet = true;
    lock.notify(); // wakes one waiting thread (which one is not guaranteed)
}
```

---

## `notifyAll()`

- Wakes up **all threads** waiting on the object's monitor.
- Each woken thread still must re-acquire the lock, so they proceed one at a time.
- Safer default than `notify()` in most real-world scenarios (see [notify() vs notifyAll()](#notify-vs-notifyall-which-to-use)).

```java
synchronized (lock) {
    conditionMet = true;
    lock.notifyAll(); // wakes all waiting threads
}
```

---

## Why These Methods Live on `Object`, Not `Thread`

Every Java object has an intrinsic **monitor lock**. `wait()`/`notify()`/`notifyAll()` operate on *that specific object's* monitor — not on a particular thread. This design lets any object act as a coordination point between arbitrary threads, which is why the methods are defined on `Object` rather than `Thread`.

---

## The Golden Rule: Always Use a Loop, Not `if`

Never do this:

```java
synchronized (lock) {
    if (!conditionMet) {
        lock.wait(); // BAD: check with 'if'
    }
}
```

Always do this:

```java
synchronized (lock) {
    while (!conditionMet) {
        lock.wait(); // GOOD: re-check condition after waking
    }
}
```

**Why:** 
- **Spurious wakeups** — the JVM is permitted to wake a waiting thread without any `notify()` call.
- **Multiple waiters** — with `notifyAll()`, several threads wake up, but only one may actually find the condition satisfied by the time it re-acquires the lock; others must go back to waiting.
- A `while` loop re-validates the condition every time the thread wakes, guaranteeing correctness.

---

## Producer-Consumer Problem

A classic concurrency pattern:
- **Producer** threads generate data and place it into a shared, bounded buffer/queue.
- **Consumer** threads take data out of the buffer and process it.
- Coordination needed:
  - Producer must **wait** if the buffer is **full**.
  - Consumer must **wait** if the buffer is **empty**.
  - Whenever the state changes (item added/removed), the waiting side must be **notified**.

This is the canonical use case for `wait()`/`notify()`/`notifyAll()`.

---

## Full Working Example

```java
import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public SharedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int value) throws InterruptedException {
        while (queue.size() == capacity) {
            System.out.println("Buffer full. Producer waiting...");
            wait(); // release lock, wait for space
        }
        queue.add(value);
        System.out.println("Produced: " + value);
        notifyAll(); // wake up any waiting consumers
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("Buffer empty. Consumer waiting...");
            wait(); // release lock, wait for an item
        }
        int value = queue.poll();
        System.out.println("Consumed: " + value);
        notifyAll(); // wake up any waiting producers
        return value;
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer(5);

        Thread producer = new Thread(() -> {
            int value = 0;
            try {
                while (true) {
                    buffer.produce(value++);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    buffer.consume();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
    }
}
```

### What's happening here

| Step | Producer | Consumer |
|------|----------|----------|
| 1 | Acquires lock on `buffer` | Acquires lock on `buffer` |
| 2 | If full → `wait()` (releases lock) | If empty → `wait()` (releases lock) |
| 3 | Adds item, calls `notifyAll()` | Removes item, calls `notifyAll()` |
| 4 | Releases lock (exits synchronized method) | Releases lock (exits synchronized method) |

Both `produce()` and `consume()` are `synchronized` on the same object (`this`, i.e., the `SharedBuffer` instance), so only one of them executes at a time, and `wait()`/`notifyAll()` coordinate correctly because they act on that same monitor.

---

## notify() vs notifyAll(): Which to Use

| | `notify()` | `notifyAll()` |
|---|---|---|
| Wakes | One arbitrary waiting thread | All waiting threads |
| Risk | Can wake the "wrong" thread type in mixed producer/consumer scenarios, causing missed signals or deadlock | Slight overhead from waking threads that go back to sleep |
| Safe when | Only one condition and one type of waiting thread | Multiple conditions or multiple types of waiting threads (most real cases) |

**Rule of thumb:** Default to `notifyAll()` unless you can prove only one specific thread should ever be woken and it's always safe to do so. The Producer-Consumer example above deliberately uses `notifyAll()` because both producers and consumers wait on the same object.

---

## Common Pitfalls

1. **Calling `wait()`/`notify()` without holding the lock** → `IllegalMonitorStateException`.
2. **Using `if` instead of `while`** → thread proceeds on a stale/false condition (spurious wakeup or lost race).
3. **Waiting/notifying on different objects** → threads never see each other's signals.
4. **Calling `notify()` before another thread has started waiting** → the signal is lost (unlike `Semaphore` permits, there's no "memory" of a notify call).
5. **Forgetting `synchronized`** on the buffer's access methods → race conditions on the underlying queue itself.

---

## Modern Alternatives

Raw `wait()`/`notify()` is low-level and error-prone. In production code, prefer:

- **`java.util.concurrent.BlockingQueue`** (e.g., `LinkedBlockingQueue`, `ArrayBlockingQueue`) — implements producer-consumer internally with `put()`/`take()`, no manual wait/notify needed.
- **`java.util.concurrent.locks.Condition`** (via `ReentrantLock`) — like `wait()`/`notify()` but supports multiple condition queues per lock (`await()`, `signal()`, `signalAll()`).
- **`Semaphore`**, **`CountDownLatch`**, **`CyclicBarrier`**, **`ExecutorService`** — higher-level coordination primitives for specific patterns.

Understanding `wait()`/`notify()`/`notifyAll()` is still essential — it's the foundation these higher-level utilities are built on, and it's a common interview topic for backend/Java roles.

```java
// Same producer-consumer, using BlockingQueue (idiomatic modern approach)
BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

// Producer
queue.put(value); // blocks automatically if full

// Consumer
int value = queue.take(); // blocks automatically if empty
```
