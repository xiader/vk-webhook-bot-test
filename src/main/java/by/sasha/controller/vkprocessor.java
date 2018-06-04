//package by.sasha.controller;
//
//
//import com.vk.api.sdk.callback.CallbackApi;
//import com.vk.api.sdk.client.TransportClient;
//import com.vk.api.sdk.client.VkApiClient;
//import com.vk.api.sdk.httpclient.HttpTransportClient;
//import com.vk.api.sdk.objects.messages.Message;
//import org.apache.logging.log4j.LogManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
////import org.apache.logging.log4j.LogManager;
////import org.apache.logging.log4j.Logger;
//
//
//public class vkprocessor extends CallbackApi {
//
//    private static final Logger LOGGER =LoggerFactory.getLogger(vkprocessor.class);
//    TransportClient transportClient = new HttpTransportClient();
//    VkApiClient vk = new VkApiClient(transportClient);
//
//    @Override
//    public void messageNew(Integer groupId, Message message) {
//        System.out.println(message.getBody());
//        LOGGER.debug( message.getBody());
//        LOGGER.debug("VKONTAKTE MESSAGE");
//        LOGGER.debug("chatid {}", message.getChatId());
//        LOGGER.debug(message.getActionEmail());
//        LOGGER.debug(message.getTitle());
//    }
//}