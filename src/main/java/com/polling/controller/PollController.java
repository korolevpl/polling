package com.polling.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.polling.domain.Poll;
import com.polling.repository.PollRepository;

// Класс ResponseEntity библиотеки Spring используется для передачи ответа.
// Если указан параметр-тип, тогда последний добавляется в тело ответа.
// Кроме того, автоматически выполняется сериализация последнего в формат JSON.
@RestController
public class PollController {
	@Autowired
	private PollRepository pollRepository;

	// @PostMapping("/polls")
	@RequestMapping(value="/polls", method=RequestMethod.POST)
	// Метасимвольный параметр <?> указывает на то, что мы не знаем
	// какого типа будет возвращаемый аргумент.
	// Аннотация @RequestBody указывает на то, что параметр poll извлекается из тела запроса.
	// Здесь происходит десериализация JSON в poll
	public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
		poll = pollRepository.save(poll);
		
		// Объект ServletUriComponentsBuilder генерирует URI для вновь созданного ресурса.
		// Метод fromCurrentRequest() копирует host, schema, port из объекта HttpServletRequest.
		// Метод path() добавляет к генерируемому URI окончание "/{id}".
		// Объект UriComponents формирует URI: http://localhost:8080/polls/{id}.
		// Метод buildAndExpand() заменяет "/{id}" в URI соответствующим значением.
		// Метод toUri() позволяет объекту UriComponents закончить генерацию URI
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(poll.getId())
				.toUri();
		responseHeaders.setLocation(newPollUri);
		// CREATED = коду 201
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	// @GetMapping("/polls")
	@RequestMapping(value="/polls", method=RequestMethod.GET)
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(allPolls, HttpStatus.OK);
	}
	// @GetMapping("/polls/{pollId}")
	@RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
	// Аннотация @PathVarible указывает, что параметр pollId метода getPoll() надо
	// извлечь из {pollId} в пути к ресурсу (объекту)
	public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
		// Тип Optional указывает на то, что допускается хранение null в переменной p
		Optional<Poll> p = pollRepository.findById(pollId);
		return new ResponseEntity<> (p, HttpStatus.OK);
	}
	// @PutMapping("/polls/{pollId}")
	@RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
	// Аннотация @RequestBody указывает на то, что параметр poll извлекается из тела запроса
	public ResponseEntity<?> updatePoll(@RequestBody Poll poll) {
		Poll p = pollRepository.save(poll);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	// @DeleteMapping("/polls/{pollId}")
	@RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
		pollRepository.deleteById(pollId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
