package com.techmojo.ratelimiter.repository;

import com.techmojo.ratelimiter.entities.Ticket;

public interface TicketRepository {
    Ticket getTicket(Long ticketId);
}
