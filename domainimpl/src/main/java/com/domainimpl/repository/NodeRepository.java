package com.domainimpl.repository;

import com.domainimpl.domain.ClusterNode;
import java.util.Collection;

public interface NodeRepository {
    void save(ClusterNode node);
    ClusterNode findById(String nodeId);
    ClusterNode findByIp(String ipAddress);
    void delete(String nodeId);
    Collection<ClusterNode> findAll();
}