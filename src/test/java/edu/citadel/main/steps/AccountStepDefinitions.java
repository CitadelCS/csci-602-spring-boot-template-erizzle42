package edu.citadel.main.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.citadel.api.request.AccountRequestBody;
import edu.citadel.dal.AccountRepository;
import edu.citadel.dal.model.Account;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class AccountStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    private ResultActions resultActions;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        accountRepository.deleteAll();
    }

    @When("the client creates a new account with username {string}, email {string}, and password {string}")
    public void theClientCreatesANewAccountWithUsernameEmailAndPassword(String username, String email, String password) throws Exception {
        AccountRequestBody accountRequestBody = new AccountRequestBody();
        accountRequestBody.setUsername(username);
        accountRequestBody.setEmail(email);
        accountRequestBody.setPassword(password);

        resultActions = mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountRequestBody)));
    }

    @Then("the account is created successfully")
    public void theAccountIsCreatedSuccessfully() throws Exception {
        resultActions.andExpect(status().isCreated());
    }

    @Given("an account exists with username {string}, email {string}, and password {string}")
    public void anAccountExistsWithUsernameEmailAndPassword(String username, String email, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(password);
        accountRepository.save(account);
    }

    @When("the client requests the account with username {string}")
    public void theClientRequestsTheAccountWithUsername(String username) throws Exception {
        resultActions = mockMvc.perform(get("/account/username/" + username));
    }

    @Then("the client receives the account details for {string}")
    public void theClientReceivesTheAccountDetailsFor(String username) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json("{\"username\":\"" + username + "\"}"));
    }
}
