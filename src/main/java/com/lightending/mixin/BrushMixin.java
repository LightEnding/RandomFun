package com.lightending.mixin;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class BrushMixin {
//    @Inject(at = @At("TAIL"),method = "usageTick")


    /**
     * @author LightEnding
     * @reason 修改刷子
     */
    @Overwrite
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks){
        boolean bl;
        BlockHitResult blockHitResult;
        PlayerEntity playerEntity;
        block10: {
            block9: {
                if (remainingUseTicks < 0 || !(user instanceof PlayerEntity)) {
                    user.stopUsingItem();
                    return;
                }
                playerEntity = (PlayerEntity)user;
                HitResult hitResult = ((BrushInvoker)this).invokeGetHitResult(playerEntity);
                if (!(hitResult instanceof BlockHitResult)) break block9;
                blockHitResult = (BlockHitResult)hitResult;
                if (hitResult.getType() == HitResult.Type.BLOCK) break block10;
            }
            user.stopUsingItem();
            return;
        }
        int i = ((BrushInvoker)this).invokeGetMaxUseTime(stack) - remainingUseTicks + 1;
        boolean bl2 = bl = i % 10 == 5;
        if (bl) {
            Vec3d lookVec = playerEntity.getRotationVec(1.0f); // 获取玩家的视线方向向量

            // 确定刷取范围的宽度和长度
            int sweep = EnchantmentHelper.getLevel(Enchantments.SWEEPING,stack);
            int width = sweep;
            int length = sweep;

            // 计算刷取范围的起始位置
            BlockPos startPos = blockHitResult.getBlockPos();

            // 根据玩家的视线方向调整刷取范围的位置
            int offsetX;
            int offsetZ;
            int limX;
            int limZ;
            if(Math.abs(lookVec.x)>=Math.abs(lookVec.z)){
                offsetZ = (int)(Math.signum(lookVec.z)*width);
                offsetX = (int)(Math.signum(lookVec.x)*length);
                limX = width;
                limZ = length;
                startPos = startPos.add(offsetX, 0, offsetZ);
                for (int x = offsetX > 0 ? -offsetX : offsetX; x <= limX; x++) {
                    for (int z = offsetZ > 0 ? -(offsetZ*2+1) : (offsetZ*2+1); z <= limZ; z++) {
                        BlockPos blockPos = startPos.add(x, 0, z);
                        BlockState blockState = world.getBlockState(blockPos);
                        // 在这里执行刷取操作
                        Brush(world,user,stack,playerEntity,blockHitResult,blockState,blockPos);
                    }
                }
            }else {
                offsetZ = (int)(Math.signum(lookVec.z)*length);
                offsetX = (int)(Math.signum(lookVec.x)*width);
                limX = width;
                limZ = length;
                startPos = startPos.add(offsetX, 0, offsetZ);
                for (int x = offsetX > 0 ? -(offsetX*2+1) : (offsetX*2+1); x <= limX; x++) {
                    for (int z = offsetZ > 0 ? -offsetZ : offsetZ; z <= limZ; z++) {
                        BlockPos blockPos = startPos.add(x, 0, z);
                        BlockState blockState = world.getBlockState(blockPos);
                        // 在这里执行刷取操作
                        Brush(world,user,stack,playerEntity,blockHitResult,blockState,blockPos);
                    }
                }
            }
            // 遍历刷取范围内的方块

        }
    }
    private void Brush(World world, LivingEntity user, ItemStack stack, PlayerEntity playerEntity,BlockHitResult blockHitResult,BlockState blockState,BlockPos blockPos){
        BrushableBlockEntity brushableBlockEntity;
        boolean bl22;
        SoundEvent soundEvent;
        Object object;
        Arm arm;
        Arm arm2 = arm = user.getActiveHand() == Hand.MAIN_HAND ? playerEntity.getMainArm() : playerEntity.getMainArm().getOpposite();
        if (blockState.hasBlockBreakParticles() && blockState.getRenderType() != BlockRenderType.INVISIBLE) {
            ((BrushInvoker)this).invokeAddDustParticles(world, blockHitResult, blockState, user.getRotationVec(0.0f), arm);
        }
        if ((object = blockState.getBlock()) instanceof BrushableBlock) {
            BrushableBlock brushableBlock = (BrushableBlock)object;
            soundEvent = brushableBlock.getBrushingSound();
        } else {
            soundEvent = SoundEvents.ITEM_BRUSH_BRUSHING_GENERIC;
        }
        world.playSound(playerEntity, blockPos, soundEvent, SoundCategory.BLOCKS);
        if (!world.isClient() && (object = world.getBlockEntity(blockPos)) instanceof BrushableBlockEntity && (bl22 = (brushableBlockEntity = (BrushableBlockEntity)object).brush(world.getTime(), playerEntity, blockHitResult.getSide()))) {
            EquipmentSlot equipmentSlot = stack.equals(playerEntity.getEquippedStack(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            stack.damage(1, user, userx -> userx.sendEquipmentBreakStatus(equipmentSlot));
        }
    }
}
