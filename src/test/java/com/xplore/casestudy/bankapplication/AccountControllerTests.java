package com.xplore.casestudy.bankapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class AccountControllerTests {
    @Autowired
    private MockMvc mockMvc;
    private SimpleGrantedAuthority authority = new SimpleGrantedAuthority("CASHIER");

    @Test
    public void getStatementByDateTest() throws Exception {
        this.mockMvc.perform(get("/accounts/statement_by_transaction_date_table/" +
                "100000302?start=2020-06-11&end=2020-06-26")
                .with(user("cash").password("k3333")
                        .authorities(authority)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view()
                        .name("fragment::n-transaction-table"))
                .andExpect(model().attributeExists("transactionList"));
    }

    @Test
    public void getStatementByLastNTransactionText() throws Exception {
        this.mockMvc.perform(get("/accounts/statement_by_transaction_cnt/100000302/4")
                .with(user("cash").password("k3333").authorities(authority)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view()
                        .name("fragment::n-transaction-table"))
                .andExpect(model().attributeExists("transactionList"));
    }


}
