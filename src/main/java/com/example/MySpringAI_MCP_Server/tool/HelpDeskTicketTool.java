package com.example.MySpringAI_MCP_Server.tool;

import com.example.MySpringAI_MCP_Server.entity.HelpDeskTicketEntity;
import com.example.MySpringAI_MCP_Server.payload.HelpDeskTicketPayload;
import com.example.MySpringAI_MCP_Server.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelpDeskTicketTool {

    private final HelpDeskTicketService service;

    @Tool(name = "createTicket", description = "Create a HelpDesk support ticket", returnDirect = true)
    public String createTicket(@ToolParam(description = "Details to create a Support ticket") HelpDeskTicketPayload payload) {

        log.info("Creating support ticket for user: {} with details: {}", payload.userName(), payload.issue());

        // 將 LLM 填入的 payload + 呼叫者 username 寫入資料庫
        HelpDeskTicketEntity savedTicket = service.createHelpDeskTicket(payload);
        log.info("Ticket created successfully. Ticket ID: {}, UserName: {}", savedTicket.getId(), savedTicket.getUsername());

        // returnDirect=true：模型會直接回傳此字串給使用者，不再追加其他回答
        return "Ticket id#: " + savedTicket.getId() + " created successfully for user: " + savedTicket.getUsername();
    }

    @Tool(description = "Fetch the status of the tickets based on a given userName")
    public List<HelpDeskTicketEntity> getTicketStatus(String userName) {

        log.info("Fetching tickets for user: {}", userName);

        // 查詢該使用者所有工單並回傳；模型可用此結果回答進度
        List<HelpDeskTicketEntity> tickets = service.getHelpDeskTicketsByUser(userName);
        log.info("Found {} tickets for user: {}", tickets.size(), userName);

        return tickets;
    }
}