//package com.roydon.framework.component;
//
//import com.alibaba.fastjson2.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author websocket服务
// */
//@Slf4j
//@Component
//@ServerEndpoint(value = "/chat-server/{username}")
//public class WebSocketServer {
//
//    /**
//     * 记录当前在线连接数
//     */
//    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
//
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("username") String username) {
//        sessionMap.put(username, session);
//        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
//        ChatUserList userList = new ChatUserList();
//        Set<ChatUser> userSet = new HashSet<>();
//        for (String key : sessionMap.keySet()) {
//            ChatUser chatUser = new ChatUser(key);
//            userSet.add(chatUser);
//        }
//        userList.setUsers(userSet);
//        sendAllMessage(JSON.toJSONString(userList));  // 后台发送消息给所有的客户端{"users": [{"username": "zhang"},{ "username": "admin"}]}
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     * 后台收到客户端发送过来的消息
//     * onMessage 是一个消息的中转站
//     * 接受 浏览器端 socket.send 发送过来的 json数据
//     *
//     * @param message 客户端发送过来的消息
//     */
//    @OnMessage
//    public void onMessage(String message, Session session, @PathParam("username") String username) {
//        log.info("服务端收到用户username={}的消息:{}", username, message);
//        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);
//        String receiver = chatMessage.getReceiver(); // to表示发送给哪个用户，比如 roydon
//        String text = chatMessage.getContent(); // 发送的消息文本  hello
//        String type = chatMessage.getType(); // 发送的消息类型 0文本1图片
//        Long time = chatMessage.getTime(); // 发送的时间戳
//        // {"receiver": "roydon", "content": "聊天文本"}
//        Session receiverSession = sessionMap.get(receiver); // 根据 receiver 用户名来获取 session，再通过session发送消息文本
//        if (receiverSession != null) {
//            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
//            // {"sender": "common", "content": "hello"}
//            ChatMessage returnMessage = new ChatMessage();
//            returnMessage.setSender(username);
//            returnMessage.setReceiver(receiver);
//            returnMessage.setContent(text);
//            returnMessage.setSendType(false);
//            returnMessage.setType(type);
//            returnMessage.setTime(time);
//            this.sendMessage(JSON.toJSONString(returnMessage), receiverSession);
//            log.info("发送给用户{}，消息：{}", receiver, JSON.toJSONString(returnMessage));
//        } else {
//            log.info("发送失败，未找到用户{}的session，对方已离线", receiver);
//        }
//    }
//
////    @OnMessage
////    public void onMessage(String message, Session session, @PathParam("username") String username) {
////        log.info("服务端收到用户username={}的消息:{}", username, message);
////        JSONObject obj = JSONUtil.parseObj(message);
////        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
////        String text = obj.getStr("text"); // 发送的消息文本  hello
////        // {"to": "admin", "text": "聊天文本"}
////        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
////        if (toSession != null) {
////            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
////            // {"from": "zhang", "text": "hello"}
////            JSONObject jsonObject = new JSONObject();
////            jsonObject.set("from", username);  // from 是 zhang
////            jsonObject.set("text", text);  // text 同上面的text
////            this.sendMessage(jsonObject.toString(), toSession);
////            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
////        } else {
////            log.info("发送失败，未找到用户username={}的session", toUsername);
////        }
////    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose(Session session, @PathParam("username") String username) {
//        sessionMap.remove(username);
//        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
//    }
//
//    /**
//     * 发生错误
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 服务端发送消息给客户端
//     */
//    private void sendMessage(String message, Session receiverSession) {
//        try {
//            log.info("服务端给客户端[{}]发送消息{}", receiverSession.getId(), message);
//            receiverSession.getBasicRemote().sendText(message);
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
//        }
//    }
//
//    /**
//     * 服务端发送消息给所有客户端
//     */
//    private void sendAllMessage(String message) {
//        try {
//            for (Session session : sessionMap.values()) {
//                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
//                session.getBasicRemote().sendText(message);
//            }
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
//        }
//    }
//}
