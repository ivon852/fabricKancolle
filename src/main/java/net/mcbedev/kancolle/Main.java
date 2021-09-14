package net.mcbedev.kancolle;

import net.fabricmc.api.ModInitializer;
import net.mcbedev.kancolle.item.ModItems;
import software.bernie.geckolib3.GeckoLib;

public class Main implements ModInitializer {

    public static final String MOD_ID = "kancollemod";

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        GeckoLib.initialize();
    }
}