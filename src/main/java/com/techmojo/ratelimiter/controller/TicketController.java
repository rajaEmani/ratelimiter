package com.techmojo.ratelimiter.controller;

import com.techmojo.ratelimiter.entities.Ticket;
import com.techmojo.ratelimiter.services.TicketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicket(@PathVariable String ticketId) {
        return this.ticketService.getTicket(Long.parseLong(ticketId));
    }
}



