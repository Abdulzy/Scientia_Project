package edu.neu.course.project;

public class Progress {


    private Long progress;
    private String completed;

    public Progress(Long progressValue, String completedValue) {
        this.progress = progressValue;
        this.completed = completedValue;
    }
    public Progress() {

    }

    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progressValue) {
        this.progress = progressValue;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
