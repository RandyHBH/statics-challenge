package com.randy.challenge.statics;

import com.randy.challenge.statics.service.transaction.TransactionDAO;
import com.randy.challenge.statics.service.transaction.TransactionResponse;
import com.randy.challenge.statics.service.transaction.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = StaticsChallengeApplication.class)
public class TransactionControllerTest {

    private static final String BODY = "{\"amount\": 200, \"preformedAt\": 123456789}";

    @MockBean
    TransactionService transactionService;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void saveTransactionShouldReturnBadRequestWithEmptyBody() {
        RequestEntity request = performPostRequest("");
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void saveTransactionShouldCallSaveMethodInTheServiceWithProperValues() {
        RequestEntity request = performPostRequest(BODY);

        restTemplate.exchange(request, String.class);

        TransactionDAO expectedRequest = new TransactionDAO(200, 123456789);
        verify(transactionService).save(expectedRequest);
    }

    @Test
    public void saveTransactionShouldReturnBadRequestWithNoAmountValue() {
        String failedBody = "{\"amount\": 200}";
        RequestEntity request = performPostRequest(failedBody);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void saveTransactionShouldReturnBadRequestWithNoPreformedAtValue() {
        String failedBody = "{\"preformedAt\": 123456789}";
        RequestEntity request = performPostRequest(failedBody);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void saveTransactionShouldReturnStatusCodeCreatedWhenResponseFromServiceIsCreated() {
        RequestEntity request = performPostRequest(BODY);
        when(transactionService.save(any())).thenReturn(TransactionResponse.CREATED);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    public void saveTransactionShouldReturnStatusCodeNoContentWhenResponseFromServiceIsFailed() {
        RequestEntity request = performPostRequest(BODY);
        when(transactionService.save(any())).thenReturn(TransactionResponse.FAILED);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    private RequestEntity performPostRequest(String body) {
        try {
            return RequestEntity
                    .post(new URI("/transactions"))
                    .contentType(APPLICATION_JSON)
                    .body(body, String.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
