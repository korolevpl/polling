package com.polling.repository;

import org.springframework.data.repository.CrudRepository;

import com.polling.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long> {

}
