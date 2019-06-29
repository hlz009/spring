package com.xz.contexthierarchydemo.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Data
@Slf4j
public class TestBean {
	private String context;

    public void hello() {
        log.info("hello " + context);
    }
}
