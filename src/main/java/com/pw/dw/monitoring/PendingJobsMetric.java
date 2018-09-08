package com.pw.dw.monitoring;

import com.codahale.metrics.Gauge;

public class PendingJobsMetric implements Gauge<Integer> {

    private int i = 0;
    private static final int MAX_COUNT = 20;

    public Integer getValue() {
        i++;
        return i % MAX_COUNT;
    }
}
