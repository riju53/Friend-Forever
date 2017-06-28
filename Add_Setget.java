package com.example.rijunath.friendforever;

/**
 * Created by RIJU NATH on 5/4/2017.
 */
public class Add_Setget {
    public String fname;
    public String lname;
    public String Email;
    public String Address;
    public String Pwd;
    public String RequestId;
    public String MsgId;
    public String ToEmail;
    public String FromEmail;
    public String MsgBody;
    public String Time;
    public String AttachmentPath;
    public String ReadUnreadFlag;
    public String IncomingOutGoing;


    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getToEmail() {
        return ToEmail;
    }

    public void setToEmail(String toEmail) {
        ToEmail = toEmail;
    }

    public String getFromEmail() {
        return FromEmail;
    }

    public void setFromEmail(String fromEmail) {
        FromEmail = fromEmail;
    }

    public String getMsgBody() {
        return MsgBody;
    }

    public void setMsgBody(String msgBody) {
        MsgBody = msgBody;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAttachmentPath() {
        return AttachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        AttachmentPath = attachmentPath;
    }

    public String getReadUnreadFlag() {
        return ReadUnreadFlag;
    }

    public void setReadUnreadFlag(String readUnreadFlag) {
        ReadUnreadFlag = readUnreadFlag;
    }

    public String getIncomingOutGoing() {
        return IncomingOutGoing;
    }

    public void setIncomingOutGoing(String incomingOutGoing) {
        IncomingOutGoing = incomingOutGoing;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String pwd) {
      this.Pwd = pwd;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }
}
