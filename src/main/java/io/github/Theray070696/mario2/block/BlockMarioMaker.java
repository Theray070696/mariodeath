package io.github.Theray070696.mario2.block;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.lib.GuiIds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 3/31/2016.
 */
public class BlockMarioMaker extends BlockMario
{
    public BlockMarioMaker()
    {
        super();

        this.setTranslationKey("marioBlockMarioMaker");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing side,
                                    float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
        {
            return false;
        } else
        {
            if(!world.isRemote)
            {
                player.openGui(MarioMod2.INSTANCE, GuiIds.MARIO_MAKER_GUI_ID, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            }

            return true;
        }
    }
}