package com.creativemd.littletiles.mixins;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.creativemd.littletiles.mixininterfaces.IMixinRenderBlocks;

@Mixin(value = RenderBlocks.class)
public abstract class MixinRenderBlocks implements IMixinRenderBlocks {

    @Shadow
    public int brightnessTopLeft;
    @Shadow
    public int brightnessBottomLeft;
    @Shadow
    public int brightnessBottomRight;
    @Shadow
    public int brightnessTopRight;

    @Shadow
    public double renderMinX;
    @Shadow
    public double renderMaxX;
    @Shadow
    public double renderMinY;
    @Shadow
    public double renderMaxY;
    @Shadow
    public double renderMinZ;
    @Shadow
    public double renderMaxZ;

    @Shadow
    public float colorRedTopLeft;
    @Shadow
    public float colorRedBottomLeft;
    @Shadow
    public float colorRedBottomRight;
    @Shadow
    public float colorRedTopRight;
    @Shadow
    public float colorGreenTopLeft;
    @Shadow
    public float colorGreenBottomLeft;
    @Shadow
    public float colorGreenBottomRight;
    @Shadow
    public float colorGreenTopRight;
    @Shadow
    public float colorBlueTopLeft;
    @Shadow
    public float colorBlueBottomLeft;
    @Shadow
    public float colorBlueBottomRight;
    @Shadow
    public float colorBlueTopRight;

    @Shadow
    public boolean partialRenderBounds;
    @Shadow
    public IBlockAccess blockAccess;

    @Shadow
    public float aoLightValueScratchYZPP;
    @Shadow
    public float aoLightValueScratchXYZPPP;
    @Shadow
    public float aoLightValueScratchXYZNPP;
    @Shadow
    public int aoBrightnessXYNP;
    @Shadow
    public float aoLightValueScratchXYZNPN;
    @Shadow
    public float aoLightValueScratchYZPN;
    @Shadow
    public float aoLightValueScratchXYPP;
    @Shadow
    public float aoLightValueScratchXYNP;
    @Shadow
    public int aoBrightnessXYZPPP;
    @Shadow
    public float aoLightValueScratchXYZPPN;
    @Shadow
    public int aoBrightnessYZPP;
    @Shadow
    public int aoBrightnessXYPP;
    @Shadow
    public int aoBrightnessXYZPPN;
    @Shadow
    public int aoBrightnessXYZNPN;
    @Shadow
    public int aoBrightnessYZPN;
    @Shadow
    public int aoBrightnessXYZNPP;
    @Shadow
    public float aoLightValueScratchXYNN;
    @Shadow
    public float aoLightValueScratchXYPN;
    @Shadow
    public float aoLightValueScratchXYZPNN;
    @Shadow
    public float aoLightValueScratchYZNN;
    @Shadow
    public float aoLightValueScratchYZNP;
    @Shadow
    public int aoBrightnessXYPN;
    @Shadow
    public int aoBrightnessXYNN;
    @Shadow
    public float aoLightValueScratchXYZNNN;
    @Shadow
    public int aoBrightnessYZNP;
    @Shadow
    public float aoLightValueScratchXYZNNP;
    @Shadow
    public float aoLightValueScratchXYZPNP;
    @Shadow
    public int aoBrightnessXYZNNN;
    @Shadow
    public int aoBrightnessYZNN;
    @Shadow
    public int aoBrightnessXYZPNN;
    @Shadow
    public int aoBrightnessXYZNNP;
    @Shadow
    public int aoBrightnessXYZPNP;

    @Shadow
    public abstract int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_,
            double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_);

    @Shadow
    public abstract boolean hasOverrideBlockTexture();

    @Shadow
    public abstract int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_);

    private boolean isLittleTiles;

    @Override
    public void setLittleTiles(boolean enable) {
        isLittleTiles = enable;
    }

    @Inject(method = "renderFaceYPos", at = @At("HEAD"))
    public void renderFaceYPos(Block block, double xIn, double yIn, double zIn, IIcon texture, CallbackInfo callback) {
        if (!(isLittleTiles && Minecraft.isAmbientOcclusionEnabled()
                && block.getLightValue() == 0
                && this.partialRenderBounds)) {
            return;
        }

        int x = (int) xIn;
        int y = (int) yIn;
        int z = (int) zIn;

        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        float colorR = (float) (l >> 16 & 255) / 255.0F;
        float colorG = (float) (l >> 8 & 255) / 255.0F;
        float colorB = (float) (l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (colorR * 30.0F + colorG * 59.0F + colorB * 11.0F) / 100.0F;
            float f4 = (colorR * 30.0F + colorG * 70.0F) / 100.0F;
            float f5 = (colorR * 30.0F + colorB * 70.0F) / 100.0F;
            colorR = f3;
            colorG = f4;
            colorB = f5;
        }

        int i1 = block.colorMultiplier(this.blockAccess, x, y, z);;

        if (this.renderMaxY >= 1.0D || !this.blockAccess.getBlock(x, y + 1, z).isOpaqueCube()) {
            i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
        }

        float f7 = this.blockAccess.getBlock(x, y + 1, z).getAmbientOcclusionLightValue();

        float f9 = (this.aoLightValueScratchYZPP + f7 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP)
                / 4.0F;
        float f10 = (f7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN)
                / 4.0F;
        float f11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + f7 + this.aoLightValueScratchYZPN)
                / 4.0F;
        float f8 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + f7)
                / 4.0F;
        int j1 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, i1);
        int i2 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, i1);
        int l1 = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
        int k1 = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, i1);

        float f3 = (float) (f9 * renderMaxX * renderMaxZ + f10 * renderMaxX * (1.0D - renderMaxZ)
                + f11 * (1.0D - renderMaxX) * (1.0D - renderMaxZ)
                + f8 * (1.0D - renderMaxX) * renderMaxZ);
        float f4 = (float) (f9 * renderMaxX * renderMinZ + f10 * renderMaxX * (1.0D - renderMinZ)
                + f11 * (1.0D - renderMaxX) * (1.0D - renderMinZ)
                + f8 * (1.0D - renderMaxX) * renderMinZ);
        float f5 = (float) (f9 * renderMinX * renderMinZ + f10 * renderMinX * (1.0D - renderMinZ)
                + f11 * (1.0D - renderMinX) * (1.0D - renderMinZ)
                + f8 * (1.0D - renderMinX) * renderMinZ);
        float f6 = (float) (f9 * renderMinX * renderMaxZ + f10 * renderMinX * (1.0D - renderMaxZ)
                + f11 * (1.0D - renderMinX) * (1.0D - renderMaxZ)
                + f8 * (1.0D - renderMinX) * renderMaxZ);

        this.brightnessTopLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                renderMaxX * renderMaxZ,
                renderMaxX * (1.0D - renderMaxZ),
                (1.0D - renderMaxX) * (1.0D - renderMaxZ),
                (1.0D - renderMaxX) * renderMaxZ);
        this.brightnessBottomLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                renderMaxX * renderMinZ,
                renderMaxX * (1.0D - renderMinZ),
                (1.0D - renderMaxX) * (1.0D - renderMinZ),
                (1.0D - renderMaxX) * renderMinZ);
        this.brightnessBottomRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                renderMinX * renderMinZ,
                renderMinX * (1.0D - renderMinZ),
                (1.0D - renderMinX) * (1.0D - renderMinZ),
                (1.0D - renderMinX) * renderMinZ);
        this.brightnessTopRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                renderMinX * renderMaxZ,
                renderMinX * (1.0D - renderMaxZ),
                (1.0D - renderMinX) * (1.0D - renderMaxZ),
                (1.0D - renderMinX) * renderMaxZ);

        this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = colorR;
        this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = colorG;
        this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = colorB;
        this.colorRedTopLeft *= f3;
        this.colorGreenTopLeft *= f3;
        this.colorBlueTopLeft *= f3;
        this.colorRedBottomLeft *= f4;
        this.colorGreenBottomLeft *= f4;
        this.colorBlueBottomLeft *= f4;
        this.colorRedBottomRight *= f5;
        this.colorGreenBottomRight *= f5;
        this.colorBlueBottomRight *= f5;
        this.colorRedTopRight *= f6;
        this.colorGreenTopRight *= f6;
        this.colorBlueTopRight *= f6;
    }

    @Inject(method = "renderFaceYNeg", at = @At("HEAD"))
    public void renderFaceYNeg(Block block, double xIn, double yIn, double zIn, IIcon texture, CallbackInfo callback) {
        if (!(isLittleTiles && Minecraft.isAmbientOcclusionEnabled()
                && block.getLightValue() == 0
                && this.partialRenderBounds)) {
            return;
        }

        int x = (int) xIn;
        int y = (int) yIn;
        int z = (int) zIn;

        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        float colorR = (float) (l >> 16 & 255) / 255.0F;
        float colorG = (float) (l >> 8 & 255) / 255.0F;
        float colorB = (float) (l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (colorR * 30.0F + colorG * 59.0F + colorB * 11.0F) / 100.0F;
            float f4 = (colorR * 30.0F + colorG * 70.0F) / 100.0F;
            float f5 = (colorR * 30.0F + colorB * 70.0F) / 100.0F;
            colorR = f3;
            colorG = f4;
            colorB = f5;
        }

        int i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);

        if (this.renderMinY <= 0.0D || !this.blockAccess.getBlock(x, y - 1, z).isOpaqueCube()) {
            i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
        }

        float f7 = this.blockAccess.getBlock(x, y - 1, z).getAmbientOcclusionLightValue();
        float f9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + f7)
                / 4.0F;
        float f10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + f7 + this.aoLightValueScratchYZNN)
                / 4.0F;
        float f11 = (f7 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN)
                / 4.0F;
        float f8 = (this.aoLightValueScratchYZNP + f7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN)
                / 4.0F;

        int j1 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, i1);
        int i2 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, i1);
        int l1 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, i1);
        int k1 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, i1);

        float f3 = (float) (f9 * (1.0D - renderMinX) * renderMaxZ + f10 * (1.0D - renderMinX) * (1.0D - renderMaxZ)
                + f11 * renderMinX * (1.0D - renderMaxZ)
                + f8 * renderMinX * renderMaxZ);
        float f4 = (float) (f9 * (1.0D - renderMinX) * renderMinZ + f10 * (1.0D - renderMinX) * (1.0D - renderMinZ)
                + f11 * renderMinX * (1.0D - renderMinZ)
                + f8 * renderMinX * renderMinZ);
        float f5 = (float) (f9 * (1.0D - renderMaxX) * renderMinZ + f10 * (1.0D - renderMaxX) * (1.0D - renderMinZ)
                + f11 * renderMaxX * (1.0D - renderMinZ)
                + f8 * renderMaxX * renderMinZ);
        float f6 = (float) (f9 * (1.0D - renderMaxX) * renderMaxZ + f10 * (1.0D - renderMaxX) * (1.0D - renderMaxZ)
                + f11 * renderMaxX * (1.0D - renderMaxZ)
                + f8 * renderMaxX * renderMaxZ);

        this.brightnessTopLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMinY) * this.renderMaxZ,
                (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ),
                this.renderMinY * (1.0D - this.renderMaxZ),
                this.renderMinY * this.renderMaxZ);
        this.brightnessBottomLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMinY) * this.renderMinZ,
                (1.0D - this.renderMinY) * (1.0D - this.renderMinZ),
                this.renderMinY * (1.0D - this.renderMinZ),
                this.renderMinY * this.renderMinZ);
        this.brightnessBottomRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMaxY) * this.renderMinZ,
                (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ),
                this.renderMaxY * (1.0D - this.renderMinZ),
                this.renderMaxY * this.renderMinZ);
        this.brightnessTopRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMaxY) * this.renderMaxZ,
                (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ),
                this.renderMaxY * (1.0D - this.renderMaxZ),
                this.renderMaxY * this.renderMaxZ);

        if (!this.hasOverrideBlockTexture()) {
            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = colorR
                    * 0.5F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = colorG
                    * 0.5F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = colorB
                    * 0.5F;
        } else {
            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
        }

        this.colorRedTopLeft *= f3;
        this.colorGreenTopLeft *= f3;
        this.colorBlueTopLeft *= f3;
        this.colorRedBottomLeft *= f4;
        this.colorGreenBottomLeft *= f4;
        this.colorBlueBottomLeft *= f4;
        this.colorRedBottomRight *= f5;
        this.colorGreenBottomRight *= f5;
        this.colorBlueBottomRight *= f5;
        this.colorRedTopRight *= f6;
        this.colorGreenTopRight *= f6;
        this.colorBlueTopRight *= f6;

    }

}
