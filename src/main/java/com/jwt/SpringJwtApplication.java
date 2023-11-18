package com.jwt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jwt.entity.User;
import com.jwt.repository.UserRepository;

@SpringBootApplication
public class SpringJwtApplication {

	
	@Autowired
	private UserRepository repository;
	
	@PostConstruct
	 public void initUsers() {
        List<User> users = Stream.of(
                new User(101, "admin", "admin", "admin@gmail.com")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}

}
