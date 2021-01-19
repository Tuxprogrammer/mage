package mage.cards.k;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KingHaraldsRevenge extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);

    public KingHaraldsRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Until end of tun, target creature gets +1/+1 for each creature you control and gains trample. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("until end of tun, target creature gets +1/+1 for each creature you control"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample."));
        this.getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn)
                .setText("It must be blocked this turn if able"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KingHaraldsRevenge(final KingHaraldsRevenge card) {
        super(card);
    }

    @Override
    public KingHaraldsRevenge copy() {
        return new KingHaraldsRevenge(this);
    }
}