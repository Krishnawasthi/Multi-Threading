# Multi-Threading

<img width="1106" height="308" alt="image" src="https://github.com/user-attachments/assets/d52b483f-a5e8-475c-aeef-67265a35b437" />

<img width="1103" height="300" alt="image" src="https://github.com/user-attachments/assets/7fb7efbe-788f-4e24-8f26-7de6f110e67d" />
<img width="1103" height="131" alt="image" src="https://github.com/user-attachments/assets/29c44fd6-127f-4c33-8ffa-70aecf0cf36a" />

<img width="1108" height="181" alt="image" src="https://github.com/user-attachments/assets/1113f02e-164f-4aa1-8479-0aaaf5378fc2" />
<img width="1108" height="313" alt="image" src="https://github.com/user-attachments/assets/a872533c-24eb-4e1d-9280-c9c6695f6c4b" />

## In thread, most of the time, your answer will be, we cannot predict the answer.
## Now we cannot predict the order of execution.
## start() method register a thread with CPU.

<img width="1106" height="305" alt="image" src="https://github.com/user-attachments/assets/f0700b10-d3b2-4af2-ba3a-4cb0ed8cbf4d" />

<img width="1106" height="306" alt="image" src="https://github.com/user-attachments/assets/eb51923b-3f68-439e-b535-0b19b8c31cb4" />

## We cannot predict the order. We cannot predict the order of execution. We don't know, right, who will get the chance first,  Which thread will be allowed by CPU to  execute the task.

# Life cycle of Thread:
<img width="1066" height="196" alt="image" src="https://github.com/user-attachments/assets/9326f9a1-d384-44f9-b627-cc2f72b9fafe" />
<img width="1097" height="309" alt="image" src="https://github.com/user-attachments/assets/e353f13f-6be6-46c2-9fae-84a796b5a0ba" />
<img width="1106" height="309" alt="image" src="https://github.com/user-attachments/assets/2ee94c8e-569a-4330-bd3c-48d3758c6235" />


<img width="1123" height="259" alt="image" src="https://github.com/user-attachments/assets/ca33acf7-136a-41b5-936f-9d4fbe6b64e5" />

### start() can call only when thread is new born otherwise it will give IllegalThreadStateException.

## Here thread is on the Sleeping state for 5 milli second
<img width="1107" height="302" alt="image" src="https://github.com/user-attachments/assets/a09f0c08-6e33-4067-a582-9a2d734f5d95" />
<img width="1105" height="215" alt="image" src="https://github.com/user-attachments/assets/8ecf9f1c-5fc6-47be-82b5-e2f0436d858e" />

### If you directly call run method at the place of start() method it won't be multithreading this will be normal call for a method that is run() method.

## Solve Problems : 
<img width="1105" height="296" alt="image" src="https://github.com/user-attachments/assets/3759f2b8-9499-4edc-96ad-f2ab3235979f" />

# Runnable Interface and callable Interface : 
## Whenever you expecting a return from the thread Runnable is not the right choice---> Use callable<V> is the right chioce
<img width="820" height="97" alt="image" src="https://github.com/user-attachments/assets/8aca66fa-fc54-466a-b062-b2b125ac2eb5" />
<img width="1083" height="162" alt="image" src="https://github.com/user-attachments/assets/f6f6277a-e608-4fbc-95cd-7d49b3a956f2" />
## callable throws the exception in return. 





