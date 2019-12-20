/*
 * @file BlockDecorWindow.java
 * @author Stefan Wilhelm (wile)
 * @copyright (C) 2019 Stefan Wilhelm
 * @license MIT (see https://opensource.org/licenses/MIT)
 *
 * Mod windows.
 */
package wile.engineersdecor.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;

public class BlockDecorWindow extends BlockDecorDirected implements IWaterLoggable
{
  public BlockDecorWindow(long config, Block.Properties builder, final AxisAlignedBB unrotatedAABB)
  { super(config|CFG_WATERLOGGABLE, builder, unrotatedAABB); }

  @Override
  public RenderTypeHint getRenderTypeHint()
  { return RenderTypeHint.TRANSLUCENT; }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
  { super.fillStateContainer(builder); builder.add(WATERLOGGED); }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockItemUseContext context)
  {
    Direction facing = context.getPlacementHorizontalFacing();
    if(Math.abs(context.getPlayer().getLookVec().y) > 0.9) {
      facing = context.getNearestLookingDirection();
    } else {
      for(Direction f: Direction.values()) {
        BlockState st = context.getWorld().getBlockState(context.getPos().offset(f));
        if(st.getBlock() == this) {
          facing = st.get(FACING);
          break;
        }
      }
    }
    return super.getStateForPlacement(context).with(FACING, facing);
  }

}