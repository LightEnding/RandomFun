package com.lightending.blocks;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class BlockStatesDefinition {
    public static final BooleanProperty MENDING = BooleanProperty.of("mending");
    public static final BooleanProperty BINDING = BooleanProperty.of("binding");
    public static final BooleanProperty BLOOM = BooleanProperty.of("bloom");
    public static final IntProperty THORNS = IntProperty.of("thorns",0,3);

//    public cakeBlockModify(AbstractBlock.Settings settings) {
//        super(settings);
//        setDefaultState(getDefaultState().with(MENDING, false));
//    }
//
//    protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
//        if (!player.canConsume(false)) {
//            return ActionResult.PASS;
//        }
//        player.incrementStat(Stats.EAT_CAKE_SLICE);
//        player.getHungerManager().add(2, 0.1f);
//        int i = state.get(BITES);
//        world.emitGameEvent((Entity) player, GameEvent.EAT, pos);
//        if (i < 6) {
//            world.setBlockState(pos, (BlockState) state.with(BITES, i + 1), Block.NOTIFY_ALL);
//        } else {
//            world.removeBlock(pos, false);
//            world.emitGameEvent((Entity) player, GameEvent.BLOCK_DESTROY, pos);
//        }
//        if (world.getBlockState(pos).get(MENDING)) {
//            world.setBlockState(pos, state.with(BITES, 0), Block.NOTIFY_ALL);
//        }
//        return ActionResult.SUCCESS;
//    }
    public static void initialize(){
    }
}
