package com.techmojo.ratelimiter.services;

import com.techmojo.ratelimiter.entities.Ticket;
import com.techmojo.ratelimiter.repository.TicketRepository;
import org.springframework.stereotype.Service;


@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket getTicket(Long ticketId) {
        return ticketRepository.getTicket(ticketId);
    }
}
