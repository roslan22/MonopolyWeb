package com.monopoly.logic.model.card;

public abstract class Card
{
    private String cardText;

    public Card(String cardText)
    {
        this.cardText = cardText;
    }

    public String getCardText()
    {
        return cardText;
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o;
    }

    @Override
    public int hashCode()
    {
        return getCardText() != null ? getCardText().hashCode() : 0;
    }
}
