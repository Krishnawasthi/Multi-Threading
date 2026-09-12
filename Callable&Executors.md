# Java Concurrency Basics: Runnable, Callable, ExecutorService & Future

A quick reference with runnable examples for the core building blocks of Java's `java.util.concurrent` package.

---

## 1. `Runnable`

`Runnable` represents a task that runs independently but **returns no result** and **cannot throw checked exceptions**.

```java
public class RunnableExample {
    public static void main(String[] args) {
        Runnable task = () -> {
            System.out.println("Running in: " + Thread.currentThread().getName());
        };

        Thread thread = new Thread(task);
        thread.start();
    }
}
```

**Key points:**
- Single abstract method: `void run()`
- No return value, no checked exceptions
- Can be run directly via `Thread`, or submitted to an `ExecutorService`

---

## 2. `Callable<V>`

`Callable` is like `Runnable`, but it **returns a result** and **can throw checked exceptions**.

```java
import java.util.concurrent.Callable;

public class CallableExample {
    public static void main(String[] args) throws Exception {
        Callable<Integer> task = () -> {
            Thread.sleep(500);
            return 10 + 20;
        };

        Integer result = task.call();
        System.out.println("Result: " + result);
    }
}
```
## call() --> return future object and throws exception, return type of call() boolean.

**Key points:**
- Single abstract method: `V call() throws Exception`
- Must be executed via an `ExecutorService` to run asynchronously and get a `Future`
- Cannot be passed to a plain `Thread` (only `Runnable` can)

---

## 3. `ExecutorService`

`ExecutorService` manages a pool of threads so you don't manually create/manage `Thread` objects. You submit tasks (`Runnable` or `Callable`) and it handles scheduling and execution.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " on " + Thread.currentThread().getName());
            });
        }

        executor.shutdown(); // stop accepting new tasks, finish existing ones
    }
}
```

**Common factory methods (`Executors`):**
| Method | Use case |
|---|---|
| `newFixedThreadPool(n)` | Fixed number of reusable threads |
| `newCachedThreadPool()` | Grows/shrinks dynamically, good for many short tasks |
| `newSingleThreadExecutor()` | One thread, tasks run sequentially |
| `newScheduledThreadPool(n)` | For delayed/periodic tasks |

**Always shut it down:**
```java
executor.shutdown();          // graceful
executor.shutdownNow();       // forceful, interrupts running tasks
executor.awaitTermination(5, TimeUnit.SECONDS); // wait for completion
```

---

## 4. `Future<V>`

`Future` represents the **result of an asynchronous computation**. When you submit a `Callable` to an `ExecutorService`, you get back a `Future` you can use to check status or retrieve the result once it's ready.

```java
import java.util.concurrent.*;

public class FutureExample {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> task = () -> {
            Thread.sleep(1000);
            return 42;
        };

        Future<Integer> future = executor.submit(task);

        System.out.println("Doing other work while task runs...");

        Integer result = future.get(); // blocks until result is available
        System.out.println("Result: " + result);

        executor.shutdown();
    }
}
```

**Key `Future` methods:**
| Method | Purpose |
|---|---|
| `get()` | Blocks until result is available (or throws) |
| `get(timeout, unit)` | Blocks up to a timeout, then throws `TimeoutException` |
| `isDone()` | True if the task completed (normally, exceptionally, or cancelled) |
| `isCancelled()` | True if the task was cancelled |
| `cancel(boolean mayInterruptIfRunning)` | Attempts to cancel the task |

---

## 5. Putting It All Together

A complete example: submit multiple `Callable` tasks to an `ExecutorService`, collect their `Future`s, and process results as they complete — including error handling.

```java
import java.util.*;
import java.util.concurrent.*;

public class CompleteExample {

    // Simulates a task that computes the square of a number, with some delay
    static Callable<Integer> squareTask(int number) {
        return () -> {
            Thread.sleep(500 + new Random().nextInt(500)); // simulate work
            if (number == 4) {
                throw new RuntimeException("Simulated failure for input 4");
            }
            return number * number;
        };
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures = new ArrayList<>();

        // Submit tasks
        for (int i = 1; i <= 5; i++) {
            futures.add(executor.submit(squareTask(i)));
        }

        // Collect results as they finish
        for (int i = 0; i < futures.size(); i++) {
            Future<Integer> future = futures.get(i);
            try {
                Integer result = future.get(2, TimeUnit.SECONDS);
                System.out.println("Task " + (i + 1) + " result: " + result);
            } catch (ExecutionException e) {
                System.out.println("Task " + (i + 1) + " failed: " + e.getCause().getMessage());
            } catch (TimeoutException e) {
                System.out.println("Task " + (i + 1) + " timed out");
                future.cancel(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdown();
    }
}
```

**What's happening:**
1. Five `Callable<Integer>` tasks are submitted to a 3-thread pool.
2. Each `submit()` call returns a `Future<Integer>` immediately (non-blocking).
3. We loop through the futures and call `get()` with a timeout to retrieve results.
4. Task 4 is designed to throw an exception — it's caught via `ExecutionException`, and `e.getCause()` gives the original exception.
5. The executor is shut down after all tasks are processed.

---

## Quick Comparison

| Concept | Returns value? | Throws checked exception? | Run by |
|---|---|---|---|
| `Runnable` | No | No | `Thread` or `ExecutorService` |
| `Callable<V>` | Yes (`V`) | Yes | `ExecutorService` only |
| `ExecutorService` | — | — | Manages thread pool, executes tasks |
| `Future<V>` | Yes (result of `Callable`) | — | Represents pending/completed async result |

---

## Notes / Gotchas
- `executor.submit(Runnable)` also returns a `Future<?>`, but its `get()` returns `null` on success (since `Runnable` has no result).
- Always call `shutdown()` (or use try-with-resources with `ExecutorService` in Java 19+, which implements `AutoCloseable`) — leaked thread pools keep the JVM alive.
- `future.get()` without a timeout can block forever if the task never completes — prefer the timeout overload in production code.
- For multiple tasks where you want "wait for all" or "wait for first" semantics, look at `ExecutorService.invokeAll()` / `invokeAny()`, or `CompletableFuture` for more composable async pipelines.

  <img width="1557" height="700" alt="image" src="https://github.com/user-attachments/assets/a0106320-ce00-4dc7-9b2a-87c9d46536fa" />
  ## If you will do this you don't need to create multiple threads. like in the below image.
  <img width="1108" height="320" alt="image" src="https://github.com/user-attachments/assets/1e36c93e-c748-4b42-9140-a5f4cc98d752" />

<img width="1072" height="191" alt="image" src="https://github.com/user-attachments/assets/0b6fd3e1-1875-4688-8f82-81ce247dc90b" />

