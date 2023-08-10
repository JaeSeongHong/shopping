package com.smhrd.shopping.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.smhrd.shopping.model.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(length=255, unique=true, nullable=false)
	private String username;
	
	@Column(length=16, nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String nickname;
	
	@Builder
	public Users(String nickname, String username, String site) {
		this.id = 1L;
		this.username = "user";
		this.password = "user";
		this.nickname = "사용자";
	}
}
