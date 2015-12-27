package com.niffy.AndEngineLockStepEngine.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageMigrate extends Message {
	// ===========================================================
	// Constants
	// ===========================================================
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(MessageMigrate.class);

	// ===========================================================
	// Fields
	// ===========================================================
	// ===========================================================
	// Constructors
	// ===========================================================

	public MessageMigrate() {
		super();
	}

	public MessageMigrate(final int pIntended) {
		super(pIntended);
	}

	public MessageMigrate(final int pIntended, final int pFlag, final int pSequenceNumber) {
		super(pIntended, pFlag, pSequenceNumber);
	}

	public MessageMigrate(final int pFlag, final int pSequenceNumber) {
		super(pFlag, pSequenceNumber);
	}

	public MessageMigrate(Parcel in) {
		super(in);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream) throws IOException {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	public static final Parcelable.Creator<MessageMigrate> CREATOR = new Parcelable.Creator<MessageMigrate>() {

		@Override
		public MessageMigrate createFromParcel(Parcel source) {
			return new MessageMigrate(source);
		}

		@Override
		public MessageMigrate[] newArray(int size) {
			return new MessageMigrate[size];
		}
	};
}
