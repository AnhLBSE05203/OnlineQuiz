package com.fpt.OnlineQuiz.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Screen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "screenId")
	private int id;
	@Column(name = "screenName")
	private String name;
	@Column(name = "screenLink")
	private String link;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ScreenRole", joinColumns = @JoinColumn(name = "screenId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
	private List<Role> roles;

	public void addRole(Role role) {
		roles.add(role);
		role.getScreens().add(this);
	}

	public void removeRole(Role role) {
		roles.remove(role);
		role.getScreens().remove(this);
	}
}
