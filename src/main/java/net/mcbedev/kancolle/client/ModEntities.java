package net.mcbedev.kancolle.client;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.entity.RensouhouchanEntity;
import net.mcbedev.kancolle.client.entity.Shell127cmEntity;
import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.mcbedev.kancolle.client.entity.TorpedoEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class ModEntities {

  /*
  格式:自訂前綴+實體ID
  預先註冊的屬性:
  minecraft:collision_box
  minecraft:scale
  minecraft:fire_Immune
   */
  public static final EntityType<ShimakazeEntity> SHIMAKAZE = Registry.register(Registry.ENTITY_TYPE,
          new Identifier("kanmusu", "shimakaze"), FabricEntityTypeBuilder
                  .create(SpawnGroup.CREATURE, ShimakazeEntity::new).fireImmune().dimensions(EntityDimensions.fixed(1.2567f,
                          1.21f)).build());
  public static final EntityType<RensouhouchanEntity> RENSOUHOUCHAN = Registry.register(Registry.ENTITY_TYPE,
          new Identifier("kanmusu", "rensouhouchan"), FabricEntityTypeBuilder
                  .create(SpawnGroup.CREATURE, RensouhouchanEntity::new).fireImmune().dimensions(EntityDimensions.fixed(0.6f,
                          1.0f).scaled(-0.5F)).build());

  //註冊投射物
  public static List<EntityType<? extends Entity>> ENTITY_TYPES = new LinkedList();
  public static final EntityType<TorpedoEntity> TORPEDO = projectile(TorpedoEntity::new, "torpedo");
  public static final EntityType<Shell127cmEntity> SHELL_127_CM = projectile(Shell127cmEntity::new, "shell127cm");

  private static <T extends Entity> EntityType<T> projectile(EntityType.EntityFactory<T> factory, String id
                                                             ) {
    EntityType<T> type = FabricEntityTypeBuilder.<T>create(SpawnGroup.MISC, factory)
            .dimensions(new EntityDimensions(0.5F, 0.5F, true)).spawnableFarFromPlayer()
            .trackRangeBlocks(90).trackedUpdateRate(40).build();
    Registry.register(Registry.ENTITY_TYPE, new Identifier("kancollemod", id), type);
    ENTITY_TYPES.add(type);
    return type;
  }


  public static void registerEntities() {
    /*
    註冊實體屬性，藉由各自實體的CreateAttribute()方法來註冊基本屬性。
     */
    FabricDefaultAttributeRegistry.register(SHIMAKAZE, ShimakazeEntity.createShimakazeAttributes());
    FabricDefaultAttributeRegistry.register(RENSOUHOUCHAN, RensouhouchanEntity.createRensouhouchanAttributes());

    System.out.println("Registering mod mobs for" + Main.MOD_ID);
  }

}
