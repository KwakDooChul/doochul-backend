package org.doochul.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/logging")
    public void home() {
        System.out.println("LogingController loging");
        log.trace("TRACE 로그!!");
        log.debug("DEBUG 로그!!");
        log.info("INFO 로그!!");
        log.warn("WARN 로그!!");
        log.error("ERROR 로그!!");
    }
}
