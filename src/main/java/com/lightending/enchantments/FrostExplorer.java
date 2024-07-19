package com.lightending.enchantments;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FrostExplorer extends Enchantment {

    protected FrostExplorer() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD});
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }//抄的冰霜行者

    @Override
    public int getMaxPower(int level) {
        return this.getMinPower(level) + 15;
    }//抄的冰霜行者

    @Override
    public int getMaxLevel() {
        return 2; // 设置最大附魔等级
    }

    @Override
    public boolean isTreasure() {
        return true; // 设置为宝藏附魔
    }

    @Override
    public boolean isCursed() {
        return true; // 标记为诅咒
    }

    public static void freezeWater(LivingEntity entity, World world, BlockPos blockPos, int level) {//抄的冰霜行者
//        if (!entity.isOnGround()) {
//            return;
//        }
        BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
        int i = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int j;
        if (entity.isSwimming()){j = 1;} else {j = 2;}//判断是否游泳
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, +j, -i), blockPos.add(i, +j, i))) {
            BlockState blockState3;
            if (!blockPos2.isWithinDistance(entity.getPos(), (double)i)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 = world.getBlockState(blockPos2)) != FrostedIceBlock.getMeltedState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getRandom(), 60, 120));
        }//冰冻
    }
}
