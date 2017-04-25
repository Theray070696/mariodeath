package io.github.Theray070696.mariodeath.plugins.jei;

import io.github.Theray070696.mariodeath.crafting.ShapelessRecipeMario;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 */
public class ShapelessRecipesWrapper extends AbstractShapelessRecipeWrapper
{
    private final ShapelessRecipeMario recipe;

    public ShapelessRecipesWrapper(IGuiHelper guiHelper, ShapelessRecipeMario recipe)
    {
        super(guiHelper);
        this.recipe = recipe;
        for(Object input : this.recipe.recipeItems)
        {
            if(input instanceof ItemStack)
            {
                ItemStack itemStack = (ItemStack) input;
                if(itemStack.stackSize != 1)
                {
                    itemStack.stackSize = 1;
                }
            }
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        ItemStack recipeOutput = recipe.getRecipeOutput();

        try
        {
            ingredients.setInputs(ItemStack.class, recipe.recipeItems);
            if(recipeOutput != null)
            {
                ingredients.setOutput(ItemStack.class, recipeOutput);
            }
        } catch(RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemStack> getInputs()
    {
        return recipe.recipeItems;
    }

    @Override
    public List<ItemStack> getOutputs()
    {
        return Collections.singletonList(recipe.getRecipeOutput());
    }
}