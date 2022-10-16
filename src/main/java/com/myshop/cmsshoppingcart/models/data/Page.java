package com.myshop.cmsshoppingcart.models.data;

import javax.persistence.*;

@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String slug;

    private String content;

    private int sorting;



}
