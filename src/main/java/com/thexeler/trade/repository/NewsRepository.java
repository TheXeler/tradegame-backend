package com.thexeler.trade.repository;

import com.thexeler.trade.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
