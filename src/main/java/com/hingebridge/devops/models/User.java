package com.hingebridge.devops.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends CommonFields {
	private String password;
	private String firstname;
	private String lastname;
	private String username;
	private String phonenumber;
	private String gender;
	private String dateOfBirth;
	private String nationality;
	private String role;
}