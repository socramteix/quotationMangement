package com.ericsson.quotationmanagement.controller;

import com.ericsson.quotationmanagement.QuotationManagementApplication;
import com.ericsson.quotationmanagement.configuration.QMConfiguration;
import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.service.QMService;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
class QMControllerTest {

    @Autowired
    private StockRepository repository;

    @Mock
    private QMService qmService;

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateOneStock() throws Exception {
        final File jsonFile = new ClassPathResource("petr4.json").getFile();
        final String userToCreate = Files.readString(jsonFile.toPath());

        when(qmService.existsInStockManager(any())).thenReturn(true);

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

        assertThat(this.repository.findAll()).hasSize(1);
    }

    @Test
    void shouldCreateAndFetchAll() throws Exception {
        File jsonFile = new ClassPathResource("petr4.json").getFile();
        String userToCreate = Files.readString(jsonFile.toPath());

        when(qmService.existsInStockManager(any())).thenReturn(true);

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

        jsonFile = new ClassPathResource("aapl34.json").getFile();
        userToCreate = Files.readString(jsonFile.toPath());

        when(qmService.existsInStockManager(any())).thenReturn(true);

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/stock")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(this.repository.findAll()).hasSize(2);
    }

    @Test
    void shouldCleanCache() throws Exception {

        when(qmService.existsInStockManager(any())).thenReturn(true);

        this.mockMvc.perform(post("/stockcache")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
