package com.fpt.OnlineQuiz.model;

import java.util.Date;
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
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Role")
@Where(clause = "status != 0")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleId")
	private int id;
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "createdTime")
	private Date createdTime;

	@Column(name = "updatedTime")
	Date updatedTime;

	@Column(name = "createdUserId")
	private int createdUserId;
	@Column(name = "updatedUserId")
	private int updatedUserId;
	@Column(name = "status", nullable = false)
	private int status;

	@ManyToMany(mappedBy = "roles")
	private List<Account> accounts;
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<Screen> screens;
}
