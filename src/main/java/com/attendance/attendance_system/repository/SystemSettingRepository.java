package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SystemSettingRepository extends JpaRepository<SystemSetting , Long> {

}
