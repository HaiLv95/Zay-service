package com.zayshop.dto;

import com.zayshop.entity.Account;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Integer id;

    private String username;

    private String nameReceived;

    private String phoneNumber;

    private LocalDate orderDate;

    private String address;

    private String status;
}
