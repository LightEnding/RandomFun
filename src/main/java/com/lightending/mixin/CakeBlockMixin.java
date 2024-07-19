package com.lightending.mixin;

import com.lightending.blocks.entities.CakeEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lightending.blocks.BlockStatesDefinition.BLOOM;
import static com.lightending.blocks.BlockStatesDefinition.MENDING;
import static net.minecraft.block.CakeBlock.BITES;
import static net.minecraft.entity.EntityType.EXPERIENCE_ORB;

@Mixin(CakeBlock.class)
public class CakeBlockMixin extends Block
        implements BlockEntityProvider {
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CakeEntity(pos, state);
    }
    public CakeBlockMixin(Settings settings) {
        super(settings);
    }//构造

    @Inject(at = @At("HEAD"),method = "appendProperties")//放置时添加blockstate
    private void injectAppend(StateManager.Builder<Block, BlockState> builder,CallbackInfo info) {
        builder.add(MENDING);
        builder.add(BLOOM);
    }

    @Inject(at = @At("RETURN"),method = "<init>")//设置默认blockstate
    private void InjectCake(Settings settings, CallbackInfo ci){
        setDefaultState(getDefaultState().with(MENDING, false));
        setDefaultState(getDefaultState().with(BLOOM, false));
    }
//    @Inject(at = @At("RETURN"),method = "tryEat")
//    private static void InjectTryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir){
//        if (world.getBlockState(pos).get(MENDING)) {
//            world.setBlockState(pos, state.with(BITES, 0), Block.NOTIFY_ALL);
//        }
//    }
    @Override//碰撞
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient) {//抄的，不懂
            return;
        }
        if (world.getBlockState(pos).get(MENDING)&&world.getBlockState(pos).get(BITES)!=0) {//判断附魔和是否完整
            if(entity.getType() == EXPERIENCE_ORB){//判断经验球
                world.setBlockState(pos, state.with(BITES, 0), Block.NOTIFY_ALL);//重置蛋糕
                entity.kill();//清除经验球
            }
        }
    }

}
