
package ws.monopoly;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for event complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="event">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="boardSquareID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="eventMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="firstDiceResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nextBoardSquareID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="paymemtFromUser" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="paymentAmount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="paymentToPlayerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="playerMove" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="playerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondDiceResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timeout" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://monopoly.ws/}eventType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "event", propOrder = {
    "boardSquareID",
    "eventMessage",
    "firstDiceResult",
    "id",
    "nextBoardSquareID",
    "paymemtFromUser",
    "paymentAmount",
    "paymentToPlayerName",
    "playerMove",
    "playerName",
    "secondDiceResult",
    "timeout",
    "type"
})
public class Event {

    protected int boardSquareID;
    protected String eventMessage;
    protected int firstDiceResult;
    protected int id;
    protected int nextBoardSquareID;
    protected boolean paymemtFromUser;
    protected int paymentAmount;
    protected String paymentToPlayerName;
    protected boolean playerMove;
    protected String playerName;
    protected int secondDiceResult;
    protected int timeout;
    protected EventType type;

    /**
     * Gets the value of the boardSquareID property.
     * 
     */
    public int getBoardSquareID() {
        return boardSquareID;
    }

    /**
     * Sets the value of the boardSquareID property.
     * 
     */
    public void setBoardSquareID(int value) {
        this.boardSquareID = value;
    }

    /**
     * Gets the value of the eventMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventMessage() {
        return eventMessage;
    }

    /**
     * Sets the value of the eventMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventMessage(String value) {
        this.eventMessage = value;
    }

    /**
     * Gets the value of the firstDiceResult property.
     * 
     */
    public int getFirstDiceResult() {
        return firstDiceResult;
    }

    /**
     * Sets the value of the firstDiceResult property.
     * 
     */
    public void setFirstDiceResult(int value) {
        this.firstDiceResult = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the nextBoardSquareID property.
     * 
     */
    public int getNextBoardSquareID() {
        return nextBoardSquareID;
    }

    /**
     * Sets the value of the nextBoardSquareID property.
     * 
     */
    public void setNextBoardSquareID(int value) {
        this.nextBoardSquareID = value;
    }

    /**
     * Gets the value of the paymemtFromUser property.
     * 
     */
    public boolean isPaymemtFromUser() {
        return paymemtFromUser;
    }

    /**
     * Sets the value of the paymemtFromUser property.
     * 
     */
    public void setPaymemtFromUser(boolean value) {
        this.paymemtFromUser = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     */
    public int getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     */
    public void setPaymentAmount(int value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the paymentToPlayerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentToPlayerName() {
        return paymentToPlayerName;
    }

    /**
     * Sets the value of the paymentToPlayerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentToPlayerName(String value) {
        this.paymentToPlayerName = value;
    }

    /**
     * Gets the value of the playerMove property.
     * 
     */
    public boolean isPlayerMove() {
        return playerMove;
    }

    /**
     * Sets the value of the playerMove property.
     * 
     */
    public void setPlayerMove(boolean value) {
        this.playerMove = value;
    }

    /**
     * Gets the value of the playerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the value of the playerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlayerName(String value) {
        this.playerName = value;
    }

    /**
     * Gets the value of the secondDiceResult property.
     * 
     */
    public int getSecondDiceResult() {
        return secondDiceResult;
    }

    /**
     * Sets the value of the secondDiceResult property.
     * 
     */
    public void setSecondDiceResult(int value) {
        this.secondDiceResult = value;
    }

    /**
     * Gets the value of the timeout property.
     * 
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the value of the timeout property.
     * 
     */
    public void setTimeout(int value) {
        this.timeout = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setType(EventType value) {
        this.type = value;
    }

}
