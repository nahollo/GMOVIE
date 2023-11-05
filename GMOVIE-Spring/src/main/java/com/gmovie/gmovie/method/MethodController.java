package com.gmovie.gmovie.method;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodController {

    @PostMapping("/executeJavaCode")
    public Map<String, String> executeJavaCode(@RequestBody MeetingRoomId meetingRoomId) {
        try {
            Method method = new Method();
            Map<String, String> result = method.summary(meetingRoomId.getId());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("error", "Error occurred");
            return errorResult;
        }
    }

    public static class MeetingRoomId {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}