package com.api.votingsession.Infrastructure.Repositories;

import com.api.votingsession.Domain.Models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {}
