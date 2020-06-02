package Reis.PFE.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Reis.PFE.Entities.DataCollection;
import Reis.PFE.dao.DataCollectionRepository;

@Service
public class DataCollectionService {
@Autowired
private DataCollectionRepository dataCollectionRepository;
public DataCollection getLast()
{
	Pageable pageable=PageRequest.of(0,1,org.springframework.data.domain.Sort.by("id").descending());
	return this.dataCollectionRepository.findAll(pageable).getContent().get(0);
}
public long dataCollectionCount()
{
	return this.dataCollectionRepository.count();
}
}
