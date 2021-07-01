package com.TripToEasy.model.parsingModel.transfers;

/**
 * Created by ptblr-1174 on 7/9/18.
 */

public class TransfersQuestionsInfo {
    String quesId,question,answer,subTitle,stringQuestionId;
    public TransfersQuestionsInfo(String quesId,String stringQuestionId,String question,String subTitle,String answer){
        this.quesId=quesId;
        this.stringQuestionId=stringQuestionId;
        this.question=question;
        this.subTitle=subTitle;
        this.answer=answer;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getStringQuestionId() {
        return stringQuestionId;
    }

    public void setStringQuestionId(String stringQuestionId) {
        this.stringQuestionId = stringQuestionId;
    }
}
