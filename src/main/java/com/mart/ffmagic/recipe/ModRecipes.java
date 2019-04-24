package com.mart.ffmagic.recipe;

import com.mart.ffmagic.item.ModItems;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ModRecipes {

    private static Map<Item, Item> pressItems = new HashMap<>();

    private static Map<Item, Item> scrollRecipes = new HashMap<>();

    public static void addRecipes(){
        pressItems.put(ModItems.firefly_jar_forest, ModItems.firefly_juice_forest);
        pressItems.put(ModItems.firefly_jar_demon, ModItems.firefly_juice_demon);
        pressItems.put(ModItems.firefly_jar_earth, ModItems.firefly_juice_earth);
        pressItems.put(ModItems.firefly_jar_fairy, ModItems.firefly_juice_fairy);
        pressItems.put(ModItems.firefly_jar_ice, ModItems.firefly_juice_ice);
        pressItems.put(ModItems.firefly_jar_mountain, ModItems.firefly_juice_mountain);
        pressItems.put(ModItems.firefly_jar_void, ModItems.firefly_juice_void);

        scrollRecipes.put(ModItems.firefly_juice_fairy, ModItems.scroll_sage);
        scrollRecipes.put(ModItems.firefly_juice_forest, ModItems.scroll_druid);
    }

    public static Item getPressOutput(Item input){
        return pressItems.getOrDefault(input, null);
    }

    public static Item getScrollRecipeOutput(Item input){
        return scrollRecipes.getOrDefault(input, null);
    }

}
