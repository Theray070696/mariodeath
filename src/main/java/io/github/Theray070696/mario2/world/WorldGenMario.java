package io.github.Theray070696.mario2.world;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.world.biome.BiomeMario;
import io.github.Theray070696.mario2.world.gen.WorldGenCastle;
import io.github.Theray070696.mario2.world.gen.WorldGenMinableSingle;
import io.github.Theray070696.mario2.world.gen.WorldGenQuestionMark;
import io.github.Theray070696.mario2.world.gen.WorldGenUndergroundHole;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Theray070696 on 9/15/2015.
 */
public class WorldGenMario implements IWorldGenerator
{
    private WorldGenerator questionMarkSMB;
    private WorldGenerator questionMarkUndergroundSMB;
    private WorldGenerator questionMarkUndergroundRareSMB;
    private WorldGenerator invisibleBlockSMB;

    private WorldGenerator questionMarkSMB3;
    private WorldGenerator questionMarkNotRareSMB3;
    private WorldGenerator invisibleBlockSMB3;

    private WorldGenerator questionMark;
    private WorldGenerator questionMarkNotRare;
    private WorldGenerator invisibleBlock;

    private WorldGenerator noteBlock;

    private WorldGenerator castle;
    private WorldGenerator undergroundHole;

    public WorldGenMario()
    {
        this.questionMarkSMB = new WorldGenQuestionMark(ModBlocks.marioBlockQuestionMarkSMB, true);
        this.questionMarkUndergroundSMB = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMarkUndergroundSMB, Blocks.AIR, false);
        this.questionMarkUndergroundRareSMB = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMarkUndergroundSMB, Blocks.AIR, true);
        this.invisibleBlockSMB = new WorldGenMinableSingle(ModBlocks.marioBlockInvisibleBlockSMB, Blocks.AIR, true);

        this.questionMarkSMB3 = new WorldGenQuestionMark(ModBlocks.marioBlockQuestionMarkSMB3, true);
        this.questionMarkNotRareSMB3 = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMarkSMB, Blocks.AIR, false);
        this.invisibleBlockSMB3 = new WorldGenMinableSingle(ModBlocks.marioBlockInvisibleBlockSMB3, Blocks.AIR, true);

        this.questionMark = new WorldGenQuestionMark(ModBlocks.marioBlockQuestionMark, true);
        this.questionMarkNotRare = new WorldGenMinableSingle(ModBlocks.marioBlockQuestionMark, Blocks.AIR, false);
        this.invisibleBlock = new WorldGenMinableSingle(ModBlocks.marioBlockInvisibleBlock, Blocks.AIR, true);

        this.noteBlock = new WorldGenMinableSingle(ModBlocks.marioBlockNoteBlock, Blocks.AIR, false);

        this.castle = new WorldGenCastle();
        this.undergroundHole = new WorldGenUndergroundHole();
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.getWorldType().getId() == WorldType.FLAT.getId())
        {
            return;
        }

        if(world.provider instanceof WorldProviderHell)
        {
            // Nether or Hellish dimensions
            this.runGenerator(this.questionMarkUndergroundRareSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(5), 3, 100);
        } else if(world.provider instanceof WorldProviderEnd)
        {
            // End or End-like dimensions
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);

            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(4), 15, 85);
        } else if(world.provider.getBiomeForCoords(new BlockPos(chunkX * 16, 0, chunkZ * 16)) instanceof BiomeMario)
        {
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(4), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(4), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 50, 85);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(4), 3, 45);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(4), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(4), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(6), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(20), 25, 100);

            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
            this.runGenerator(this.undergroundHole, world, random, chunkX, chunkZ, 0, 1, 45);
            this.runGenerator(this.undergroundHole, world, random, chunkX, chunkZ, 0, 1, 45);
        } else if(world.getWorldType().getName().equalsIgnoreCase("atg"))
        {
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(2), 55, 128);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 50);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 128);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 55, 128);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 3, 50);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 128);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 55, 128);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(2), 3, 50);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(5), 3, 128);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(15), 25, 128);

            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
            this.runGenerator(this.undergroundHole, world, random, chunkX, chunkZ, 0, 1, 50);
        } else if(!world.provider.getDimensionType().getName().equalsIgnoreCase("CompactMachinesWorld") && !world.provider.getDimensionType()
                .getName().contains("Tardis"))
        {
            // Overworld or some mod dimension
            this.runGenerator(this.questionMarkSMB, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkUndergroundSMB, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB, world, random, chunkX, chunkZ, random.nextInt(5), 3, 85);

            this.runGenerator(this.questionMarkSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkNotRareSMB3, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlockSMB3, world, random, chunkX, chunkZ, random.nextInt(5), 3, 85);

            this.runGenerator(this.questionMark, world, random, chunkX, chunkZ, random.nextInt(2), 50, 85);
            this.runGenerator(this.questionMarkNotRare, world, random, chunkX, chunkZ, random.nextInt(2), 3, 45);
            this.runGenerator(this.invisibleBlock, world, random, chunkX, chunkZ, random.nextInt(5), 3, 85);

            this.runGenerator(this.noteBlock, world, random, chunkX, chunkZ, random.nextInt(15), 25, 100);

            this.runGenerator(this.castle, world, random, chunkX, chunkZ, 0, 0, 0);
            this.runGenerator(this.undergroundHole, world, random, chunkX, chunkZ, 0, 1, 45);
        }
    }

    private void runGenerator(WorldGenerator worldGenerator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight,
            int maxHeight)
    {
        if(minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
        {
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
        }

        if(worldGenerator == this.noteBlock)
        {
            int heightDiff = maxHeight - minHeight + 1;

            Block topBlock = world.provider.getBiomeForCoords(new BlockPos(chunk_X, 0, chunk_Z)).topBlock.getBlock();

            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);

                BlockPos generatePos = new BlockPos(x, y, z);

                Block block = world.getBlockState(generatePos.down()).getBlock();

                if(block == topBlock || block == Blocks.DIRT || block == Blocks.STONE || block == Blocks.SAND || block == ModBlocks
                        .marioBlockGroundUnderground || block == ModBlocks.marioBlockGroundUndergroundSMW)
                {
                    worldGenerator.generate(world, rand, generatePos);
                }
            }
        } else if(worldGenerator instanceof WorldGenMinableSingle) // Underground and Invisible Question Mark Blocks
        {
            int heightDiff = maxHeight - minHeight + 1;
            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);

                BlockPos generatePos = new BlockPos(x, y, z);

                worldGenerator.generate(world, rand, generatePos);
                WorldGenQuestionMark.onQuestionMarkGenerated(world, generatePos, rand);
            }
        } else if(worldGenerator instanceof WorldGenQuestionMark) // Above ground Question Mark Blocks
        {
            for(int i = 0; i < chancesToSpawn; i++)
            {
                int x = chunk_X * 16 + rand.nextInt(16);
                int z = chunk_Z * 16 + rand.nextInt(16);
                worldGenerator.generate(world, rand, new BlockPos(x, minHeight, z));
            }
        } else if(worldGenerator == this.castle)
        {
            int x = chunk_X * 16 + rand.nextInt(16);
            int z = chunk_Z * 16 + rand.nextInt(16);

            if(!BiomeDictionary.hasType(world.getBiomeForCoordsBody(new BlockPos(x, 50, z)), BiomeDictionary.Type.OCEAN) && rand.nextInt(80) == 0)
            {
                BlockPos generatePos = new BlockPos(x, 50, z);

                generatePos = world.getTopSolidOrLiquidBlock(generatePos);

                Block topBlock = world.getBlockState(world.getTopSolidOrLiquidBlock(generatePos)).getBlock();

                if(!(topBlock instanceof BlockLiquid) && !(topBlock instanceof BlockFluidBase))
                {
                    worldGenerator.generate(world, rand, generatePos);
                }
            }
        } else if(worldGenerator == this.undergroundHole)
        {
            if(rand.nextInt(85) == 0)
            {
                int heightDiff = maxHeight - minHeight + 1;
                int x = chunk_X * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunk_Z * 16 + rand.nextInt(16);

                worldGenerator.generate(world, rand, new BlockPos(x, y, z));
            }
        }
    }
}