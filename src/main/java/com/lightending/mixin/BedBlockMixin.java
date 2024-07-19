package com.lightending.mixin;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.lightending.blocks.BlockStatesDefinition.BINDING;
import static com.lightending.blocks.BlockStatesDefinition.THORNS;
import static net.minecraft.block.HorizontalFacingBlock.FACING;

@Mixin(BedBlock.class)
public class BedBlockMixin extends Block {
    public BedBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"),method = "appendProperties")//放置时添加blockstate
    private void injectAppend(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(BINDING);
        builder.add(THORNS);
    }

    @Inject(at = @At("RETURN"),method = "<init>")//设置默认blockstate
    private void injectBed(DyeColor color, Settings settings, CallbackInfo ci){
        setDefaultState(getDefaultState().with(BINDING, false));
        setDefaultState(getDefaultState().with(THORNS, 0));
    }

    @Inject(at = @At("RETURN"),method = "onPlaced")
    private void injectPlacement(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci){
            Direction facing = state.get(FACING);
            BlockPos headPos = pos.offset(facing);
            BlockState headState = world.getBlockState(headPos);
            if (headState.getBlock() instanceof BedBlock) {
                world.setBlockState(headPos, headState.with(BINDING, world.getBlockState(pos).get(BINDING))
                        .with(THORNS, world.getBlockState(pos).get(THORNS)), Block.NOTIFY_ALL);
                //设置另一半床的blockstate
            }
    }

    @Inject(at = @At("HEAD"),method = "onEntityLand")
    private void injectLand(BlockView world, Entity entity, CallbackInfo ci){
        World world1 = entity.getWorld();
        BlockPos pos = entity.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BedBlock && world.getBlockState(pos).get(THORNS) > 0){
            entity.damage(world1.getDamageSources().cactus(), world.getBlockState(pos).get(THORNS));
        }
    }

    @Inject(at = @At("RETURN"),method = "onUse")
    private void injectUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        if (state.getBlock() instanceof BedBlock && world.getBlockState(pos).get(THORNS) >= 0){
            player.damage(world.getDamageSources().cactus(), world.getBlockState(pos).get(THORNS));
        }
    }
}
