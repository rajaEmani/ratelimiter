package com.techmojo.ratelimiter.services;
import com.techmojo.ratelimiter.entities.Ticket;

public interface TicketService {
    Ticket getTicket(Long ticketId);
}
