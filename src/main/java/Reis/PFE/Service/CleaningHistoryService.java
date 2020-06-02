package Reis.PFE.Service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Reis.PFE.Entities.CleaningHistory;
import Reis.PFE.dao.CleaningHistoryRepository;
@Service
public class CleaningHistoryService {
@Autowired
private CleaningHistoryRepository cleaningHistoryRepository;
public CleaningHistory getLastCleaningHistory()
{
	Pageable pageable=PageRequest.of(0,1,org.springframework.data.domain.Sort.by("id").descending());
	return this.cleaningHistoryRepository.findAll(pageable).getContent().get(0);
}
public void Increment(String step)
{
	CleaningHistory c=this.getLastCleaningHistory();
	switch (step) {
	case "1":
		c.setStep_1(c.getStep_1()+1);
		this.cleaningHistoryRepository.save(c);
		break;
	case "2":

		c.setStep_2(c.getStep_2()+1);
		this.cleaningHistoryRepository.save(c);
		break;
case "3":

	c.setStep_3(c.getStep_3()+1);
	this.cleaningHistoryRepository.save(c);
		break;
case "4":

	c.setStep_4(c.getStep_4()+1);
	this.cleaningHistoryRepository.save(c);
	break;
case "5":

	c.setStep_5(c.getStep_5()+1);
	this.cleaningHistoryRepository.save(c);
	break;
case "6":

	c.setStep_6(c.getStep_6()+1);
	this.cleaningHistoryRepository.save(c);
	break;
case "7":

	c.setStep_7(c.getStep_7()+1);
	this.cleaningHistoryRepository.save(c);
	break;
case "8":

	c.setStep_8(c.getStep_8()+1);
	this.cleaningHistoryRepository.save(c);
	break;
case "9":

	c.setStep_9(c.getStep_9()+1);
	this.cleaningHistoryRepository.save(c);
	break;
case "10":

	c.setStep_10(c.getStep_10()+1);
	this.cleaningHistoryRepository.save(c);
	break;
	default:
		break;
	}
}
public void addNewCleaning()
{
	this.cleaningHistoryRepository.save(new CleaningHistory(LocalDateTime.now()));
}
}
