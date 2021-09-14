package net.mcbedev.kancolle;

import net.fabricmc.api.ModInitializer;
import net.mcbedev.kancolle.item.ModItems;

public class Main implements ModInitializer {

    public static final String MOD_ID = "kancolledmod";

    @Override
    public void onInitialize() {
        ModItems.registerModItems();

    }
}