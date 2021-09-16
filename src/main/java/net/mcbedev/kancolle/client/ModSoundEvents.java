package net.mcbedev.kancolle.client;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
    /*
    註冊音效事件，不需要在Main.java初始化
     */
    public static final SoundEvent ENTITY_SHIMAKAZE_SAY = register("entity.shimakaze.say");
    public static final SoundEvent ENTITY_SHIMAKAZE_SAY2 = register("entity.shimakaze.say2");
    public static final SoundEvent ENTITY_SHIMAKAZE_SAY3 = register("entity.shimakaze.say3");
    public static final SoundEvent ENTITY_SHIMAKAZE_SAY4 = register("entity.shimakaze.say4");
    public static final SoundEvent ENTITY_SHIMAKAZE_HURT = register("entity.shimakaze.hurt");
    public static final SoundEvent ENTITY_SHIMAKAZE_ATTACK = register("entity.shimakaze.attack");
    public static final SoundEvent ENTITY_SHIMAKAZE_DEATH = register("entity.shimakaze.death");

    /*
    註冊音效事件的方法
     */
    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier("kancollemod",id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }
}
