package net.mcbedev.kancolle.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.ModEntities;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    // 註冊物品new Admiral Cap，再在各個檔案實作功能
    public static final Item ADMIRAL_CAP = registerItem("kancollemod","admiral_cap",
            new admiral_cap(new FabricItemSettings().group(ModItemGroup.KANMUSU)));

    // 註冊物品的方法，自訂前綴+物品ID
    private static Item registerItem(String prefix, String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(prefix, name), item);
    }

    //註冊生怪蛋，材質由json決定
    public static final Item SHIMAKAZE_SPAWN_EGG = registerItem("kancollemod", "shimakaze_spawn_egg", new SpawnEggItem(ModEntities.SHIMAKAZE, 12895428, 11382189, new Item.Settings().group(ModItemGroup.KANMUSU)));

    public static void registerModItems() {
        System.out.println("Registering mod items for" + Main.MOD_ID);
    }
}
