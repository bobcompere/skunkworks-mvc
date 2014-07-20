package net.fourstrategery.cloud.email;

public class MailInfo {

	private String toAddresses;
	private String fromAddress;
	private String ccAddresses;
	private String bccAddresses;
	private String subject;
	private String messageBody;
	
	public String getToAddresses() {
		return toAddresses;
	}
	public void setToAddresses(String toAddresses) {
		this.toAddresses = toAddresses;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getCcAddresses() {
		return ccAddresses;
	}
	public void setCcAddresses(String ccAddresses) {
		this.ccAddresses = ccAddresses;
	}
	public String getBccAddresses() {
		return bccAddresses;
	}
	public void setBccAddresses(String bccAddresses) {
		this.bccAddresses = bccAddresses;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
}
