package com.polling.repository;

import org.springframework.data.repository.CrudRepository;

import com.polling.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {

}
