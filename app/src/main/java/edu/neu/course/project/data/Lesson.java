package edu.neu.course.project.data;

public class Lesson {

    public String lessonName;
    public Long locked;
    public String imageLink;

    public  Lesson() {

    }

    public Lesson(String lessonName, Long locked, String imageLink) {
        this.lessonName = lessonName;
        this.locked = locked;
        this.imageLink = imageLink;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Long getLocked() {
        return locked;
    }

    public void setLocked(Long locked) {
        this.locked = locked;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lesson= " + lessonName + '\'' +
                ", locked='" + locked + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }

}
