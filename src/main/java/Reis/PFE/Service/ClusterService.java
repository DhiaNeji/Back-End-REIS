package Reis.PFE.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Reis.PFE.Entities.Cluster;
import Reis.PFE.Entities.ClusterToEdit;
import Reis.PFE.dao.ClusterRepository;
@Service
public class ClusterService {
@Autowired
private ClusterRepository clusterRepository;
public List<Cluster>getClustersByIdCluster()
{
	return this.clusterRepository.getGroupedClusters();
}
}
