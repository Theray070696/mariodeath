package io.github.Theray070696.mario2.plugins.jei;

import io.github.Theray070696.mario2.block.ModBlocks;
import io.github.Theray070696.mario2.client.gui.GuiMarioMaker;
import io.github.Theray070696.mario2.container.ContainerMarioMaker;
import io.github.Theray070696.mario2.crafting.*;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin
{
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new MarioMakerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.handleRecipes(ShapedCoinRecipe.class, recipe -> new ShapedCoinRecipeWrapper(jeiHelpers, recipe), "Mario Maker");
        registry.handleRecipes(ShapelessCoinRecipe.class, recipe -> new ShapelessCoinRecipeWrapper(jeiHelpers, recipe), "Mario Maker");
        registry.handleRecipes(ShapedOreRecipeMario.class, recipe -> new ShapedOreRecipeWrapper(jeiHelpers, recipe), "Mario Maker");
        registry.handleRecipes(ShapelessOreRecipeMario.class, recipe -> new ShapelessOreRecipeWrapper(jeiHelpers, recipe), "Mario Maker");

        registry.addRecipeClickArea(GuiMarioMaker.class, 88, 32, 28, 23, "Mario Maker");

        IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

        recipeTransferRegistry.addRecipeTransferHandler(ContainerMarioMaker.class, "Mario Maker", 1, 9, 10, 36);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.marioBlockMarioMaker), "Mario Maker");

        registry.addRecipes(MarioMakerCraftingManager.getInstance().getRecipeList(), "Mario Maker");

        registry.addAdvancedGuiHandlers(new MarioMakerAdvancedGuiHandler());

        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockInvisibleBlock));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockInvisibleBlockSMB));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockInvisibleBlockSMB3));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockQuestionMark));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockQuestionMarkSMB));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockQuestionMarkSMB3));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockQuestionMarkUndergroundSMB));
        jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ModBlocks.marioBlockQuestionMarkCastleSMB));
    }

    private static class MarioMakerAdvancedGuiHandler implements IAdvancedGuiHandler<GuiMarioMaker>
    {
        @Nonnull
        @Override
        public Class<GuiMarioMaker> getGuiContainerClass()
        {
            return GuiMarioMaker.class;
        }

        @Nullable
        @Override
        public List<Rectangle> getGuiExtraAreas(GuiMarioMaker guiMarioMaker)
        {
            return guiMarioMaker.getExtraGuiAreas();
        }
    }
}
