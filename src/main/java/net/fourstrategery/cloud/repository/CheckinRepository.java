package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.CheckinEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<CheckinEntity, String> {

}
