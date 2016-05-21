
package ws.monopoly;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eventType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="eventType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GameStart"/>
 *     &lt;enumeration value="GameOver"/>
 *     &lt;enumeration value="GameWinner"/>
 *     &lt;enumeration value="PlayerLost"/>
 *     &lt;enumeration value="DiceRoll"/>
 *     &lt;enumeration value="Move"/>
 *     &lt;enumeration value="PassedStartSquare"/>
 *     &lt;enumeration value="LandedOnStartSquare"/>
 *     &lt;enumeration value="GoToJail"/>
 *     &lt;enumeration value="PropmtPlayerToByAsset"/>
 *     &lt;enumeration value="PropmptPlayerToByHouse"/>
 *     &lt;enumeration value="AssetBought"/>
 *     &lt;enumeration value="HouseBought"/>
 *     &lt;enumeration value="SurpriseCard"/>
 *     &lt;enumeration value="WarrantCard"/>
 *     &lt;enumeration value="GetOutOfJailCard"/>
 *     &lt;enumeration value="Payment"/>
 *     &lt;enumeration value="PlayerUsedGetOutOfJailCard"/>
 *     &lt;enumeration value="PlayerTurn"/>
 *     &lt;enumeration value="PlayerResigned"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "eventType")
@XmlEnum
public enum EventType {

    @XmlEnumValue("GameStart")
    GAME_START("GameStart"),
    @XmlEnumValue("GameOver")
    GAME_OVER("GameOver"),
    @XmlEnumValue("GameWinner")
    GAME_WINNER("GameWinner"),
    @XmlEnumValue("PlayerLost")
    PLAYER_LOST("PlayerLost"),
    @XmlEnumValue("DiceRoll")
    DICE_ROLL("DiceRoll"),
    @XmlEnumValue("Move")
    MOVE("Move"),
    @XmlEnumValue("PassedStartSquare")
    PASSED_START_SQUARE("PassedStartSquare"),
    @XmlEnumValue("LandedOnStartSquare")
    LANDED_ON_START_SQUARE("LandedOnStartSquare"),
    @XmlEnumValue("GoToJail")
    GO_TO_JAIL("GoToJail"),
    @XmlEnumValue("PropmtPlayerToByAsset")
    PROPMT_PLAYER_TO_BY_ASSET("PropmtPlayerToByAsset"),
    @XmlEnumValue("PropmptPlayerToByHouse")
    PROPMPT_PLAYER_TO_BY_HOUSE("PropmptPlayerToByHouse"),
    @XmlEnumValue("AssetBought")
    ASSET_BOUGHT("AssetBought"),
    @XmlEnumValue("HouseBought")
    HOUSE_BOUGHT("HouseBought"),
    @XmlEnumValue("SurpriseCard")
    SURPRISE_CARD("SurpriseCard"),
    @XmlEnumValue("WarrantCard")
    WARRANT_CARD("WarrantCard"),
    @XmlEnumValue("GetOutOfJailCard")
    GET_OUT_OF_JAIL_CARD("GetOutOfJailCard"),
    @XmlEnumValue("Payment")
    PAYMENT("Payment"),
    @XmlEnumValue("PlayerUsedGetOutOfJailCard")
    PLAYER_USED_GET_OUT_OF_JAIL_CARD("PlayerUsedGetOutOfJailCard"),
    @XmlEnumValue("PlayerTurn")
    PLAYER_TURN("PlayerTurn"),
    @XmlEnumValue("PlayerResigned")
    PLAYER_RESIGNED("PlayerResigned");
    private final String value;

    EventType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventType fromValue(String v) {
        for (EventType c: EventType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
