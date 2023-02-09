package com.example.springhapiserverdemo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class PatientResponseDTO {

    private Long id;

    @NotBlank
    @Size(min = 0, max = 50)
    @Schema(description = "Name of patient", example = "Loa")
    private String name;

    @NotBlank
    @Size(min = 0, max = 80)
    @Schema(description = "Email of patient", example = "loa@gmail.com")
    private String email;

    @Schema(description = "Phone of patient to contact", example = "(+67) 909876989")
    private String phone;

    @Schema(description = "Address of patient", example = "Texas, USA")
    private String address;

}
