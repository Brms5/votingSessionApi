package com.api.votingsession.Infrastructure.Repositories;

import com.api.votingsession.Domain.Models.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, UUID> {}
