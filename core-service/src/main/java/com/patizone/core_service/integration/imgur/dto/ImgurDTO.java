package com.patizone.core_service.integration.imgur.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ImgurDTO {
    private String id;
    private String deletehash;
    private String account_id;
    private String account_url;
    private String ad_type;
    private String ad_url;
    private String title;
    private String description;
    private String name;
    private String type;
    private int width;
    private int height;
    private int size;
    private int views;
    private String section;
    private String vote;
    private int bandwidth;
    private boolean animated;
    private boolean favorite;
    private boolean in_gallery;
    private boolean in_most_viral;
    private boolean has_sound;
    private boolean is_ad;
    private Boolean nsfw;
    private String link;
    private List<String> tags;
    private long datetime;
    private String mp4;
    private String hls;
}
