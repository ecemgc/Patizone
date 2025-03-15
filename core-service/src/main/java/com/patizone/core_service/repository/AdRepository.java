package com.patizone.core_service.repository;

import com.patizone.core_service.entity.Ad;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

  List<Ad> findAllByOwnerId(Long ownerId);

  Page<Ad> findAllByOwnerId(Long ownerId, Pageable pageable);

}
