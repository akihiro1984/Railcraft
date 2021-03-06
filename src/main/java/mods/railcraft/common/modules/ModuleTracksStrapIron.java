/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2016
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.modules;

import mods.railcraft.api.core.RailcraftModule;
import mods.railcraft.common.blocks.RailcraftBlocks;

@RailcraftModule(value = "railcraft:tracks|strap_iron", description = "strap iron tracks")
public class ModuleTracksStrapIron extends RailcraftModulePayload {

    public ModuleTracksStrapIron() {
        setEnabledEventHandler(new ModuleEventHandler() {
            @Override
            public void construction() {
                add(
                        RailcraftBlocks.TRACK_FLEX_STRAP_IRON
//                        RailcraftBlocks.TRACK_JUNCTION_STRAP_IRON
                );
            }
        });
    }
}
