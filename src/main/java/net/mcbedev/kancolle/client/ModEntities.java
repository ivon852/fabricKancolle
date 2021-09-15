package net.mcbedev.kancolle.client;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.entity.CubeEntity;
import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities{

    // 開始註冊程序

    // 註冊實體
    public static final EntityType<CubeEntity> CUBE = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "cube"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CubeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
  
    public static final EntityType<ShimakazeEntity> SHIMAKAZE = Registry.register(Registry.ENTITY_TYPE,
    new Identifier(Main.MOD_ID, "shimakaze"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ShimakazeEntity::new)
            .dimensions(EntityDimensions.fixed(0.5f, 2f)).build());

            
            

    public static void registerEntities() {
        FabricDefaultAttributeRegistry.register(SHIMAKAZE, ShimakazeEntity.createMobAttributes());
          FabricDefaultAttributeRegistry.register(CUBE, CubeEntity.createMobAttributes());
        System.out.println("Registering mod mobs for" + Main.MOD_ID);
    }




    public class SHIMAKAZE {
    }

}
