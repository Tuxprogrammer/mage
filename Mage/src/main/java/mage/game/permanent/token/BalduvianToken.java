

package mage.game.permanent.token;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class BalduvianToken extends TokenImpl {

    public BalduvianToken() {
        super("Graveborn", "3/1 black and red Graveborn creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(1);
        subtype.add(SubType.GRAVEBORN);
        addAbility(HasteAbility.getInstance());
    }

    public BalduvianToken(final BalduvianToken token) {
        super(token);
    }

    public BalduvianToken copy() {
        return new BalduvianToken(this);
    }
}
