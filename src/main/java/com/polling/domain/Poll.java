package com.polling.domain;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Poll {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="POLL_ID")
	private Long id;
	
	@Column(name="QUESTION")
	private String question;
	// Аннотация @OneToMany указывает, что переменная options ссылается на много объектов класса Option.
	// При этом все операции с объектом класса Poll через СУБД требуют наличие всех объектов класса Option
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="POLL_ID")
	@OrderBy
	private Set<Option> options;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Set<Option> getOptions() {
		return options;
	}
	public void setOptions(Set<Option> options) {
		this.options = options;
	}
	
	@Override
	public String toString() {
		return getId() + ", " + getQuestion() + ", " + getOptions();
	}
	
}
