package com.javagrind.paymentservice.dao;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerDao {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String notes;
}
