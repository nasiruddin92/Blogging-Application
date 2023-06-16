package com.naba.tech.bloggingapplication.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="Categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min = 3,max = 100,message ="Category Title can't be empty!!")
    private String title;
    private String description;

    @OneToMany(mappedBy ="category",cascade =CascadeType.ALL,fetch =FetchType.LAZY)
    List<Post> posts=new ArrayList<>();
}
