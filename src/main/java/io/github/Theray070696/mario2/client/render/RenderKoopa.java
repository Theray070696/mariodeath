package io.github.Theray070696.mario2.client.render;

import io.github.Theray070696.mario2.client.model.ModelKoopa;
import io.github.Theray070696.mario2.lib.ModInfo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Theray070696 on 8/16/2017
 */
public class RenderKoopa extends RenderLiving
{
    public RenderKoopa(RenderManager manager)
    {
        super(manager, new ModelKoopa(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation(ModInfo.MOD_ID, "textures/entities/koopa.png");
    }
}
