package com.example.englishclub.entity.enums;

public enum ThemesType{
	IT("IT"),
	Marketing("Marketing") ,
	Traveling("Traveling"),
	Education("Education"),
	Healthcare("Healthcare"),
	Finance("Finance"),
	Engineering("Engineering"),
	Hospitality("Hospitality"),
	Sales("Sales"),
	ArtAndDesign("Art and Design"),
	Legal("Legal"),
	Science("Science"),
	CustomerService("Customer Service"),
	HumanResources("Human Resources"),
	Manufacturing("Manufacturing"),
	Agriculture("Agriculture"),
	MediaAndJournalism("Media and Journalism"),
	Fashion("Fashion"),
	RealEstate("Real Estate"),
	Nonprofit("Nonprofit"),
	Consulting("Consulting"),
	Research("Research"),
	Retail("Retail"),
	Construction("Construction"),
	Government("Government"),
	Automotive("Automotive"),
	Telecommunications("Telecommunications"),
	Music("Music"),
	Sports("Sports"),
	Entertainment("Entertainment"),
	Architecture("Architecture"),
	Pharmaceutical("Pharmaceutical"),
	Aerospace("Aerospace");
	private final String value;

	private ThemesType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}


