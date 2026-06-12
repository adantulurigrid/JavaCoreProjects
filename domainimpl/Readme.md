# Domain Implementation (Consentra Node Service)

This microservice manages Cluster Nodes for the Consentra Consensus System, demonstrating layered architecture using pure Java and Collections.

## User Stories
* As a system admin, I want to register a new node so it can join the cluster.
* As a system admin, I want to find a node by its ID or IP Address instantly.
* As a system admin, I want to update a node's state (e.g., FOLLOWER to CANDIDATE).
* As a system admin, I want to remove a node from the cluster.

## Technologies
* Pure Java 17
* Maven (with Maven Wrapper)
* Lombok
* JUnit 5
* PMD, Checkstyle, SpotBugs