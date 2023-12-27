package vn.huydtg.immunizationservice.service.notification;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.huydtg.immunizationservice.web.rest.vm.PushNotificationRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PushNotificationService {

    private Logger logger = LoggerFactory.getLogger(PushNotificationService.class);
    private FCMService fcmService;

    public PushNotificationService(FCMService fcmService) {
        this.fcmService = fcmService;
    }


    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(request);
            logger.info(request.getToken());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void sendPushNotification(PushNotificationRequest request) {
        try {
            fcmService.sendMessage(getSamplePayloadData(), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Map<String, String> getSamplePayloadData() {
        Map<String, String> pushData = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        Map<String, String> payload = new HashMap<>();
        Map<String, String> article_data = new HashMap<>();
        pushData.put("title", "Notification for pending work");
        pushData.put("message", "pls complete your pending task immediately");
        pushData.put("image", "https://www.hartz.com/wp-content/uploads/2023/05/do-cats-only-meow-to-humans-1.jpg");
        pushData.put("timestamp", "2020-07-11 19:23:21");
        pushData.put("article_data", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        return pushData;
    }
    public void sendPushNotificationCustomDataWithTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageCustomDataWithTopic(getSamplePayloadDataCustom(), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    private Map<String, String> getSamplePayloadDataCustom() {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("topic", "cute");
        pushData.put("title", "Notification for pending work-custom");
        pushData.put("message", "pls complete your pending task immediately-custom");
        pushData.put("image", "https://www.hartz.com/wp-content/uploads/2023/05/do-cats-only-meow-to-humans-1.jpg");
        pushData.put("timestamp", String.valueOf(new Date()));
        pushData.put("article_data", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        return pushData;
    }



    public void sendPushNotificationCustomDataWithTopicWithSpecificJson(PushNotificationRequest request) {
        try {
            fcmService.sendMessageCustomDataWithTopic(getSamplePayloadDataWithSpecificJsonFormat(), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Map<String, String> getSamplePayloadDataWithSpecificJsonFormat() {
        Map<String, String> pushData = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        ArrayList<Map<String, String>> payload = new ArrayList<>();
        Map<String, String> article_data = new HashMap<>();
        pushData.put("title", "jsonformat");
        pushData.put("message", "working");
        pushData.put("image", "https://www.hartz.com/wp-content/uploads/2023/05/do-cats-only-meow-to-humans-1.jpg");
        pushData.put("timestamp", String.valueOf(new Date()));
        article_data.put("article_data", "ffff");
        payload.add(article_data);
        pushData.put("payload", String.valueOf(payload));
        data.put("data", String.valueOf(pushData));
        return data;
    }

    public void sendPushNotificationWithoutData(PushNotificationRequest request) {
        try {
            fcmService.sendMessageWithoutData(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


}
