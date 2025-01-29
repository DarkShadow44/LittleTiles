package com.creativemd.littletiles.mixins;

import net.minecraft.client.renderer.RenderBlocks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderBlocks.class)
public class MixinRenderBlocks {

    @Redirect(
            method = "renderStandardBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isAmbientOcclusionEnabled()Z"))
    private boolean renderStandardBlockIsAmbientOcclusionEnabled() {
        return false;
    }
}
