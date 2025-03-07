package com.patizone.core_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Table(name = "ads")
public class Ad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User owner;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String description;

  //bird, cat, dog etc...
  @Column(name = "animal_type", nullable = false)
  private String animalType;

  //given name of animal
  @Column(name = "animal_name")
  private String animalName;

  //breed of animal
  @Column(name = "animal_breed")
  private String animalBreed;

  @Column(name = "animal_age")
  private Integer animalAge;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  //start date of the ad
  @Column(name = "start_date", nullable = false)
  @CreationTimestamp
  private LocalDateTime startDate;

  //end date of the ad
  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "created_at", nullable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "ad_type_id", nullable = false)
  private AdType adType;
}
