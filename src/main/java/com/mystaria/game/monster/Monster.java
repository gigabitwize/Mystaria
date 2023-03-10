package com.mystaria.game.monster;

import com.mystaria.game.core.entity.MystariaEntity;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.instance.MystariaInstanceContainer;
import com.mystaria.game.tier.Tier;
import net.kyori.adventure.text.Component;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Giovanni on 1/31/2023
 */
public class Monster extends EntityCreature implements MystariaEntity {

    private final Tier tier;
    private final MonsterEquipment monsterEquipment;

    public Monster(Tier tier, String displayName, @NotNull EntityType entityType) {
        super(entityType);
        this.tier = tier;
        setCustomName(Component.text(displayName).color(tier.getColorData().getTextColor()));
        setCustomNameVisible(true);

        getAttribute(Attribute.MAX_HEALTH).setBaseValue(100);

        addAIGroup(new EntityAIGroupBuilder()
                .addGoalSelector(new RandomStrollGoal(this, 3))
                .addGoalSelector(new RandomLookAroundGoal(this, 50))
                .build());

        this.monsterEquipment = new MonsterEquipment(this);
    }

    /**
     * Spawns this monster.
     */
    @Override
    public void spawn(Location location) {
        if (isActive()) return;
        setInstance(location.getInstance(), location.getPosition());
        heal();
    }

    @Override
    public void despawn() {
        if (!isActive()) return;
        remove();
    }

    @Override
    public Location getLocation() {
        return new Location(getInstance(), getPosition());
    }

    @Override
    public @Nullable MystariaInstanceContainer getInstance() {
        return (MystariaInstanceContainer) super.getInstance();
    }


    public MonsterEquipment getMonsterEquipment() {
        return monsterEquipment;
    }

    public Tier getTier() {
        return tier;
    }
}
