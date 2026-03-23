package com.thexeler.whin.repository;

import com.thexeler.whin.entity.userdata.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
