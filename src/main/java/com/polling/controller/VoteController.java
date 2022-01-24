package com.polling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.polling.domain.Vote;
import com.polling.repository.VoteRepository;

@RestController
public class VoteController {

	@Autowired
	private VoteRepository voteRepository;
	// @PostMapping("/polls/{pollId}/votes")
	@RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.POST)
	public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
		vote = voteRepository.save(vote);

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(vote.getId())
				.toUri());
		
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	// @GetMapping("/polls/{pollId}/votes")
	@RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
	public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
		return voteRepository.findByPoll(pollId);
	}	
}
