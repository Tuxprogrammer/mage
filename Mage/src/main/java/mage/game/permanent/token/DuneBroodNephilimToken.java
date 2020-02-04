

package mage.game.permanent.token;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class DuneBroodNephilimToken extends TokenImpl {

    public DuneBroodNephilimToken() {
        super("Sand", "1/1 colorless Sand creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SAND);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
    public DuneBroodNephilimToken(final DuneBroodNephilimToken token) {
        super(token);
    }

    public DuneBroodNephilimToken copy() {
        return new DuneBroodNephilimToken(this);
    }
}
