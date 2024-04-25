package com.yevhenii.ticketingService.repository;

import com.yevhenii.ticketingService.entity.ProveFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository <ProveFile,String> {



}
