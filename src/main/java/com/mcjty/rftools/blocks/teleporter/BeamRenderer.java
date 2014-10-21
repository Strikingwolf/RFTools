package com.mcjty.rftools.blocks.teleporter;

import com.mcjty.rftools.Constants;
import com.mcjty.rftools.render.DefaultISBRH;
import com.mcjty.rftools.render.ModRenderers;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class BeamRenderer extends DefaultISBRH {

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorOpaque(255, 255, 255);
        tessellator.setBrightness(190);
        tessellator.addTranslation(x, y, z);
//
//        addSide(block, tessellator, Constants.SIDE_DOWN);
//        addSide(block, tessellator, Constants.SIDE_UP);
//        addSide(block, tessellator, Constants.SIDE_NORTH);
//        addSide(block, tessellator, Constants.SIDE_SOUTH);
//        addSide(block, tessellator, Constants.SIDE_WEST);
//        addSide(block, tessellator, Constants.SIDE_EAST);
        int meta = 0;
        if (world != null) {
            meta = world.getBlockMetadata(x, y, z);
        }

        addSideHeight(block, tessellator, Constants.SIDE_NORTH, meta, 3);
        addSideHeight(block, tessellator, Constants.SIDE_SOUTH, meta, 3);
        addSideHeight(block, tessellator, Constants.SIDE_WEST, meta, 3);
        addSideHeight(block, tessellator, Constants.SIDE_EAST, meta, 3);

        tessellator.addTranslation(-x, -y, -z);
        return true;
    }

    @Override
    public int getRenderId() {
        return ModRenderers.RENDERID_BEAM;
    }
}
