package com.fpt.OnlineQuiz.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleId")
	private int id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "status", nullable = false)
	private int status;
	@ManyToMany(mappedBy = "roles")
	private List<Account> accounts;
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<Screen> screens;
}
