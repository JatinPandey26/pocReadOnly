package com.jatinpandey.readonlypoc.readOnlyPocTry2;

import com.jatinpandey.readonlypoc.TestService;
import com.jatinpandey.readonlypoc.TestUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/read-write")
    public String testReadWriteOperation() {
        testService.performReadWriteOperation();
        return "Read-Write Operation Successful";
    }

    @GetMapping("/read-only")
    public String testReadOnlyOperation() {
        List<TestUser> testUsers = testService.performReadOnlyOperation();
        log.info("testUsers: {}", testUsers);
        return "Read-Only Operation Successful";
    }
}
