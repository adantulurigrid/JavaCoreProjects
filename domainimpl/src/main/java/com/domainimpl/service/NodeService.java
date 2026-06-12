package com.domainimpl.service;

import com.domainimpl.domain.ClusterNode;
import com.domainimpl.repository.NodeRepository;

public class NodeService {
    private final NodeRepository repository;

    public NodeService(NodeRepository repository) {
        this.repository = repository;
    }

    public void registerNode(String id, String ip, String state) {
        ClusterNode node = new ClusterNode(id, ip, state);
        repository.save(node);
        System.out.println("Registered: " + node);
    }

    public ClusterNode getNodeById(String id) {
        return repository.findById(id);
    }

    public ClusterNode getNodeByIp(String ip) {
        return repository.findByIp(ip);
    }

    public void updateNodeState(String id, String newState) {
        ClusterNode node = repository.findById(id);
        if (node != null) {
            node.setState(newState);
            repository.save(node);
            System.out.println("Updated state for " + id + " to " + newState);
        } else {
            System.out.println("Node " + id + " not found.");
        }
    }

    public void removeNode(String id) {
        repository.delete(id);
        System.out.println("Removed node: " + id);
    }

    public void printAllNodes() {
        System.out.println("Current Cluster Nodes:");
        for (ClusterNode node : repository.findAll()) {
            System.out.println(" - " + node);
        }
    }
}