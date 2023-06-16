package com.naba.tech.bloggingapplication.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {

    private long id;
    @NotEmpty
    @Size(min = 3,max = 100,message ="Category Title can't be empty!!")
    private String title;
    private String description;
}
