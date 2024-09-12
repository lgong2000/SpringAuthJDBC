package com.example.springauthjdbc.model;

public record UserDto(String username, String password, String firstname, String lastname, String email, String groupName, String newUsername) {
}
