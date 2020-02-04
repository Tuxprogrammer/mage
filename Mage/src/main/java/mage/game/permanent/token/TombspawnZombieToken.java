
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class TombspawnZombieToken extends TokenImpl {

    public TombspawnZombieToken() {
        super("Tombspawn", "2/2 black Zombie creature token with haste named Tombspawn");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ZOMBIE);
        color.setBlack(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(HasteAbility.getInstance());
    }

    public TombspawnZombieToken(final TombspawnZombieToken token) {
        super(token);
    }

    public TombspawnZombieToken copy() {
        return new TombspawnZombieToken(this);
    }
}
