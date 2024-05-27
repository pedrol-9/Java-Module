package com.mindhub.homebanking.DTOs;

public class CreateCardDTO {
  private String cardType;
  private String cardColor;

  public CreateCardDTO() {
  }

  public CreateCardDTO(String cardType, String cardColor) {
    this.cardType = cardType;
    this.cardColor = cardColor;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCardColor() {
    return cardColor;
  }

  public void setCardColor(String cardColor) {
    this.cardColor = cardColor;
  }
}
