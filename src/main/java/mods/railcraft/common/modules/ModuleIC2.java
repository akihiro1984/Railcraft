/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.modules;

import ic2.api.recipe.Recipes;
import ic2.api.crops.Crops;
import mods.railcraft.api.core.RailcraftModule;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.blocks.detector.EnumDetector;
import mods.railcraft.common.blocks.machine.manipulator.ManipulatorVariant;
import mods.railcraft.common.carts.RailcraftCarts;
import mods.railcraft.common.items.ModItems;
import mods.railcraft.common.items.RailcraftItems;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft.common.plugins.ic2.IC2Plugin;
import mods.railcraft.common.plugins.misc.Mod;
//import mods.railcraft.common.plugins.ic2.crops.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@RailcraftModule(value = "railcraft:ic2", description = "industrial craft integration")
public class ModuleIC2 extends RailcraftModulePayload {

    @Override
    public void checkPrerequisites() throws MissingPrerequisiteException {
        if (!Mod.anyLoaded(Mod.IC2, Mod.IC2_CLASSIC))
            throw new MissingPrerequisiteException("IC2 not detected");
    }

    public ModuleIC2() {
        setEnabledEventHandler(new ModuleEventHandler() {
            @Override
            public void construction() {
                add(
                        RailcraftCarts.ENERGY_BATBOX,
                        RailcraftCarts.ENERGY_MFE,
                        RailcraftCarts.ENERGY_CESU,
                        RailcraftBlocks.MANIPULATOR
                );
                if (Mod.IC2_CLASSIC.isLoaded()) add(RailcraftCarts.ENERGY_MFSU);
            }

            @Override
            public void preInit(){
            //    IC2Plugin.addCanningRecipe(IC2Plugin.getItem("fluid_cell"), RailcraftItems.TARBERRY.getStack(), IC2Plugin.getItem("fluid_cell#railcraftcreosote")); ##Saved for later
            }

            @Override
            public void init() {
            //    Crops.instance.registerCrop(new CropCreosote()); ##Save this for when a proper crafting recipe is possible
            }

            @Override
            public void postInit() {
//                Block blockDetector = RailcraftBlocks.DETECTOR.block();
//
//                if (blockDetector != null) {
//                    ItemStack stack = EnumDetector.ENERGY.getItem();
//                    Object tin = RailcraftItems.PLATE.getRecipeObject(Metal.TIN);
//                    if (tin == null)
//                        tin = "ingotTin";
//                    CraftingPlugin.addRecipe(stack, false,
//                            "XXX",
//                            "XPX",
//                            "XXX",
//                            'X', tin,
//                            'P', Blocks.STONE_PRESSURE_PLATE);
//                }

                ItemStack batbox = ModItems.BAT_BOX.get();
                if (batbox != null) {
                    RailcraftCarts cart = RailcraftCarts.ENERGY_BATBOX;
                    ItemStack stack = cart.getStack();
                    if (stack != null) {
                        CraftingPlugin.addRecipe(stack,
                                "E",
                                "M",
                                'E', batbox,
                                'M', Items.MINECART
                        );
                    }
                }

                if (!Mod.IC2_CLASSIC.isLoaded()) {
                    ItemStack cesu = ModItems.CESU.get();
                    if (cesu != null) {
                        RailcraftCarts cart = RailcraftCarts.ENERGY_CESU;
                        ItemStack stack = cart.getStack();
                        if (stack != null) {
                            CraftingPlugin.addRecipe(stack,
                                    "E",
                                    "M",
                                    'E', cesu,
                                    'M', Items.MINECART
                            );
                        }
                    }
                } else {
                    ItemStack mfsu = ModItems.MFSU.get();
                    if (mfsu != null) {
                        RailcraftCarts cart = RailcraftCarts.ENERGY_MFSU;
                        ItemStack stack = cart.getStack();
                        if (stack != null) {
                            CraftingPlugin.addRecipe(stack,
                                    "E",
                                    "M",
                                    'E', mfsu,
                                    'M', Items.MINECART
                            );
                        }
                    }
                }

                ItemStack mfe = ModItems.MFE.get();
                if (mfe != null) {
                    RailcraftCarts cart = RailcraftCarts.ENERGY_MFE;
                    ItemStack stack = cart.getStack();
                    if (stack != null) {

                        CraftingPlugin.addRecipe(stack,
                                "E",
                                "M",
                                'E', mfe,
                                'M', Items.MINECART
                        );
                    }
                }

                ItemStack battery = ModItems.BATTERY.get();
                ItemStack machine = ModItems.IC2_MACHINE.get();

                ItemStack detector;
                if (RailcraftBlocks.DETECTOR.isLoaded())
                    detector = EnumDetector.ADVANCED.getStack();
                else
                    detector = new ItemStack(Blocks.STONE_PRESSURE_PLATE);

                if (battery != null && machine != null) {
                    if (ManipulatorVariant.ENERGY_LOADER.isAvailable())
                        Recipes.advRecipes.addRecipe(ManipulatorVariant.ENERGY_LOADER.getStack(),
                                "BLB",
                                "BIB",
                                "BDB",
                                'D', detector,
                                'B', battery,
                                'I', machine,
                                'L', new ItemStack(Blocks.HOPPER));

                    if (ManipulatorVariant.ENERGY_UNLOADER.isAvailable())
                        Recipes.advRecipes.addRecipe(ManipulatorVariant.ENERGY_UNLOADER.getStack(),
                                "BDB",
                                "BIB",
                                "BLB",
                                'D', detector,
                                'B', battery,
                                'I', machine,
                                'L', new ItemStack(Blocks.HOPPER));
                }
            }
        });
    }
}
