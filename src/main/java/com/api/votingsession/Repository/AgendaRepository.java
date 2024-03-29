package com.api.votingsession.Repository;

import com.api.votingsession.domain.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, UUID> {}
