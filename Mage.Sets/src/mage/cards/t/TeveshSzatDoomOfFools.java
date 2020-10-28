package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BreedingPitThrullToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class TeveshSzatDoomOfFools extends CardImpl {

    public TeveshSzatDoomOfFools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SZAT);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +2: Create two 0/1 black Thrull creature tokens.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new BreedingPitThrullToken(), 2), 2));

        // +1: You may sacrifice another creature or planeswalker. If you do, draw two cards, then draw another card if the sacrificed permanent was a commander.
        this.addAbility(new LoyaltyAbility(new TeveshSzatDoomOfFoolsSacrificeEffect(), 1));

        // −10: Gain control of all commanders. Put all commanders from the commander zone onto the battlefield under your control.
        this.addAbility(new LoyaltyAbility(new TeveshSzatDoomOfFoolsCommanderEffect(), -10));

        // Tevesh Szat, Doom of Fools can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private TeveshSzatDoomOfFools(final TeveshSzatDoomOfFools card) {
        super(card);
    }

    @Override
    public TeveshSzatDoomOfFools copy() {
        return new TeveshSzatDoomOfFools(this);
    }
}

class TeveshSzatDoomOfFoolsSacrificeEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("another creature or planeswalker you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    TeveshSzatDoomOfFoolsSacrificeEffect() {
        super(Benefit);
        staticText = "you may sacrifice another creature or planeswalker. If you do, draw two cards, " +
                "then draw another card if the sacrificed permanent was a commander";
    }

    private TeveshSzatDoomOfFoolsSacrificeEffect(final TeveshSzatDoomOfFoolsSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public TeveshSzatDoomOfFoolsSacrificeEffect copy() {
        return new TeveshSzatDoomOfFoolsSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            return false;
        }
        target.choose(outcome, source.getControllerId(), source.getSourceId(), game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        boolean isCommander = game.getCommandersIds(
                game.getPlayer(permanent.getControllerId()),
                CommanderCardType.COMMANDER_OR_OATHBREAKER
        ).contains(permanent.getId());
        if (!permanent.sacrifice(source.getSourceId(), game)) {
            return false;
        }
        controller.drawCards(2, source.getSourceId(), game);
        if (isCommander) {
            controller.drawCards(1, source.getSourceId(), game);
        }
        return true;
    }
}

class TeveshSzatDoomOfFoolsCommanderEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CommanderPredicate.instance);
    }

    TeveshSzatDoomOfFoolsCommanderEffect() {
        super(Outcome.Benefit);
        staticText = "Gain control of all commanders. Put all commanders " +
                "from the command zone onto the battlefield under your control.";
    }

    private TeveshSzatDoomOfFoolsCommanderEffect(final TeveshSzatDoomOfFoolsCommanderEffect effect) {
        super(effect);
    }

    @Override
    public TeveshSzatDoomOfFoolsCommanderEffect copy() {
        return new TeveshSzatDoomOfFoolsCommanderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        )) {
            game.addEffect(new GainControlTargetEffect(
                    Duration.Custom, true
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        Set<UUID> commanders = game
                .getPlayerList()
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER))
                .flatMap(Collection::stream)
                .filter(uuid -> game.getState().getZone(uuid) == Zone.COMMAND)
                .collect(Collectors.toSet());
        controller.moveCards(new CardsImpl(commanders), Zone.BATTLEFIELD, source, game);
        return true;
    }
}