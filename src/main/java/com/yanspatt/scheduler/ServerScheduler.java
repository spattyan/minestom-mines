package com.yanspatt.scheduler;

import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.TaskSchedule;

import java.util.LinkedList;
import java.util.UUID;

public class ServerScheduler {

    private LinkedList<SchedulerJob> schedulerJobs;

    public ServerScheduler() {
        schedulerJobs = new LinkedList<>();
    }

    public void addJob(SchedulerJob schedulerJob) {
        this.schedulerJobs.add(schedulerJob);
    }

    public void removeJob(UUID uuid) {
        schedulerJobs.remove(schedulerJobs.stream().filter(job -> job.getId() == uuid).findFirst().orElse(null));
    }

    public void scheduleJobs() {
        MinecraftServer.getSchedulerManager().scheduleTask(() ->{

            schedulerJobs.forEach(schedulerJob -> {
                if ((System.currentTimeMillis() > (schedulerJob.getLastRun() + schedulerJob.getRunEvery()))) {

                    schedulerJob.getExpires().run();
                    schedulerJob.setLastRun(System.currentTimeMillis());
                    if (schedulerJob.getEndAt() > -1 && (System.currentTimeMillis() >= schedulerJob.getEndAt())) {
                        removeJob(schedulerJob.getId());
                    }
                }
            });

        }, TaskSchedule.tick(10), TaskSchedule.tick(1));
    }
}
