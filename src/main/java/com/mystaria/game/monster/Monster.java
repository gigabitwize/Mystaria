package com.mystaria.game.monster;

import com.mystaria.game.core.entity.MystariaEntity;
import com.mystaria.game.core.instance.Location;
import com.mystaria.game.core.player.MystariaPlayer;
import com.mystaria.game.tier.Tier;
import net.kyori.adventure.text.Component;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
import net.minestom.server.entity.ai.goal.FollowTargetGoal;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomLookAroundGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.utils.time.TimeUnit;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

/**
 * Created by Giovanni on 1/31/2023
 */
public class Monster extends EntityCreature implements MystariaEntity {

    private final Tier tier;

    public Monster(Tier tier, String displayName, @NotNull EntityType entityType) {
        super(entityType);
        this.tier = tier;
        setCustomName(Component.text(displayName).color(tier.getColorData().getTextColor()));
        setCustomNameVisible(true);

        getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(2);
        getAttribute(Attribute.MAX_HEALTH).setBaseValue(100);
        heal();

        addAIGroup(new EntityAIGroupBuilder()
                .addGoalSelector(new RandomStrollGoal(this, 10))
                .addGoalSelector(new RandomLookAroundGoal(this, 50))
                .build());
    }

    /**
     * Spawns this monster.
     */
    @Override
    public void spawn(Location location) {
        if (isActive()) return;
        setInstance(location.getInstance(), location.getPosition());
    }

    @Override
    public void despawn() {
        if (!isActive()) return;
        remove();
    }

    public Tier getTier() {
        return tier;
    }
}
