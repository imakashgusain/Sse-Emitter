package com.example.sseemitter.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@RestController
public class NewsController {

    public List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    // Method for client subscription
    @CrossOrigin
    @GetMapping("/subscribe")
    public SseEmitter subscribe() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        sseEmitter.onCompletion(()->emitters.remove(sseEmitter));
        emitters.add(sseEmitter);
        return sseEmitter;
    }


    // method for dispatching events to all clients
    @PostMapping("/dispatchEvent")
    public void dispatchEvent(@RequestParam String testtitle,@RequestParam String text) throws JSONException {
        String myData = new JSONObject().put("testtitle",testtitle)
                .put("text",text).toString();

        for (SseEmitter emitter: emitters) {
            try {
                emitter.send(SseEmitter.event().name("testTitle").data(myData));
            } catch (IOException e) {
              emitters.remove(emitter);
            }
        }

    }



}
