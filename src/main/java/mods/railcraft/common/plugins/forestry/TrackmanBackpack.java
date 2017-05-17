/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.plugins.forestry;

import mods.railcraft.api.core.items.IMinecartItem;
import mods.railcraft.api.core.items.IToolCrowbar;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.blocks.machine.alpha.EnumMachineAlpha;
import mods.railcraft.common.blocks.machine.beta.EnumMachineBeta;
import mods.railcraft.common.blocks.tracks.TrackTools;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMinecart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;

/**
 * @author CovertJaguar <http://www.railcraft.info>
 */
@Optional.Interface(iface = "forestry.api.storage.IBackpackDefinition", modid = ForestryPlugin.FORESTRY_ID)
public class TrackmanBackpack extends BaseBackpack {

    private static TrackmanBackpack instance;

    public static TrackmanBackpack getInstance() {
        if (instance == null)
            instance = new TrackmanBackpack();
        return instance;
    }

    protected TrackmanBackpack() {
        super("railcraft.trackman");
    }

    public void setup() {

        for (ResourceLocation id : Block.REGISTRY.getKeys()) {
            Block block = Block.REGISTRY.getObject(id);
            if (block == null) continue;
            if (TrackTools.isRailBlock(block))
                add(block);
        }

        for (ResourceLocation id : Item.REGISTRY.getKeys()) {
            Item item = Item.REGISTRY.getObject(id);
            if (item instanceof ItemMinecart || item instanceof IMinecartItem)
                add(item);
        }

        add(IToolCrowbar.ORE_TAG);

        add(EnumMachineAlpha.ANCHOR_WORLD.getStack());
        add(EnumMachineAlpha.ANCHOR_PERSONAL.getStack());
        add(EnumMachineAlpha.ANCHOR_ADMIN.getStack());
        add(EnumMachineAlpha.ANCHOR_PASSIVE.getStack());
        add(EnumMachineBeta.SENTINEL.getStack());

        add(RailcraftItems.BOTTLE_CREOSOTE);

        add(RailcraftItems.SPIKE_MAUL_IRON);
        add(RailcraftItems.SPIKE_MAUL_STEEL);
        add(RailcraftItems.CHARGE_METER);
        add(RailcraftItems.RAIL);
        add(RailcraftItems.RAILBED);
        add(RailcraftItems.TICKET);
        add(RailcraftItems.TICKET_GOLD);
        add(RailcraftItems.TIE);
        add(RailcraftItems.WHISTLE_TUNER);
        add(RailcraftItems.OVERALLS);
        add(RailcraftItems.TRACK_KIT);
        add(RailcraftItems.TRACK_PARTS);

        add(RailcraftBlocks.CHARGE_FEEDER);
        add(RailcraftBlocks.MANIPULATOR);
        add(RailcraftBlocks.TRACK_ELEVATOR);
        add(RailcraftBlocks.WIRE);
    }

    @Override
    public int getPrimaryColour() {
        return 0x0094FF;
    }

    @Override
    public int getSecondaryColour() {
        return 0xFFFFFF;
    }

}
