package com.lightending.blocks.entities;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkCatalystBlock;
import net.minecraft.block.entity.BlockEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

import static com.lightending.blocks.BlockStatesDefinition.BLOOM;

public class CakeEntity
        extends BlockEntity
        implements GameEventListener.Holder<CakeEntity.Listener> {
    private final Listener eventListener;

    public CakeEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.CAKE_ENTITY, pos, state);
        this.eventListener = new CakeEntity.Listener(state, new BlockPositionSource(pos));
    }

    public static void tick(World world, BlockPos pos, BlockState state, CakeEntity blockEntity) {
    }


    @Override
    public void readNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public Listener getEventListener() {
        return this.eventListener;
    }

    public class Listener
            implements GameEventListener {
        public static final int RANGE = 8;
        private final BlockState state;
        private final PositionSource positionSource;

        public Listener(BlockState state, PositionSource positionSource) {
            this.state = state;
            this.positionSource = positionSource;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public int getRange() {
            return 8;
        }

        @Override
        public boolean listen(ServerWorld world, GameEvent event, GameEvent.Emitter emitter, Vec3d emitterPos) {
            if (world.getBlockState(pos).get(BLOOM)) {
                Entity entity;
                if (event == GameEvent.ENTITY_DIE && (entity = emitter.sourceEntity()) instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if (!livingEntity.isExperienceDroppingDisabled()) {
                        int i = livingEntity.getXpToDrop();
                        if (livingEntity.shouldDropXp() && i > 0) {
                            world.setBlockState(BlockPos.ofFloored(emitterPos.offset(Direction.UP, 0.5)), state);
                            this.triggerCriteria(world, livingEntity);
                        }
                        livingEntity.disableExperienceDropping();
                        this.positionSource.getPos(world).ifPresent(pos -> this.bloom(world, BlockPos.ofFloored(pos), this.state, world.getRandom()));
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public TriggerOrder getTriggerOrder() {
            return GameEventListener.super.getTriggerOrder();
        }
        private void bloom(ServerWorld world, BlockPos pos, BlockState state, Random random) {
//            world.setBlockState(pos, (BlockState)state.with(SculkCatalystBlock.BLOOM, true), Block.NOTIFY_ALL);
            world.scheduleBlockTick(pos, state.getBlock(), 8);
            world.spawnParticles(ParticleTypes.HEART, (double)pos.getX() + 0.5, (double)pos.getY() + 1.15, (double)pos.getZ() + 0.5, 2, 0.2, 0.0, 0.2, 0.0);
            world.playSound(null, pos, SoundEvents.BLOCK_SCULK_CATALYST_BLOOM, SoundCategory.BLOCKS, 2.0f, 0.6f + random.nextFloat() * 0.4f);
        }

        private void triggerCriteria(World world, LivingEntity deadEntity) {
            LivingEntity livingEntity = deadEntity.getAttacker();
            if (livingEntity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)livingEntity;
                DamageSource damageSource = deadEntity.getRecentDamageSource() == null ? world.getDamageSources().playerAttack(serverPlayerEntity) : deadEntity.getRecentDamageSource();
                Criteria.KILL_MOB_NEAR_SCULK_CATALYST.trigger(serverPlayerEntity, deadEntity, damageSource);
            }
        }
    }
}
