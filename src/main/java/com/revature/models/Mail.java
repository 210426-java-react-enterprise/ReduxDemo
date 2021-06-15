package com.revature.models;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.revature.dtos.UserDTO;

public class Mail {
    private String mailFrom;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String mailSubject;
    private String mailContent;
    private String contentType;
    private  String attachmentString;
    private Map<String,Object> props;

    private List<Object> attachments;
    private Object obj;

    public String getAttactchmentResource() {
        return attactchmentResource;
    }

    public void setAttactchmentResource(String attactchmentResource) {
        this.attactchmentResource = attactchmentResource;
    }

    private String attactchmentResource;

    public void setAttachmentString(String attachmentString) {
        this.attachmentString = attachmentString;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setAttachmentResource(String attachmentResource) {
        this.attachmentResource = attachmentResource;
    }

    private String attachmentResource;

    public Mail(){
        contentType = "text/plain";
    }
    public void getContentType(){
        this.contentType = contentType;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailCc() {
        return mailCc;
    }

    public void setMailCc(String mailCc) {
        this.mailCc = mailCc;
    }

    public String getMailBcc() {
        return mailBcc;
    }

    public void setMailBcc(String mailBcc) {
        this.mailBcc = mailBcc;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentString() {
        return attachmentString;
    }
    public void setAttachmentString(){
        this.attachmentString = attachmentString;
    }

    public Map<String, Object> getProps() {
        return props;
    }

    public String getAttachmentResource() {
        return attachmentResource = attactchmentResource;
    }

    public void setProps(Map<String, Object> model) {
        this.props = props;
    }
}
