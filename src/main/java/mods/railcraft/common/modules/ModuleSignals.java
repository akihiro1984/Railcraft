/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.modules;

import mods.railcraft.api.core.RailcraftModule;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.items.RailcraftItems;

@RailcraftModule(value = "railcraft:signals", description = "signals, signal boxes")
public class ModuleSignals extends RailcraftModulePayload {

    public ModuleSignals() {
        setEnabledEventHandler(new ModuleEventHandler() {
            @Override
            public void construction() {
                add(
//                        RailcraftBlocks.signal,
                        RailcraftBlocks.SIGNAL_BOX,
                        RailcraftItems.SIGNAL_BLOCK_SURVEYOR,
                        RailcraftItems.SIGNAL_TUNER,
                        RailcraftItems.SIGNAL_LAMP,
                        RailcraftItems.SIGNAL_LABEL,
                        RailcraftItems.CIRCUIT
                );
            }
        });
    }
}
