package com.domainimpl;

import com.domainimpl.repository.InMemoryNodeRepository;
import com.domainimpl.repository.NodeRepository;
import com.domainimpl.service.NodeService;

public class Main {
    public static void main(String[] args) {
        NodeRepository repo = new InMemoryNodeRepository();
        NodeService service = new NodeService(repo);

        System.out.println("--- 1. Create Nodes ---");
        service.registerNode("node-1", "192.168.1.10", "LEADER");
        service.registerNode("node-2", "192.168.1.11", "FOLLOWER");
        service.registerNode("node-3", "192.168.1.12", "FOLLOWER");

        System.out.println("\n--- 2. Find All Nodes ---");
        service.printAllNodes();

        System.out.println("\n--- 3. Find Nodes (Constant Time Search) ---");
        System.out.println("Find by ID (node-1): " + service.getNodeById("node-1"));
        System.out.println("Find by IP (192.168.1.12): " + service.getNodeByIp("192.168.1.12"));

        System.out.println("\n--- 4. Update Node ---");
        service.updateNodeState("node-2", "CANDIDATE");
        System.out.println("Verify Update (node-2): " + service.getNodeById("node-2"));

        System.out.println("\n--- 5. Delete Node ---");
        service.removeNode("node-3");
        System.out.println("Attempt to find deleted node-3: " + service.getNodeById("node-3"));

        System.out.println("\n--- 6. Final State of Cluster ---");
        service.printAllNodes();
    }
}