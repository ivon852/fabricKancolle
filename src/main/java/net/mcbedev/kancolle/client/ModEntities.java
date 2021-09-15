package net.mcbedev.kancolle.client;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.mcbedev.kancolle.Main;
import net.mcbedev.kancolle.client.entity.ShimakazeEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

  /*
  格式:自訂前綴+實體ID
  預先註冊的屬性:collision box、Scale、fireImmune
   */
  public static final EntityType<ShimakazeEntity> SHIMAKAZE = Registry.register(Registry.ENTITY_TYPE,
      new Identifier("kanmusu", "shimakaze"), FabricEntityTypeBuilder
          .create(SpawnGroup.CREATURE, ShimakazeEntity::new).fireImmune().dimensions(EntityDimensions.fixed(1.2567f, 1.21f).scaled(1)).build());


  public static void registerEntities() {
    /*
    註冊實體屬性，藉由各自實體的CreateAttribute()方法來註冊基本屬性。
     */
    FabricDefaultAttributeRegistry.register(SHIMAKAZE, ShimakazeEntity.createShimakazeAttributes());

    /*
    註冊實體音效
     */


    System.out.println("Registering mod mobs for" + Main.MOD_ID);
  }

}
