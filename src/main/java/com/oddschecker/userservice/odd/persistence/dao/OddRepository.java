package com.oddschecker.userservice.odd.persistence.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.oddschecker.userservice.odd.model.Odds;

public interface OddRepository extends JpaRepository<Odds, Long> {

	List<Odds> findByBetId(Integer betId);

}
