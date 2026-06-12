package com.domainimpl.domain;



public class ClusterNode {
    private String nodeId;
    private String ipAddress;
    private String state;

    public ClusterNode() {}

    public ClusterNode(String nodeId, String ipAddress, String state) {
        this.nodeId = nodeId;
        this.ipAddress = ipAddress;
        this.state = state;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ClusterNode{" +
                "nodeId='" + nodeId + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}