package com.mcjty.rftools.blocks.dimlets;

import com.mcjty.container.GenericGuiContainer;
import com.mcjty.gui.Window;
import com.mcjty.gui.events.ValueEvent;
import com.mcjty.gui.layout.HorizontalLayout;
import com.mcjty.gui.layout.VerticalLayout;
import com.mcjty.gui.widgets.Panel;
import com.mcjty.gui.widgets.ScrollableLabel;
import com.mcjty.gui.widgets.Slider;
import com.mcjty.gui.widgets.Widget;
import com.mcjty.rftools.network.Argument;
import net.minecraft.inventory.Container;

import java.awt.*;

public class GuiDimensionMonitor extends GenericGuiContainer<DimensionMonitorTileEntity> {
    public static final int MONITOR_WIDTH = 160;
    public static final int MONITOR_HEIGHT = 30;

    private ScrollableLabel alarmLevel;

    public GuiDimensionMonitor(DimensionMonitorTileEntity dimensionMonitorTileEntity, Container container) {
        super(dimensionMonitorTileEntity, container);
    }

    @Override
    public void initGui() {
        super.initGui();
        int k = (this.width - MONITOR_WIDTH) / 2;
        int l = (this.height - MONITOR_HEIGHT) / 2;

        Panel toplevel = new Panel(mc, this).setFilledRectThickness(2).setLayout(new VerticalLayout());

        alarmLevel = new ScrollableLabel(mc, this).setSuffix("%").setDesiredWidth(30).setRealMinimum(0).setRealMaximum(100).
                setRealValue(tileEntity.getAlarmLevel()).
                addValueEvent(new ValueEvent() {
                    @Override
                    public void valueChanged(Widget parent, int newValue) {
                        changeAlarmValue(newValue);
                    }
                });
        Slider alarmSlider = new Slider(mc, this).
                setDesiredHeight(15).
                setHorizontal().
                setTooltips("Alarm level").
                setScrollable(alarmLevel);

        Panel bottomPanel = new Panel(mc, this).setLayout(new HorizontalLayout()).addChild(alarmLevel).addChild(alarmSlider);
        toplevel.addChild(bottomPanel);

        toplevel.setBounds(new Rectangle(k, l, MONITOR_WIDTH, MONITOR_HEIGHT));
        window = new Window(this, toplevel);
    }

    private void changeAlarmValue(int newValue) {
        tileEntity.setAlarmLevel(newValue);
        sendServerCommand(DimensionMonitorTileEntity.CMD_SETALARM, new Argument("level", newValue));
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        window.draw();
    }
}
