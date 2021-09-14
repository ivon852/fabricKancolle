package net.mcbedev.kancolle.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.mcbedev.kancolle.Main;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    // 註冊物品"Admiral Cap"
    public static final Item ADMIRAL_CAP = registerItem("admiral_cap",
            new admiral_cap(new FabricItemSettings().group(ModItemGroup.KANMUSU)));

    // 註冊物品的方法
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering mod items for" + Main.MOD_ID);
    }
}
