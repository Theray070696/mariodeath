package io.github.Theray070696.mario2.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Theray070696 on 4/13/2017.
 * Code from Forge.
 */
public class ShapedOreRecipeMario implements IMarioRecipe
{
    public static final int MAX_CRAFT_GRID_WIDTH = 3;
    public static final int MAX_CRAFT_GRID_HEIGHT = 3;

    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    protected ItemStack output = ItemStack.EMPTY;
    /**
     * Is a array of Objects that composes the recipe.
     */
    protected Object[] input = null;
    /**
     * How many horizontal slots this recipe is wide.
     */
    public int recipeWidth = 0;
    /**
     * How many vertical slots this recipe uses.
     */
    public int recipeHeight = 0;
    protected boolean mirrored = true;

    public ShapedOreRecipeMario(Block result, Object... recipe)
    {
        this(new ItemStack(result), recipe);
    }

    public ShapedOreRecipeMario(Item result, Object... recipe)
    {
        this(new ItemStack(result), recipe);
    }

    public ShapedOreRecipeMario(ItemStack result, Object... recipe)
    {
        output = result.copy();

        String shape = "";
        int idx = 0;

        if(recipe[idx] instanceof Boolean)
        {
            mirrored = (Boolean) recipe[idx];
            if(recipe[idx + 1] instanceof Object[])
            {
                recipe = (Object[]) recipe[idx + 1];
            } else
            {
                idx = 1;
            }
        }

        if(recipe[idx] instanceof String[])
        {
            String[] parts = ((String[]) recipe[idx++]);

            for(String s : parts)
            {
                recipeWidth = s.length();
                shape += s;
            }

            recipeHeight = parts.length;
        } else
        {
            while(recipe[idx] instanceof String)
            {
                String s = (String) recipe[idx++];
                shape += s;
                recipeWidth = s.length();
                recipeHeight++;
            }
        }

        if(recipeWidth * recipeHeight != shape.length())
        {
            String ret = "Invalid shaped ore recipe: ";
            for(Object tmp : recipe)
            {
                ret += tmp + ", ";
            }
            ret += output;
            throw new RuntimeException(ret);
        }

        HashMap<Character, Object> itemMap = new HashMap<Character, Object>();

        for(; idx < recipe.length; idx += 2)
        {
            Character chr = (Character) recipe[idx];
            Object in = recipe[idx + 1];

            if(in instanceof ItemStack)
            {
                itemMap.put(chr, ((ItemStack) in).copy());
            } else if(in instanceof Item)
            {
                itemMap.put(chr, new ItemStack((Item) in));
            } else if(in instanceof Block)
            {
                itemMap.put(chr, new ItemStack((Block) in, 1, OreDictionary.WILDCARD_VALUE));
            } else if(in instanceof String)
            {
                itemMap.put(chr, OreDictionary.getOres((String) in));
            } else
            {
                String ret = "Invalid shaped ore recipe: ";
                for(Object tmp : recipe)
                {
                    ret += tmp + ", ";
                }
                ret += output;
                throw new RuntimeException(ret);
            }
        }

        input = new Object[recipeWidth * recipeHeight];
        int x = 0;
        for(char chr : shape.toCharArray())
        {
            input[x++] = itemMap.get(chr);
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting var1)
    {
        return output.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return input.length;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return output;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        for(int x = 0; x <= MAX_CRAFT_GRID_WIDTH - recipeWidth; x++)
        {
            for(int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - recipeHeight; ++y)
            {
                if(checkMatch(inv, x, y, false))
                {
                    return true;
                }

                if(mirrored && checkMatch(inv, x, y, true))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror)
    {
        for(int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++)
        {
            for(int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++)
            {
                int subX = x - startX;
                int subY = y - startY;
                Object target = null;

                if(subX >= 0 && subY >= 0 && subX < recipeWidth && subY < recipeHeight)
                {
                    if(mirror)
                    {
                        target = input[recipeWidth - subX - 1 + subY * recipeWidth];
                    } else
                    {
                        target = input[subX + subY * recipeWidth];
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);

                if(target instanceof ItemStack)
                {
                    if(!OreDictionary.itemMatches((ItemStack) target, slot, false))
                    {
                        return false;
                    }
                } else if(target instanceof List)
                {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((List<ItemStack>) target).iterator();
                    while(itr.hasNext() && !matched)
                    {
                        matched = OreDictionary.itemMatches(itr.next(), slot, false);
                    }

                    if(!matched)
                    {
                        return false;
                    }
                } else if(target == null && slot != ItemStack.EMPTY)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public ShapedOreRecipeMario setMirrored(boolean mirror)
    {
        mirrored = mirror;
        return this;
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     *
     * @return The recipes input vales.
     */
    public Object[] getInput()
    {
        return this.input;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) //getRecipeLeftovers
    {
        return ForgeHooks.defaultRecipeGetRemainingItems(inv).toArray(new ItemStack[0]);
    }
}
