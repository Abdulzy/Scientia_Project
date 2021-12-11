package edu.neu.course.project;


public class QuestionData {

    private String qTitle;
    private String qQuestion;
    private String ans;
    private String option1;
    private String option2;
    private String option3;

    public QuestionData() {

    }

    public QuestionData(String qQuestion, String ans, String option1, String option2, String option3) {

        this.qQuestion = qQuestion;
        this.ans = ans;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getqQuestion() {
        return qQuestion;
    }

    public void setqQuestion(String qQuestion) {
        this.qQuestion = qQuestion;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }
}
