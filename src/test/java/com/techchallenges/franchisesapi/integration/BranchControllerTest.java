package com.techchallenges.franchisesapi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.techchallenges.franchisesapi.infrastructure.controllers.BranchController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class BranchControllerTest {

    private static final String LOCAL_HOST_BRANCH_URL = "http://localhost:8080/api/branch";

    @Autowired
    private BranchController branchController;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
    }

    @Test
    void getTopStockBranchesByFranchiseTest() throws Exception {
        var franchiseName = "KFC";
        var branchName = "KFC-N";

        String response = mockMvc
                .perform(get(LOCAL_HOST_BRANCH_URL + BranchController.TOP_STOCK_URL, franchiseName))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var productName = getTopStockProductNameByBranchFromResponse(response, branchName);

        assertEquals("Popcorn", productName);
    }

    private String getTopStockProductNameByBranchFromResponse(String response, String branchName) throws Exception {
        var jsonArray = new JSONArray(response);
        JSONObject branchJson, productJson;

        for (int i = 0; i < jsonArray.length(); i++) {
            branchJson = jsonArray.getJSONObject(i);
            if (branchJson.getString("name").equals(branchName)) {
                productJson = branchJson.getJSONArray("products").getJSONObject(0);
                return productJson.getString("name");
            }
        }

        return "";
    }
}
