package io.github.Theray070696.mario2.proxy;

import io.github.Theray070696.mario2.MarioMod2;
import io.github.Theray070696.mario2.configuration.ConfigHandler;
import io.github.Theray070696.mario2.entity.EntityFireball;
import io.github.Theray070696.mario2.entity.EntityGoomba;
import io.github.Theray070696.mario2.entity.EntityKoopa;
import io.github.Theray070696.mario2.lib.ModInfo;
import io.github.Theray070696.mario2.world.ModBiomes;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 8/25/15.
 */
public abstract class CommonProxy implements IProxy
{
    @Override
    public void construct(FMLPreInitializationEvent event) throws Exception
    {

    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        // TODO: White squid, the Blooper. Idea from Bullseye55
        EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MOD_ID, "goomba"), EntityGoomba.class, ModInfo.MOD_ID + ":goomba", 0, MarioMod2.INSTANCE, 128, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MOD_ID, "koopa"), EntityKoopa.class, ModInfo.MOD_ID + ":koopa", 1, MarioMod2.INSTANCE, 128, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(ModInfo.MOD_ID, "fireball"), EntityFireball.class, ModInfo.MOD_ID + ":fireball", 2, MarioMod2.INSTANCE, 64, 1, true);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>();
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.COOL));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.DESERT));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.ICY));
        biomeEntries.addAll(BiomeManager.getBiomes(BiomeManager.BiomeType.WARM));
        List<Biome> biomes = new ArrayList<>();
        for(BiomeManager.BiomeEntry b : biomeEntries)
        {
            biomes.add(b.biome);
        }
        biomes.addAll(BiomeManager.oceanBiomes);

        if(ConfigHandler.enableBiomes)
        {
            biomes.remove(ModBiomes.biomeMarioPlains);
            biomes.remove(ModBiomes.biomeMarioForest);
            biomes.remove(ModBiomes.biomeMarioForestHills);
            biomes.remove(ModBiomes.biomeForestOfIllusion);
        }

        EntityRegistry.addSpawn(EntityGoomba.class, 100, 2, 5, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
        EntityRegistry.addSpawn(EntityKoopa.class, 100, 2, 5, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
    }
}