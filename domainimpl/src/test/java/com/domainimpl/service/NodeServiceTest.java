package com.domainimpl.service;

import com.domainimpl.domain.ClusterNode;
import com.domainimpl.repository.InMemoryNodeRepository;
import com.domainimpl.repository.NodeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NodeServiceTest {

    private NodeService service;

    @BeforeEach
    void setUp() {
        NodeRepository repo = new InMemoryNodeRepository();
        service = new NodeService(repo);
    }

    @Test
    void testRegisterAndFindNode() {
        service.registerNode("test-node-1", "10.0.0.1", "FOLLOWER");

        ClusterNode nodeById = service.getNodeById("test-node-1");
        Assertions.assertNotNull(nodeById);
        Assertions.assertEquals("10.0.0.1", nodeById.getIpAddress());

        ClusterNode nodeByIp = service.getNodeByIp("10.0.0.1");
        Assertions.assertNotNull(nodeByIp);
        Assertions.assertEquals("test-node-1", nodeByIp.getNodeId());
    }

    @Test
    void testUpdateNodeState() {
        service.registerNode("test-node-2", "10.0.0.2", "FOLLOWER");
        service.updateNodeState("test-node-2", "LEADER");

        ClusterNode updatedNode = service.getNodeById("test-node-2");
        Assertions.assertEquals("LEADER", updatedNode.getState());
    }

    @Test
    void testRemoveNode() {
        service.registerNode("test-node-3", "10.0.0.3", "CANDIDATE");
        service.removeNode("test-node-3");

        Assertions.assertNull(service.getNodeById("test-node-3"));
        Assertions.assertNull(service.getNodeByIp("10.0.0.3"));
    }
}