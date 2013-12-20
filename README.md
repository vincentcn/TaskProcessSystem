Task Process System
===================

**TPS(Task Process System)** is a system can be described as following.
* A task based system. "Task" is the basic unit of TPS. A task can be a sequence of business processes/data flow. Every function supported by the system will be describe as a task. And the system will be extended by supporting more and more tasks.
* The processing module of TPS system receive tasks and process them one by one asynchronously. The tasks are received and processed in different threads. The target of processing module is a stable and high-throughput distribution&calculation center.
* Fetching data via http/https, EMS, web services, Data Analysis, Mail notification, Data persistence ..., all the featured will be added to support different tasks.

Please refer to the [Wiki pages](https://github.com/vincentcn/TaskProcessSystem/wiki) for more.
