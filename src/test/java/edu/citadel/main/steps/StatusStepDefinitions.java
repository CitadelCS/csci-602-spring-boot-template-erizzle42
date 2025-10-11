package edu.citadel.main.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StatusStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    @Value("${info.app.name}")
    private String applicationName;

    @Value("${info.app.description}")
    private String applicationDescription;

    @Value("${info.app.version}")
    private String applicationVersion;

    @When("the client requests the server health")
    public void theClientRequestsTheServerHealth() throws Exception {
        resultActions = mockMvc.perform(get("/health"));
    }

    @Then("the client receives a status of ok")
    public void theClientReceivesAStatusOfOk() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"ok\"}"));
    }

    @When("the client requests the server info")
    public void theClientRequestsTheServerInfo() throws Exception {
        resultActions = mockMvc.perform(get("/info"));
    }

    @Then("the client receives server info")
    public void theClientReceivesServerInfo() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"" + applicationName + "\",\"version\":\"" + applicationVersion + "\",\"description\":\"" + applicationDescription + "\"}"));
    }
}
