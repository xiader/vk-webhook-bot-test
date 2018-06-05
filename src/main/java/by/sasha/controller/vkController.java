package by.sasha.controller;

import by.sasha.service.vkStarter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.Map;

@RestController
public class vkController {
    private final static String CONFIRMATION_TYPE = "confirmation";
    private final static String MESSAGE_TYPE = "message_new";
    private final static String OK_BODY = "ok";
    private static final Logger LOGGER = LoggerFactory.getLogger(vkController.class);

    private final vkStarter vkStarter;

    @Autowired
    public vkController(vkStarter vkStarter) {
        this.vkStarter = vkStarter;
    }

    @Value("${confirmationCode}")
    private String confirmationCode;

    @RequestMapping(value = "/api/vkontakte", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String doPost(@RequestBody String requestBody, @RequestHeader Map<String, String> headers) {
        LOGGER.info("VKONTAKTE");
        LOGGER.info("VKONTAKTE body {}", requestBody);
        LOGGER.info("VKONTAKTE headers {}", headers);
        Gson gson = new GsonBuilder().create();
        String responseBody = null;
        try {
            JsonObject requestJson = gson.fromJson(requestBody, JsonObject.class);

            String type = requestJson.get("type").getAsString();

            if (type == null || type.isEmpty()) throw new ServletException("No type in json");


            switch (type) {
                case CONFIRMATION_TYPE:
                    responseBody = confirmationCode;
                    break;
                case MESSAGE_TYPE:
                    JsonObject object = requestJson.getAsJsonObject("object");
                    int userId = object.getAsJsonPrimitive("user_id").getAsInt();
                    vkStarter.handle(userId);
                    responseBody = OK_BODY;
                    break;
                default:
                    responseBody = OK_BODY; // in case we get another event
                    break;
            }

        } catch (Exception e) {
            LOGGER.error("EXCEPTION IN CONTROLLER {}", e);
        }
        return responseBody;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/status", method = RequestMethod.GET)
    public ResponseEntity<?> getStatusOfService() {
        return new ResponseEntity<>("Привет сервис запущен", HttpStatus.OK);
    }
}

