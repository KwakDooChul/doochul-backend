package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.infra.FcmMessageSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FCMController {

    private final FcmMessageSender init;

    @GetMapping("/v1")
    public String v1(){
        init.initialize();
        return "index";
    }
}
