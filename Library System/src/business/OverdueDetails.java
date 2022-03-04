package business;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class OverdueDetails {
	
	
	private String isbn;
	private String title;
	private String memberId;
	private String memberName;
	private String memberLastName;
	private Date checkoutDate;
	private Date dueDate;
	private double lateFee;
	
	public OverdueDetails(String isbn, String title, String memberId, String memberName, String memberLastName, 
			Date checkoutDate, Date dueDate) {
		this.isbn = isbn;
		this.title = title;
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberLastName = memberLastName;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.lateFee = calculateLateFee();
	}
	
	private double calculateLateFee() {
		
		return Math.abs(0.25 * ChronoUnit.DAYS.between(convertDate(new Date()), convertDate(dueDate)));
	}
	
	public LocalDate convertDate(Date date) {
		return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberLastName() {
		return memberLastName;
	}

	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public double getLateFee() {
		return lateFee;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}
	
	
	
}
