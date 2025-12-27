package com.example.MySpringAI_MCP_Server.service;

import com.example.MySpringAI_MCP_Server.entity.HelpDeskTicketEntity;
import com.example.MySpringAI_MCP_Server.payload.HelpDeskTicketPayload;
import com.example.MySpringAI_MCP_Server.repo.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    public HelpDeskTicketEntity createHelpDeskTicket(HelpDeskTicketPayload payload) {
        HelpDeskTicketEntity ticket = HelpDeskTicketEntity.builder()
                .issue(payload.issue())
                .username(payload.userName())
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return helpDeskTicketRepository.save(ticket);
    }

    public List<HelpDeskTicketEntity> getHelpDeskTicketsByUser(String username) {
        return helpDeskTicketRepository.findByUsername(username);
    }
}
