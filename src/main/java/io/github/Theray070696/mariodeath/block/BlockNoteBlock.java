package io.github.Theray070696.mariodeath.block;

import io.github.Theray070696.mariodeath.audio.SoundHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 12/19/2016.
 */
public class BlockNoteBlock extends BlockMario
{
    public BlockNoteBlock()
    {
        super();

        this.setUnlocalizedName("marioBlockNoteBlock");
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState blockState, Entity entity)
    {
        if(entity.motionY < -0.1D)
        {
            world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundHandler.noteBlock, SoundCategory.BLOCKS, 1.0F, 1.0F);

            entity.motionY *= -2.0D;
        }
        entity.fallDistance = 0;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos blockPos)
    {
        return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), (double) blockPos.getX() + 1.0D, (double) blockPos.getY() + 0.625D, (double) blockPos.getZ() + 1.0D);
    }
}
