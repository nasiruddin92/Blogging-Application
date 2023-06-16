package com.naba.tech.bloggingapplication.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name ="Posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty
    @Size(min = 3, max = 80,message ="Min Length 3 And Max Length 80 !!")
    private String title;
    private String content;
    private String imageName;
    private Date createDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy ="post" ,cascade =CascadeType.ALL,fetch =FetchType.LAZY)
    private List<Comment> comments=new ArrayList<>( );
}
