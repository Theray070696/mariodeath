package io.github.Theray070696.mariodeath.world.gen;

import io.github.Theray070696.mariodeath.block.BlockMario;
import io.github.Theray070696.mariodeath.block.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 8/22/2017
 */
public class WorldGenMarioTree extends WorldGenerator
{

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        int height = rand.nextInt(3) + 4;
        boolean flag = true;

        if(pos.getY() >= 1 && pos.getY() + height + 1 <= world.getHeight())
        {
            for(int j = pos.getY(); j <= pos.getY() + 1 + height; ++j)
            {
                int k = 1;

                if(j == pos.getY())
                {
                    k = 0;
                }

                if(j >= pos.getY() + 1 + height - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

                for(int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l)
                {
                    for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1)
                    {
                        if(j >= 0 && j < world.getHeight())
                        {
                            IBlockState state = world.getBlockState(mutableBlockPos.setPos(l, j, i1));

                            if(state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood
                                    (world, pos))
                            {
                                flag = false;
                            }
                        } else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if(!flag)
            {
                return false;
            } else
            {
                IBlockState state = world.getBlockState(pos.down());

                if(state.getBlock() instanceof BlockMario && ((BlockMario) state.getBlock()).isGround())
                {
                    for(int i3 = pos.getY() - 3 + height; i3 <= pos.getY() + height; ++i3)
                    {
                        int i4 = i3 - (pos.getY() + height);
                        int j1 = 1 - i4 / 2;

                        for(int k1 = pos.getX() - j1; k1 <= pos.getX() + j1; ++k1)
                        {
                            int l1 = k1 - pos.getX();

                            for(int i2 = pos.getZ() - j1; i2 <= pos.getZ() + j1; ++i2)
                            {
                                int j2 = i2 - pos.getZ();

                                if(Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(k1, i3, i2);
                                    state = world.getBlockState(blockpos);

                                    if(state.getBlock().isAir(state, world, blockpos) || state.getBlock().isLeaves(state, world, blockpos) ||
                                            state.getMaterial() == Material.VINE)
                                    {
                                        this.setBlockAndNotifyAdequately(world, blockpos, ModBlocks.blockMarioLeaves.getDefaultState());
                                    }
                                }
                            }
                        }
                    }

                    for(int j3 = 0; j3 < height; ++j3)
                    {
                        BlockPos upN = pos.up(j3);
                        state = world.getBlockState(upN);

                        if(state.getBlock().isAir(state, world, upN) || state.getBlock().isLeaves(state, world, upN) || state.getMaterial() ==
                                Material.VINE)
                        {
                            this.setBlockAndNotifyAdequately(world, pos.up(j3), ModBlocks.blockMarioLog.getDefaultState());
                        }
                    }

                    return true;
                } else
                {
                    return false;
                }
            }
        } else

        {
            return false;
        }
    }
}