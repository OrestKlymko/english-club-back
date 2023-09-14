package com.example.englishclub.user.entity.enums;

public enum LevelEnglish {
	Beginner("Beginner"),
	Intermediate("Intermediate"),
	UpperIntermediate("Upper-Intermediate"),
	Advanced("Advanced"),
	Mastery("Mastery");

	private final String value;

	private LevelEnglish(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
