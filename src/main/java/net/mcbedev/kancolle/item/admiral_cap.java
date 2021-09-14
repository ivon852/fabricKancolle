package net.mcbedev.kancolle.item;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class admiral_cap extends Item {

    public admiral_cap(Settings settings) {
        super(settings);
    }

    // 偵測玩家手的動作
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        // playerEntity.setVelocity(playerEntity.getVelocity().x, 0.5,
        // playerEntity.getVelocity().z);

        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

    // 物品提示
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText("Use to interact with Kanmusu.").formatted(Formatting.BLUE));
    }

}
