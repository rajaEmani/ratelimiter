package com.techmojo.ratelimiter.repository;

import com.techmojo.ratelimiter.entities.Ticket;
import com.techmojo.ratelimiter.services.TicketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepositoryImpl implements TicketRepository{
    private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);
    List<Ticket> list;

    public TicketRepositoryImpl() {
        list = new ArrayList<>();
        int i;
        for (i = 1; i <= 8000; i++) {
            list.add(new Ticket(i, i + 3, "Ticket"));
        }
    }

    public Ticket getTicket(Long courseId) {
        LOG.info("Getting course info for id {}", courseId);
        for (Ticket ticket : list) {
            if (ticket.getId() == courseId) {
                return ticket;
            }
        }
        return null;
    }
}
