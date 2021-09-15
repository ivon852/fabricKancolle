package net.mcbedev.kancolle;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.mcbedev.kancolle.client.ModEntities;
import net.mcbedev.kancolle.client.entity.CubeEntity;
import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.mcbedev.kancolle.item.ModItems;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.GeckoLib;

public class Main implements ModInitializer {

    public static final String MOD_ID = "kancollemod";

    // 註冊實體
    public static final EntityType<CubeEntity> CUBE = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "cube"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CubeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());

    public static final EntityType<ShimakazeEntity> SHIMAKAZE = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "shimakaze"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ShimakazeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 2f)).build());

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
        GeckoLib.initialize();

        // 如果可以，我想把實體註冊程序另外寫
        ModEntities.registerEntities();

        // 賦予實體基本屬性
        FabricDefaultAttributeRegistry.register(CUBE, CubeEntity.createMobAttributes());

        FabricDefaultAttributeRegistry.register(SHIMAKAZE, ShimakazeEntity.createMobAttributes());

    }
}