package com.miros77.rbp.registry.all_cake.Pizza;

//Item
import net.minecraft.item.ItemStack;

//Block
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;

//Sound
import net.minecraft.sound.BlockSoundGroup;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;

//Util
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

//Entity
import net.minecraft.entity.player.PlayerEntity;

//Stats
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;

//World

import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

//New
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

//Sound

public class RawPizza extends CakeBlock {

	public RawPizza() {
      super(FabricBlockSettings.of(Material.CAKE).ticksRandomly().sounds(BlockSoundGroup.WOOL).nonOpaque());
      this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(BITES, 0));
	}
   
   public static final IntProperty BITES;
   protected static final VoxelShape[] BITES_TO_SHAPE;
   
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return BITES_TO_SHAPE[(Integer)state.get(BITES)];
   }

 //Test
//	@Override
//	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
//		stateManager.add(Properties.HORIZONTAL_FACING);
//	}
// 
//	@Override
//	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ctx) {
//		Direction dir = state.get(BITES);
//		switch(dir) {
//			case NORTH:
//				return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
//			case SOUTH:
//				return VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
//			case EAST:
//				return VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
//			case WEST:
//				return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
//			default:
//				return VoxelShapes.fullCube();
//		}
//	}
// 
//	public BlockState getPlacementState(ItemPlacementContext ctx) {
//		return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing());
//	}
//Test


   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (world.isClient) {  
         
         ItemStack itemStack = player.getStackInHand(hand);
         if (this.tryEat(world, pos, state, player).isAccepted()) {
            return ActionResult.SUCCESS;
         }

         if (itemStack.isEmpty()) {
            return ActionResult.CONSUME;
         }
      }

      return this.tryEat(world, pos, state, player);
   }

   public ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!player.canConsume(false)) {
         return ActionResult.PASS;
      } else {
         player.incrementStat(Stats.EAT_CAKE_SLICE);
         player.getHungerManager().add(2, 0.2F);
         player.applyStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 0));
 
         int i = (Integer)state.get(BITES);
         if (i < 6) {
            world.setBlockState(pos, (BlockState)state.with(BITES, i + 1), 3);
         } else {
            world.removeBlock(pos, false);
         }
 
         return ActionResult.SUCCESS;
      }

   }

   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return world.getBlockState(pos.down()).getMaterial().isSolid();
   }

   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(BITES);
   }

   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return (7 - (Integer)state.get(BITES)) * 2;
   }

   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   static {
      BITES = Properties.BITES;
      BITES_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D), Block.createCuboidShape(3.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D), Block.createCuboidShape(5.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D), Block.createCuboidShape(7.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D), Block.createCuboidShape(9.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D), Block.createCuboidShape(11.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D), Block.createCuboidShape(13.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D)};
   }
   
}

  