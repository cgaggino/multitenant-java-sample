package ar.com.scratch.tmtest.repository;

import org.springframework.data.repository.CrudRepository;

import ar.com.scratch.tmtest.domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
