package by.sasha.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class vkStarter {

    private static final Logger LOG = LoggerFactory.getLogger(vkStarter.class);

    private VkApiClient apiClient;

    private  GroupActor actor;

    private final Random random = new Random();

    @Value("${groupId}")
    private String groupID;

    @Value("${token}")
    private String group_token;

    @Value("${serverId}")
    private String server_id;

    @PostConstruct
        public void init() {
            HttpTransportClient client = new HttpTransportClient();
            VkApiClient apiClient = new VkApiClient(client);
            GroupActor actor = null;
            try {
                actor = initVkApi(apiClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        this.apiClient = apiClient;
        this.actor = actor;

    }


    private GroupActor initVkApi(VkApiClient apiClient) {
        int groupId = Integer.parseInt(groupID);
        String token = group_token;
        int serverId = Integer.parseInt(server_id);
        if (groupId == 0 || token == null || serverId == 0) throw new RuntimeException("Params are not set");
        GroupActor actor = new GroupActor(groupId, token);

        try {
            apiClient.groups().setCallbackSettings(actor, serverId).messageNew(true).execute();
        } catch (ApiException e) {
            throw new RuntimeException("Api error during init", e);
        } catch (ClientException e) {
            throw new RuntimeException("Client error during init", e);
        }

        return actor;
    }


   public void handle(int userId) {
        try {
            apiClient.messages().send(actor).message("Hello my friend!").userId(userId).randomId(random.nextInt()).execute();
        } catch (ApiException e) {
            LOG.error("INVALID REQUEST", e);
        } catch (ClientException e) {
            LOG.error("NETWORK ERROR", e);
        }
    }

}
