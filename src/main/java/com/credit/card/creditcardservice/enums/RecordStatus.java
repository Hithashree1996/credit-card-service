package com.credit.card.creditcardservice.enums;

/**
 * Represents the status of a record.
 */
public enum RecordStatus {

	NEW("New"), APPROVED("Approved"), REJECTED("Rejected");

	private String displayName;

	/**
	 * Returns the display name of the record status.
	 *
	 * @return the display name of the record status
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Constructs a new instance of the {@code RecordStatus} enum with the specified display name.
	 *
	 * @param displayName the display name of the record status
	 */
	private RecordStatus(String displayName) {
		this.displayName = displayName;
	}

}
