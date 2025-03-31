package com.codecraftlearning.One.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Integer id;
    private String name;
    private String email;
    private String createdAt;
}
