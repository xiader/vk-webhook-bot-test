package by.sasha.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class vkController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(vkController.class);
    //  private vkprocessor vk;
    @RequestMapping(value = "/api/vkontakte", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String doPost(@RequestBody String requestBody, @RequestHeader Map<String, String> headers) {
//        callbackPreprocessor.onRequest(headers, requestBody);
        LOGGER.info("VKONTAKTE");
        LOGGER.info("VKONTAKTE body {}", requestBody);
        LOGGER.info("VKONTAKTE headers {}", headers);
        return "46e1aa81";
    }

    @CrossOrigin
    @RequestMapping(value = "/api/status", method = RequestMethod.GET)
    public ResponseEntity<?> getStatusOfService() {
        return    new ResponseEntity<>("Привет сервис запущен", HttpStatus.OK);
    }
}

