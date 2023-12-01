package com.privacare.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    String authId;

    @NotEmpty
    String name;

    @NotEmpty
    String surname;

    @NotEmpty
    String pesel;

    @NotEmpty
    String phoneNumber;
}
