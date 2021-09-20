package net.mcbedev.kancolle.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.mcbedev.kancolle.client.renderer.RensouhouchanRenderer;
import net.mcbedev.kancolle.client.renderer.Shell127cmRenderer;
import net.mcbedev.kancolle.client.renderer.ShimakazeRenderer;
import net.mcbedev.kancolle.client.renderer.TorpedoRenderer;

@Environment(EnvType.CLIENT)
public class EntityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //註冊渲染器
        EntityRendererRegistryImpl.register(ModEntities.SHIMAKAZE, ShimakazeRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.RENSOUHOUCHAN, RensouhouchanRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.TORPEDO, TorpedoRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.SHELL_127_CM, Shell127cmRenderer::new);

    }
}