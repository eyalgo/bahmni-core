package org.bahmni.module.bahmnicore.web.v1_0.controller.search;

import org.bahmni.module.bahmnicore.web.v1_0.BaseIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

public class BahmniPatientSearchControllerIT extends BaseIntegrationTest {

    @Before
    public void setup() {
    }

    @Test
    public void shouldReturnStatusOK() throws Exception {
        MockHttpServletRequest request = newGetRequest("/rest/v1/bahmnicore/search/patient");
        MockHttpServletResponse response = handle(request);

        assertEquals(200, response.getStatus());
    }
//
//    @Test
//    public void shouldReturnStatusOK() throws Exception {
//        mockMvc.perform(get("http://www.google.com")).andExpect(status().isOk());
//
//        String url = "openmrs/ws/rest/" + RestConstants.VERSION_1 + "/bahmnicore/search/patient";
//
//        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

}