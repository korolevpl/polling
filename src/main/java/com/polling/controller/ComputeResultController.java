package com.polling.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polling.domain.Vote;
import com.polling.dto.OptionCount;
import com.polling.dto.VoteResult;
import com.polling.repository.VoteRepository;

@RestController
public class ComputeResultController {

	@Autowired
	private VoteRepository voteRepository;

	
	@RequestMapping(value="/computeresult", method=RequestMethod.GET)
	public ResponseEntity<?> computeResult(@RequestParam("pollId") Long pollId) {
		VoteResult voteResult = new VoteResult();
		Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);

		int totalVotes = 0;
		Map<Long, OptionCount> temp = new HashMap<Long, OptionCount>();
		for(Vote v : allVotes) {
			totalVotes ++;

			OptionCount optionCount = temp.get(v.getOption().getId());
			if(optionCount == null) {
				optionCount = new OptionCount();
				optionCount.setOptionId(v.getOption().getId());
				temp.put(v.getOption().getId(), optionCount);
			}
			optionCount.setCount(optionCount.getCount()+1);
		}
		
		voteResult.setTotalVotes(totalVotes);
		voteResult.setResults(temp.values());
		
		return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
	}
	
}