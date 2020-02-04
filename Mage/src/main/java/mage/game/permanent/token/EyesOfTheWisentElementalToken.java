

package mage.game.permanent.token;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class EyesOfTheWisentElementalToken extends TokenImpl {

    public EyesOfTheWisentElementalToken() {
        super("Elemental", "4/4 green Elemental creature token");
        this.setOriginalExpansionSetCode("MMA");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        setTokenType(1);
    }

    public EyesOfTheWisentElementalToken(final EyesOfTheWisentElementalToken token) {
        super(token);
    }

    public EyesOfTheWisentElementalToken copy() {
        return new EyesOfTheWisentElementalToken(this);
    }
}
