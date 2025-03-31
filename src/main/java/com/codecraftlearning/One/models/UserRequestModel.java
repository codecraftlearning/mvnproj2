package com.codecraftlearning.One.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestModel {
    private Integer id;
    private String name;
    private String email;
}
