package com.creativemd.littletiles.mixins;

import com.creativemd.littletiles.mixininterfaces.IMixinRenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    public double renderMinZ;
    @Shadow
    public double renderMaxZ;

    @Shadow
    public boolean enableAO;

    /** Red color value for the top left corner */
    @Shadow    public float colorRedTopLeft;
    /** Red color value for the bottom left corner */
    @Shadow    public float colorRedBottomLeft;
    /** Red color value for the bottom right corner */
    @Shadow   public float colorRedBottomRight;
    /** Red color value for the top right corner */
    @Shadow   public float colorRedTopRight;
    /** Green color value for the top left corner */
    @Shadow   public float colorGreenTopLeft;
    /** Green color value for the bottom left corner */
    @Shadow public float colorGreenBottomLeft;
    /** Green color value for the bottom right corner */
    @Shadow public float colorGreenBottomRight;
    /** Green color value for the top right corner */
    @Shadow public float colorGreenTopRight;
    /** Blue color value for the top left corner */
    @Shadow public float colorBlueTopLeft;
    /** Blue color value for the bottom left corner */
    @Shadow public float colorBlueBottomLeft;
    /** Blue color value for the bottom right corner */
    @Shadow  public float colorBlueBottomRight;
    /** Blue color value for the top right corner */
    @Shadow  public float colorBlueTopRight;


    @Shadow   public IBlockAccess blockAccess;
    /** If set to >=0, all block faces will be rendered using this texture index */
    @Shadow  public IIcon overrideBlockTexture;

    @Shadow    public double renderMinY;
    /** The maximum Y value for rendering (default 1.0). */
    @Shadow    public double renderMaxY;
    @Shadow
    public abstract void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceYPos(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceXNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceXPos(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceZPos(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);
    @Shadow
    public abstract void renderFaceZNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_);

    @Shadow
    public abstract IIcon getBlockIcon(Block p_147745_1_);
    @Shadow
    public abstract IIcon getBlockIcon(Block p_147793_1_, IBlockAccess p_147793_2_, int p_147793_3_, int p_147793_4_, int p_147793_5_, int p_147793_6_);

    @Shadow
    public abstract int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_, double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_);

    @Shadow
    public boolean renderAllFaces;

    @Shadow public abstract boolean hasOverrideBlockTexture();

    @Shadow
    public abstract int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_);

    @Shadow
    public boolean partialRenderBounds;

    private boolean isLittleTiles;

    @Override
    public void setLittleTiles(boolean enable)
    {
        isLittleTiles = enable;
    }

    @Inject(method = "renderFaceYPos", at=@At("HEAD"))
    public void renderFaceYPos(Block block, double xIn, double yIn, double zIn, IIcon texture, CallbackInfo callback)
    {
        if (!(isLittleTiles && Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0 && this.partialRenderBounds))
        {
            return;
        }

        int x = (int)xIn;
        int y = (int)yIn;
        int z =(int)zIn;

        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        float colorR = (float)(l >> 16 & 255) / 255.0F;
        float colorG = (float)(l >> 8 & 255) / 255.0F;
        float colorB = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (colorR * 30.0F + colorG * 59.0F + colorB * 11.0F) / 100.0F;
            float f4 = (colorR * 30.0F + colorG * 70.0F) / 100.0F;
            float f5 = (colorR * 30.0F + colorB * 70.0F) / 100.0F;
            colorR = f3;
            colorG = f4;
            colorB = f5;
        }

        int i1 = block.colorMultiplier(this.blockAccess, x, y, z);;

        if (this.renderMaxY >= 1.0D || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_).isOpaqueCube())
        {
            i1 = p_147808_1_.getMixedBrightnessForBlock(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
        }

        f7 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_).getAmbientOcclusionLightValue();
        f6 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + f7) / 4.0F;
        f3 = (this.aoLightValueScratchYZPP + f7 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
        f4 = (f7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
        f5 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + f7 + this.aoLightValueScratchYZPN) / 4.0F;
        this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, i1);
        this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, i1);
        this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, i1);
        this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
        this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_;
        this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_;
        this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_;
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

    @Inject(method = "renderFaceYNeg", at=@At("HEAD"))
    public void renderFaceYNeg(Block block, double xIn, double yIn, double zIn, IIcon texture, CallbackInfo callback)
    {
        if (!(isLittleTiles && Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0 && this.partialRenderBounds))
        {
            return;
        }

        int x = (int)xIn;
        int y = (int)yIn;
        int z = (int)zIn;

        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        float colorR = (float)(l >> 16 & 255) / 255.0F;
        float colorG = (float)(l >> 8 & 255) / 255.0F;
        float colorB = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable)
        {
            float f3 = (colorR * 30.0F + colorG * 59.0F + colorB * 11.0F) / 100.0F;
            float f4 = (colorR * 30.0F + colorG * 70.0F) / 100.0F;
            float f5 = (colorR * 30.0F + colorB * 70.0F) / 100.0F;
            colorR = f3;
            colorG = f4;
            colorB = f5;
        }

        int i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);

        if (this.renderMinY <= 0.0D || !this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_).isOpaqueCube())
        {
            i1 = p_147808_1_.getMixedBrightnessForBlock(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
        }

        f7 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_).getAmbientOcclusionLightValue();
        f3 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + f7) / 4.0F;
        f6 = (this.aoLightValueScratchYZNP + f7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
        f5 = (f7 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
        f4 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + f7 + this.aoLightValueScratchYZNN) / 4.0F;
        this.brightnessTopLeft = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, i1);
        this.brightnessTopRight = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, i1);
        this.brightnessBottomRight = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, i1);
        this.brightnessBottomLeft = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, i1);

        if (!this.hasOverrideBlockTexture())
        {
            this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.5F;
            this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.5F;
            this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.5F;
        }
        else
        {
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
