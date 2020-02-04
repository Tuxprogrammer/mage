

package mage.game.permanent.token;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class RedHumanToken extends TokenImpl {

    public RedHumanToken() {
        super("Human", "1/1 red Human creature token");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HUMAN);

        this.color = ObjectColor.RED;
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    public RedHumanToken(final RedHumanToken token) {
        super(token);
    }

    public RedHumanToken copy() {
        return new RedHumanToken(this);
    }
}
