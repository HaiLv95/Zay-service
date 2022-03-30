package com.zayshop.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {

    @NotNull
    private String username;

    @NotNull
    private String fullname;

    @NotNull
    private String email;

    @NotNull
    private Boolean activated = false;

    @NotNull
    private String role;

    private boolean isEdit;
}
