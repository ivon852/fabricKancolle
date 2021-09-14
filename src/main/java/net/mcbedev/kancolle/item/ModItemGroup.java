package net.mcbedev.kancolle.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.mcbedev.kancolle.Main;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    //新增創造面板，用提督帽當圖示
    public static final ItemGroup KANMUSU = FabricItemGroupBuilder.build(new Identifier(Main.MOD_ID, "kanmusu"),
            () -> new ItemStack(ModItems.ADMIRAL_CAP));

}
