package com.example.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class JsonData implements Serializable {

    private static final long serialVersionUID = 1L;
        
    private String lastName;
    
    private String firstName;

 
}
