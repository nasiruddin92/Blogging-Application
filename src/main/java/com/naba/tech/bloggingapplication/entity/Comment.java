package com.naba.tech.bloggingapplication.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name ="Comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min = 3,max = 100,message ="Comment content can't be empty!!")
    private String content;
    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;
}
