package com.mcjty.rftools.blocks.dimlets;

import com.mcjty.container.GenericGuiContainer;
import com.mcjty.entity.GenericEnergyHandlerTileEntity;
import com.mcjty.gui.Window;
import com.mcjty.gui.layout.PositionalLayout;
import com.mcjty.gui.widgets.*;
import com.mcjty.gui.widgets.Label;
import com.mcjty.gui.widgets.Panel;
import com.mcjty.rftools.RFTools;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;

public class GuiDimensionEditor extends GenericGuiContainer<DimensionEditorTileEntity> {
    public static final int EDITOR_WIDTH = 180;
    public static final int EDITOR_HEIGHT = 152;

    private EnergyBar energyBar;
    private ImageLabel arrow;
    private Label percentage;

    private static final ResourceLocation iconLocation = new ResourceLocation(RFTools.MODID, "textures/gui/dimensioneditor.png");
    private static final ResourceLocation iconGuiElements = new ResourceLocation(RFTools.MODID, "textures/gui/guielements.png");

    public GuiDimensionEditor(DimensionEditorTileEntity dimensionEditorTileEntity, DimensionEditorContainer container) {
        super(dimensionEditorTileEntity, container);
        GenericEnergyHandlerTileEntity.setCurrentRF(dimensionEditorTileEntity.getEnergyStored(ForgeDirection.DOWN));

        xSize = EDITOR_WIDTH;
        ySize = EDITOR_HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        int maxEnergyStored = tileEntity.getMaxEnergyStored(ForgeDirection.DOWN);
        energyBar = new EnergyBar(mc, this).setVertical().setMaxValue(maxEnergyStored).setLayoutHint(new PositionalLayout.PositionalHint(10, 7, 8, 54)).setShowText(false);
        energyBar.setValue(GenericEnergyHandlerTileEntity.getCurrentRF());

        arrow = new ImageLabel(mc, this).setImage(iconGuiElements, 192, 0);
        arrow.setLayoutHint(new PositionalLayout.PositionalHint(90, 26, 16, 16));

        percentage = new Label(mc, this).setText("0%");
        percentage.setLayoutHint(new PositionalLayout.PositionalHint(80, 43, 40, 16));

        Widget toplevel = new Panel(mc, this).setBackground(iconLocation).setLayout(new PositionalLayout()).addChild(energyBar).
                addChild(arrow).addChild(percentage);
        toplevel.setBounds(new Rectangle(guiLeft, guiTop, xSize, ySize));

        window = new Window(this, toplevel);
        tileEntity.requestRfFromServer();
        tileEntity.requestBuildingPercentage();
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        int pct = DimensionEditorTileEntity.getEditPercentage();
        if (pct > 0) {
            arrow.setImage(iconGuiElements, 144, 0);
        } else {
            arrow.setImage(iconGuiElements, 192, 0);
        }
        percentage.setText(pct + "%");

        window.draw();

        energyBar.setValue(GenericEnergyHandlerTileEntity.getCurrentRF());

        tileEntity.requestRfFromServer();
        tileEntity.requestBuildingPercentage();
    }
}
