package com.ericsson.quotationmanagement.controller;

import com.ericsson.quotationmanagement.model.StockSM;
import com.ericsson.quotationmanagement.repository.StockRepository;
import com.ericsson.quotationmanagement.service.QMIntegrationService;
import com.ericsson.quotationmanagement.service.QMService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
class QMControllerTest {
    @MockBean
    private QMIntegrationService qmIntegrationService;
    //@Autowired
    //WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QMService qmService;
    @Autowired
    private StockRepository stockRepository;

    @Test
    void shouldCreateOneStock() throws Exception {
        final File jsonFile = new ClassPathResource("petr4.json").getFile();
        final String stockToCreate = Files.readString(jsonFile.toPath());
        List<StockSM> list = new ArrayList<>();
        StockSM stockSm = new StockSM();
        stockSm.setId("petr4");
        list.add(stockSm);
        when(qmIntegrationService.fetchAllStocksFromStockManager()).thenReturn(list);

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(stockToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void shouldCreateAndFetchAll() throws Exception {
        File jsonFile = new ClassPathResource("petr4.json").getFile();
        String userToCreate = Files.readString(jsonFile.toPath());

        List<StockSM> list = new ArrayList<>();
        StockSM stockSm = new StockSM();
        stockSm.setId("petr4");
        list.add(stockSm);
        stockSm = new StockSM();
        stockSm.setId("aapl34");
        list.add(stockSm);
        when(qmIntegrationService.fetchAllStocksFromStockManager()).thenReturn(list);

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

        jsonFile = new ClassPathResource("aapl34.json").getFile();
        userToCreate = Files.readString(jsonFile.toPath());

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/stock")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void shouldCleanCache() throws Exception {

        this.mockMvc.perform(delete("/stockcache")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldFetchStockByStockId() throws Exception {

        File jsonFile = new ClassPathResource("aapl34.json").getFile();
        String userToCreate = Files.readString(jsonFile.toPath());

        List<StockSM> list = new ArrayList<>();
        StockSM stockSm = new StockSM();
        stockSm.setId("aapl34");
        list.add(stockSm);
        when(qmIntegrationService.fetchAllStocksFromStockManager()).thenReturn(list);

        this.mockMvc.perform(post("/stock")
                        .contentType(APPLICATION_JSON)
                        .content(userToCreate))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/stock/aapl34")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
