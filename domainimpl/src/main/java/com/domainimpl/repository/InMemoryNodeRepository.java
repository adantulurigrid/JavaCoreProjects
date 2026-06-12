package com.domainimpl.repository;

import com.domainimpl.domain.ClusterNode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryNodeRepository implements NodeRepository {
    private final Map<String, ClusterNode> nodesById = new HashMap<>();
    private final Map<String, ClusterNode> nodesByIp = new HashMap<>();

    @Override
    public void save(ClusterNode node) {
        nodesById.put(node.getNodeId(), node);
        nodesByIp.put(node.getIpAddress(), node);
    }

    @Override
    public ClusterNode findById(String nodeId) {
        return nodesById.get(nodeId);
    }

    @Override
    public ClusterNode findByIp(String ipAddress) {
        return nodesByIp.get(ipAddress);
    }

    @Override
    public void delete(String nodeId) {
        ClusterNode node = nodesById.remove(nodeId);
        if (node != null) {
            nodesByIp.remove(node.getIpAddress());
        }
    }

    @Override
    public Collection<ClusterNode> findAll() {
        return nodesById.values();
    }
}