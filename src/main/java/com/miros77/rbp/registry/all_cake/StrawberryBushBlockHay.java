package com.miros77.rbp.registry.all_cake;

//Item
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

//Block

import net.minecraft.block.Material;
import net.minecraft.block.SweetBerryBushBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import javax.swing.text.html.BlockView;
import net.minecraft.block.BlockState;

//My
import com.miros77.rbp.registry.ModItems;

//Sound
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.BlockSoundGroup;

//Fabric API
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

//Util
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

//Entity
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;

//World
import net.minecraft.world.World;


public class StrawberryBushBlockHay extends SweetBerryBushBlock {

  public StrawberryBushBlockHay() {
    super(FabricBlockSettings.of(Material.PLANT).ticksRandomly().noCollision().sounds(BlockSoundGroup.SWEET_BERRY_BUSH).nonOpaque());
  }

  @Environment(EnvType.CLIENT)
  public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
    return new ItemStack(ModItems.STRAWBERRY_BIG);
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    if (entity instanceof LivingEntity) {
      entity.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
      if (!world.isClient && (Integer) state.get(AGE) > 0
          && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())
          && entity instanceof HostileEntity) {
        double d = Math.abs(entity.getX() - entity.lastRenderX);
        double e = Math.abs(entity.getZ() - entity.lastRenderZ);
        if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
          entity.damage(DamageSource.SWEET_BERRY_BUSH, 1.0F);
        }
      }
    }
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
      BlockHitResult hit) {
    int i = (Integer) state.get(AGE);
    boolean bl = i == 3;
    if (!bl && player.getStackInHand(hand).getItem() == Items.BONE_MEAL) {
      return ActionResult.PASS;
    } else if (i > 1) {
      int j = 1 + world.random.nextInt(2);
      dropStack(world, pos, new ItemStack(ModItems.STRAWBERRY_BIG, j + (bl ? 1 : 0)));
      world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS,
          1.0F, 0.8F + world.random.nextFloat() * 0.4F);
      world.setBlockState(pos, (BlockState) state.with(AGE, 1), 2);
      return ActionResult.SUCCESS;
    } else {
      return super.onUse(state, world, pos, player, hand, hit);
    }
  }
}