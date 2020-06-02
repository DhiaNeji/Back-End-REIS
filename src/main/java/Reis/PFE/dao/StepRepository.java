package Reis.PFE.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Reis.PFE.Entities.Step;
import Reis.PFE.Entities.StepDTO;
import Reis.PFE.Entities.StepId;

public interface StepRepository extends JpaRepository<Step,StepId> {
@Query(value="select sum(data_length),step_name from step group by step_name",nativeQuery=true)
public List<Object>getsum();
@Query(value="select * from step where id=?1 and data_collection_id=?2",nativeQuery=true)
public Step getLastStep(int id,int data_collection_id);
@Query(value="select sum(data_length) from step",nativeQuery=true)
public long err_data_count();
@Query(value="select sum(edited_data_length) from step",nativeQuery=true)
public long editeddata_count();
@Query(value="select sum(ignored_data_length) from step",nativeQuery=true)
public long ignored_data_count();
@Query(value="select * from step where data_collection_id=1 order by id asc",nativeQuery=true)
public List<Step>getStepsByCollection(int id);
@Query(value="select * from step order by data_collection_id,id",nativeQuery=true)
public List<Step>getOrderedSteps();
@Query(value="select * from step order by id",nativeQuery=true)
public List<Step>getOrderedStepsById();
@Query(value="select * from step  where id>=14 order by id",nativeQuery=true)
public List<Step>getOrderedDuplicationStepsById();
@Query(value="select id,sum(data_length) as data_length,'0' as data_collection_id from step group by id",nativeQuery=true)
public List<test>test();

}
