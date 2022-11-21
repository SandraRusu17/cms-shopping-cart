package com.myshop.cmsshoppingcart.models.data;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pages")
@Data
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, message = "The title is too short" )
    private String title;

    private String slug;

    @Size(min = 10, message = "The content is too short" )
    private String content;

    private int sorting;

}
