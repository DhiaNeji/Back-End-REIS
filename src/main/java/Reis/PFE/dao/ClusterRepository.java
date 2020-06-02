package Reis.PFE.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import Reis.PFE.Entities.Cluster;
import Reis.PFE.Entities.ClusterToEdit;


public interface ClusterRepository extends JpaRepository<Reis.PFE.Entities.Cluster, Long> {

	
	@Query(value="select * from customersclusters where idcluster in(select idcluster from customersclusters group by idcluster having(count(idcluster)>1))", nativeQuery = true)
	List<Cluster>  getGroupedClusters();
	@Query(value="SELECT * FROM customersclusters where idcluster=:idcluster", nativeQuery = true)
	List<Cluster> findAllByNid(@Param(value = "idcluster") int idcluster );
	@Query(value=" SELECT * from customersclusters where idcluster in (select idcluster from source1.customersclusters c group by idcluster having count(idcluster)>1)ORDER BY idcluster", nativeQuery = true)
	Page<Object> findAllCluster(@Param(value = "page")  Pageable page );



}
