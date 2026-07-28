package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;
 // @Column(unique = true)
 private String username;
 private String password;
 private String salt;

public User() {
    this.username = null;
    this.password = null;
    this.salt = null;
}

 public User(String username, String password, String salt) {
  super();
  this.username = username;
  this.password = password;
  this.salt = salt;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getUsername() {
  return username;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public String getSalt() {
  return salt;
 }

 public void setSalt(String salt) {
  this.salt = salt;
 }

 @Override
 public String toString() {
  return "User [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt + "]";
 }

}