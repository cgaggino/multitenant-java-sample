package ar.com.scratch.tmtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.scratch.tmtest.domain.Ticket;
import ar.com.scratch.tmtest.repository.TicketRepository;

@RestController
@RequestMapping("/{tenantid}")
public class HelloController {

	@Autowired
	private TicketRepository TicketRepository;

	@RequestMapping
	public String index() {
		String body = "<h1>Tickets</h1>";
		Iterable<Ticket> tickets = this.getTicketRepository().findAll();
		for (Ticket ticket : tickets) {
			body += "<li>" + ticket.getProducto() + "</li>";
		}
		return body;
	}

	public TicketRepository getTicketRepository() {
		return TicketRepository;
	}

	public void setTicketRepository(TicketRepository ticketRepository) {
		TicketRepository = ticketRepository;
	}

}