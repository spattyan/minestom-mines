package com.yanspatt.scheduler;

import lombok.Data;

import java.util.UUID;

@Data
public class SchedulerJob {

    private UUID id;
    private long startedAt;
    private long runEvery;
    private long lastRun;
    private long endAt;

    public Runnable expires;

    public SchedulerJob(long expiresAt, long runEvery, Runnable expires) {
        this.id = UUID.randomUUID();
        this.startedAt = System.currentTimeMillis();
        this.expires = expires;
        this.endAt = expiresAt;
        this.runEvery = runEvery;
    }
}
