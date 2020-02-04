package mage.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.counters.Counters;

/**
 *
 * @author BetaSteward
 */
public class CardState implements Serializable {

    protected boolean faceDown;
    protected Map<String, String> info;
    protected Counters counters;
    protected Abilities<Ability> abilities;
    protected boolean lostAllAbilities;

    private static final Map<String, String> emptyInfo = new HashMap<>();
    private static final Abilities<Ability> emptyAbilities = new AbilitiesImpl<>();

    public CardState() {
        counters = new Counters();
    }

    public CardState(final CardState state) {
        this.faceDown = state.faceDown;
        if (state.info != null) {
            info = new HashMap<>();
            info.putAll(state.info);
        }
        counters = state.counters.copy();
        if (state.abilities != null) {
            abilities = new AbilitiesImpl<>();
            for (Ability ability : state.abilities) {
                abilities.add(ability.copy());
            }
        }
        this.lostAllAbilities = state.lostAllAbilities;
    }

    public CardState copy() {
        return new CardState(this);
    }

    public void setFaceDown(boolean value) {
        faceDown = value;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public Counters getCounters() {
        return counters;
    }

    public void addInfo(String key, String value) {
        if (info == null) {
            info = new HashMap<>();
        }
        if (value == null || value.isEmpty()) {
            info.remove(key);
        } else {
            info.put(key, value);
        }
    }

    public Map<String, String> getInfo() {
        if (info == null) {
            return emptyInfo;
        }
        return info;
    }

    public Abilities<Ability> getAbilities() {
        if (abilities == null) {
            return emptyAbilities;
        }
        return abilities;
    }

    public void addAbility(Ability ability) {
        if (abilities == null) {
            abilities = new AbilitiesImpl<>();
        }
        abilities.add(ability);
        abilities.addAll(ability.getSubAbilities());
    }

    /**
     * Called from applyEffects reset, to reset all layered effects
     */
    public void clearAbilities() {
        if (abilities != null) {
            abilities = null;
        }
        setLostAllAbilities(false);
    }

    public void clear() {
        counters.clear();
        info = null;
        clearAbilities();
        lostAllAbilities = false;
    }

    public boolean hasLostAllAbilities() {
        return lostAllAbilities;
    }

    public void setLostAllAbilities(boolean lostAllAbilities) {
        this.lostAllAbilities = lostAllAbilities;
    }

}
