package com.pw.dw.monitoring;

import com.codahale.metrics.health.HealthCheck;

public class DemoHealthCheck extends HealthCheck {

    private int status = 0;

    @Override
    protected Result check() {
        if (status ++ % 2 == 0) {
            return Result.unhealthy("Fail");
        }
        return Result.healthy();
    }
}
