/*------------------------------------------------------------------------------
 Copyright (c) CovertJaguar, 2011-2017
 http://railcraft.info

 This code is the property of CovertJaguar
 and may only be used with explicit written
 permission unless otherwise specified on the
 license page at http://railcraft.info/wiki/info:license.
 -----------------------------------------------------------------------------*/
package mods.railcraft.common.plugins.thaumcraft;

import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.blocks.aesthetics.brick.BrickTheme;
import mods.railcraft.common.blocks.aesthetics.brick.BrickVariant;
import mods.railcraft.common.blocks.aesthetics.generic.EnumGeneric;
import mods.railcraft.common.blocks.detector.EnumDetector;
import mods.railcraft.common.blocks.machine.alpha.EnumMachineAlpha;
import mods.railcraft.common.blocks.machine.beta.EnumMachineBeta;
import mods.railcraft.common.blocks.machine.equipment.EquipmentVariant;
import mods.railcraft.common.blocks.ore.EnumOre;
import mods.railcraft.common.blocks.ore.EnumOreMagic;
import mods.railcraft.common.carts.IRailcraftCartContainer;
import mods.railcraft.common.carts.RailcraftCarts;
import mods.railcraft.common.items.ItemDust;
import mods.railcraft.common.items.ItemGear.EnumGear;
import mods.railcraft.common.items.ItemRail.EnumRail;
import mods.railcraft.common.items.ItemTie.EnumTie;
import mods.railcraft.common.items.Metal;
import mods.railcraft.common.items.RailcraftItems;
import mods.railcraft.common.items.firestone.ItemFirestoneCracked;
import mods.railcraft.common.items.firestone.ItemFirestoneRefined;
import mods.railcraft.common.plugins.color.EnumColor;
import mods.railcraft.common.plugins.forge.LocalizationPlugin;
import mods.railcraft.common.plugins.misc.Mod;
import mods.railcraft.common.util.inventory.InvTools;
import mods.railcraft.common.util.misc.Game;
import mods.railcraft.common.util.misc.ItemStackCache;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class ThaumcraftPlugin {
    public static final ItemStackCache ITEMS = new ItemStackCache("Thaumcraft", ItemsTC.class, Mod.THAUMCRAFT::isLoaded, tag -> {
        try {
            return new ItemStack((Item) ItemsTC.class.getField(tag).get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            if (Game.DEVELOPMENT_ENVIRONMENT)
                throw new RuntimeException(e);
            return null;
        }
    });
    public static final ItemStackCache BLOCKS = new ItemStackCache("Thaumcraft", BlocksTC.class, Mod.THAUMCRAFT::isLoaded, tag -> {
        try {
            return new ItemStack((Block) BlocksTC.class.getField(tag).get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            if (Game.DEVELOPMENT_ENVIRONMENT)
                throw new RuntimeException(e);
            return null;
        }
    });
    public static final String RESEARCH_CATEGORY = "RAILCRAFT";
    private static Map<String, ResearchPage> researchPages = new HashMap<String, ResearchPage>();

    public static void setupResearch() {
        ResearchCategories.registerCategory(RESEARCH_CATEGORY, null, new ResourceLocation("railcraft", "textures/items/tool.crowbar.magic.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));

        // Apothecaries Backpack
        Item item = RailcraftItems.BACKPACK_APOTHECARY_T1.item();
        if (item != null) {
            IArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe("RC_ApothecariesBackpack", RailcraftItems.BACKPACK_APOTHECARY_T1.getStack(),
                    new AspectList().add(Aspect.AIR, 16).add(Aspect.ORDER, 16),
                    "X#X",
                    "VYV",
                    "X#X",
                    '#', Blocks.WOOL,
                    'V', Items.GLASS_BOTTLE,
                    'X', Items.STRING,
                    'Y', "chestWood");

            AspectList aspects = new AspectList();
            aspects.add(Aspect.VOID, 3).add(Aspect.CRAFT, 3).add(Aspect.MOTION, 2);

            ResearchItem backpack = new ResearchItemRC("RC_ApothecariesBackpack", ThaumcraftPlugin.RESEARCH_CATEGORY, aspects, 2, 0, 6, RailcraftItems.BACKPACK_APOTHECARY_T1.getStack());
            backpack.setPages(ThaumcraftPlugin.getResearchPage("RC_ApothecariesBackpack"), new ResearchPage(recipe)).setParentsHidden("ENCHFABRIC").registerResearchItem();
        }

        // Thaumium Crowbar
        item = RailcraftItems.CROWBAR_THAUMIUM.item();
        if (item != null) {
            String researchTag = "RC_Crowbar_Thaumium";
            IArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(researchTag, new ItemStack(item),
                    new AspectList().add(Aspect.ORDER, 8),
                    " RI",
                    "RIR",
                    "IR ",
                    'I', ThaumcraftPlugin.ITEMS.get("ingots", 0),
                    'R', "dyeRed");

            AspectList aspects = new AspectList();
            aspects.add(Aspect.TOOL, 1).add(Aspect.MECHANISM, 2).add(Aspect.METAL, 1);

            ResearchItem thaumiumCrowbar = new ResearchItemRC(researchTag, ThaumcraftPlugin.RESEARCH_CATEGORY, aspects, 0, 0, 3, new ItemStack(item));
            thaumiumCrowbar.setPages(ThaumcraftPlugin.getResearchPage(researchTag), new ResearchPage(recipe))
                    .setParentsHidden("THAUMIUM")
                    .registerResearchItem();
        }

        // Void Crowbar
        item = RailcraftItems.CROWBAR_VOID.item();
        if (item != null) {
            String researchTag = "RC_Crowbar_Void";
            IArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(researchTag, new ItemStack(item),
                    new AspectList().add(Aspect.ENTROPY, 50),
                    " RI",
                    "RIR",
                    "IR ",
                    'I', ThaumcraftPlugin.ITEMS.get("ingots", 1),
                    'R', "dyeRed");

            AspectList aspects = new AspectList();
            aspects.add(Aspect.TOOL, 2).add(Aspect.MECHANISM, 4).add(Aspect.METAL, 2);

            ResearchItemRC voidCrowbar = new ResearchItemRC(researchTag, ThaumcraftPlugin.RESEARCH_CATEGORY, aspects, 0, 1, 3, new ItemStack(item));
            voidCrowbar.setPages(ThaumcraftPlugin.getResearchPage(researchTag), new ResearchPage(recipe))
                    .setParents(researchTag).setParentsHidden("VOIDMETAL")
                    .registerResearchItem();
        }
    }

    private static ResearchPage createResearchPage(String key, int pageNum) {
        return new ResearchPage(LocalizationPlugin.translate(String.format("thaumcraft.research.%s.page.%d", key, pageNum)).replace("\n", "<BR>").replace("---", "<LINE>").replace("{img}", "<IMG>").replace("{/img}", "</IMG>"));
    }

    public static ResearchPage getResearchPage(String researchTag) {
        ResearchPage page = researchPages.get(researchTag);
        if (page == null) {
            page = createResearchPage(researchTag, 1);
            researchPages.put(researchTag, page);
        }
        return page;
    }

    public static void registerAspects() {
        try {
            AspectList anchorAspects = new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.ORDER, 4).add(Aspect.AURA, 2).add(Aspect.DESIRE, 2);
            AspectList steamAspects = new AspectList().add(Aspect.WATER, 3).add(Aspect.MECHANISM, 2).add(Aspect.FIRE, 3);
            AspectList tankAspects = new AspectList().add(Aspect.VOID, 4).add(Aspect.WATER, 4);

            addBrickAspects(BrickTheme.ABYSSAL, Aspect.DARKNESS);
            addBrickAspects(BrickTheme.BLEACHEDBONE, Aspect.DEATH);
            addBrickAspects(BrickTheme.BLOODSTAINED, Aspect.LIFE);
            addBrickAspects(BrickTheme.FROSTBOUND, Aspect.COLD);
            addBrickAspects(BrickTheme.INFERNAL, 2, Aspect.FIRE, Aspect.SOUL);
            addBrickAspects(BrickTheme.NETHER, Aspect.FIRE);
            addBrickAspects(BrickTheme.QUARRIED, Aspect.LIGHT);
            addBrickAspects(BrickTheme.SANDY, Aspect.EARTH);

            addItemAspect(EnumGeneric.STONE_ABYSSAL.getStack(), new AspectList().add(Aspect.EARTH, 4).add(Aspect.DARKNESS, 2));
            addItemAspect(EnumGeneric.STONE_QUARRIED.getStack(), new AspectList().add(Aspect.EARTH, 4).add(Aspect.LIGHT, 2));
            addItemAspect(EnumGeneric.CRUSHED_OBSIDIAN.getStack(), copyAspects(Blocks.OBSIDIAN).remove(Aspect.EARTH, 1).add(Aspect.ENTROPY, 1).add(Aspect.ELDRITCH, 1));
            addItemAspect(EnumGeneric.BLOCK_CONCRETE.getStack(), new AspectList().add(Aspect.EARTH, 3).add(Aspect.METAL, 1));
            addItemAspect(EnumGeneric.BLOCK_CREOSOTE.getStack(), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ORDER, 1));

            addItemAspect(EnumOre.DARK_DIAMOND.getStack(), copyAspects(Blocks.DIAMOND_ORE).add(Aspect.DARKNESS, 1));
            addItemAspect(EnumOre.DARK_EMERALD.getStack(), copyAspects(Blocks.EMERALD_ORE).add(Aspect.DARKNESS, 1));
            addItemAspect(EnumOre.DARK_LAPIS.getStack(), copyAspects(Blocks.LAPIS_ORE).add(Aspect.DARKNESS, 1));

            addItemAspect(EnumOre.SULFUR.getStack(), new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 3));
            addItemAspect(EnumOre.SALTPETER.getStack(), new AspectList().add(Aspect.EARTH, 1).add(Aspect.AIR, 3));
            addItemAspect(EnumOreMagic.FIRESTONE.getStack(), new AspectList().add(Aspect.EARTH, 1).add(Aspect.FIRE, 6).add(Aspect.ENTROPY, 1));

            addItemAspect(RailcraftItems.DUST.getStack(ItemDust.EnumDust.SULFUR), new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 3));
            addItemAspect(RailcraftItems.DUST.getStack(ItemDust.EnumDust.SALTPETER), new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.AIR, 3));
            addItemAspect(RailcraftItems.DUST.getStack(ItemDust.EnumDust.CHARCOAL), new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.FIRE, 2));
            addItemAspect(RailcraftItems.DUST.getStack(ItemDust.EnumDust.OBSIDIAN), copyAspects(Blocks.OBSIDIAN).remove(Aspect.EARTH, 2).add(Aspect.ENTROPY, 1));

            addItemAspect(RailcraftItems.REBAR.getStack(), new AspectList().add(Aspect.METAL, 1));
            addItemAspect(RailcraftItems.RAIL.getStack(1, EnumRail.STANDARD), new AspectList().add(Aspect.METAL, 1));
            addItemAspect(RailcraftItems.RAIL.getStack(1, EnumRail.REINFORCED), new AspectList().add(Aspect.METAL, 1).add(Aspect.ORDER, 1));
            addItemAspect(RailcraftItems.RAIL.getStack(1, EnumRail.WOOD), new AspectList().add(Aspect.PLANT, 1));
            addItemAspect(RailcraftItems.RAIL.getStack(1, EnumRail.ADVANCED), new AspectList().add(Aspect.METAL, 1).add(Aspect.MECHANISM, 1));
            addItemAspect(RailcraftItems.RAIL.getStack(1, EnumRail.SPEED), new AspectList().add(Aspect.METAL, 1).add(Aspect.MOTION, 1));
            addItemAspect(RailcraftItems.TIE.getStack(1, EnumTie.WOOD), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ORDER, 1));
            addItemAspect(RailcraftItems.TIE.getStack(1, EnumTie.STONE), new AspectList().add(Aspect.EARTH, 3).add(Aspect.METAL, 1));

            addItemAspect(RailcraftItems.COKE.getStack(), new AspectList().add(Aspect.FIRE, 4).add(Aspect.ENERGY, 4));

            addItemAspect(RailcraftItems.PLATE.getStack(1, Metal.IRON), new AspectList().add(Aspect.METAL, 4));
            addItemAspect(RailcraftItems.PLATE.getStack(1, Metal.STEEL), new AspectList().add(Aspect.METAL, 3).add(Aspect.ORDER, 1));
            addItemAspect(RailcraftItems.PLATE.getStack(1, Metal.TIN), new AspectList().add(Aspect.METAL, 3));

            addItemAspect(RailcraftItems.GEAR.getStack(EnumGear.BRASS), new AspectList().add(Aspect.METAL, 4).add(Aspect.ORDER, 1).add(Aspect.MECHANISM, 2).add(Aspect.DESIRE, 2));
            addItemAspect(RailcraftItems.GEAR.getStack(EnumGear.BUSHING), new AspectList().add(Aspect.METAL, 1).add(Aspect.ORDER, 1).add(Aspect.MECHANISM, 1));
            addItemAspect(RailcraftItems.GEAR.getStack(EnumGear.IRON), new AspectList().add(Aspect.METAL, 4).add(Aspect.ORDER, 1).add(Aspect.MECHANISM, 4));
            addItemAspect(RailcraftItems.GEAR.getStack(EnumGear.STEEL), new AspectList().add(Aspect.METAL, 8).add(Aspect.ORDER, 2).add(Aspect.MECHANISM, 4));

            addItemAspect(EquipmentVariant.ROLLING_MACHINE_POWERED.getStack(), new AspectList().add(Aspect.CRAFT, 6).add(Aspect.MECHANISM, 2));
            addItemAspect(EnumMachineAlpha.ROCK_CRUSHER.getStack(), new AspectList().add(Aspect.CRAFT, 2).add(Aspect.MECHANISM, 4).add(Aspect.EARTH, 4).add(Aspect.EARTH, 4));
            addItemAspect(EquipmentVariant.FEED_STATION.getStack(), new AspectList().add(Aspect.PLANT, 4).add(Aspect.BEAST, 4).add(Aspect.MECHANISM, 2));
            addItemAspect(EquipmentVariant.SMOKER.getStack(), new AspectList().add(Aspect.FIRE, 4).add(Aspect.AIR, 4).add(Aspect.MECHANISM, 2));
            addItemAspect(EquipmentVariant.SMOKER.getStack(), new AspectList().add(Aspect.FIRE, 4).add(Aspect.AIR, 4).add(Aspect.MECHANISM, 2));

            addItemAspect(EnumMachineAlpha.STEAM_TRAP_MANUAL.getStack(), steamAspects.copy().add(Aspect.TRAP, 4));
            addItemAspect(EnumMachineAlpha.STEAM_TRAP_AUTO.getStack(), steamAspects.copy().add(Aspect.TRAP, 4).add(Aspect.MIND, 2));
            addItemAspect(EnumMachineAlpha.STEAM_OVEN.getStack(), steamAspects.copy().add(Aspect.FIRE, 3));
            addItemAspect(EnumMachineAlpha.TURBINE.getStack(), steamAspects.copy().add(Aspect.ENERGY, 4));

            addItemAspect(EnumMachineAlpha.ANCHOR_ADMIN.getStack(), anchorAspects);
            addItemAspect(EnumMachineAlpha.ANCHOR_PERSONAL.getStack(), anchorAspects);
            addItemAspect(EnumMachineAlpha.ANCHOR_WORLD.getStack(), anchorAspects);
            addItemAspect(EnumMachineBeta.SENTINEL.getStack(), anchorAspects);

            addItemAspect(EnumMachineBeta.BOILER_FIREBOX_SOLID.getStack(), steamAspects.copy().add(Aspect.ENERGY, 2));
            addItemAspect(EnumMachineBeta.BOILER_FIREBOX_FLUID.getStack(), steamAspects.copy().add(Aspect.ENERGY, 2));
            addItemAspect(EnumMachineBeta.BOILER_TANK_LOW_PRESSURE.getStack(), steamAspects.copy().add(Aspect.ENERGY, 2));
            addItemAspect(EnumMachineBeta.BOILER_TANK_HIGH_PRESSURE.getStack(), steamAspects.copy().add(Aspect.ENERGY, 2));
            addItemAspect(EnumMachineBeta.ENGINE_STEAM_HOBBY.getStack(), steamAspects.copy().add(Aspect.ENERGY, 4));
            addItemAspect(EnumMachineBeta.ENGINE_STEAM_LOW.getStack(), steamAspects.copy().add(Aspect.ENERGY, 4));
            addItemAspect(EnumMachineBeta.ENGINE_STEAM_HIGH.getStack(), steamAspects.copy().add(Aspect.ENERGY, 4));

            addItemAspect(EnumMachineAlpha.TANK_WATER.getStack(), tankAspects.copy().add(Aspect.PLANT, 2).add(Aspect.WATER, 2));

            AspectList ironTankAspects = tankAspects.copy().add(Aspect.METAL, 2);
            addItemAspect(EnumMachineBeta.TANK_IRON_GAUGE.getStack(), ironTankAspects);
            addItemAspect(EnumMachineBeta.TANK_IRON_VALVE.getStack(), ironTankAspects);
            addItemAspect(EnumMachineBeta.TANK_IRON_WALL.getStack(), ironTankAspects);
            addItemAspect(EnumMachineBeta.TANK_STEEL_GAUGE.getStack(), ironTankAspects);
            addItemAspect(EnumMachineBeta.TANK_STEEL_VALVE.getStack(), ironTankAspects);
            addItemAspect(EnumMachineBeta.TANK_STEEL_WALL.getStack(), ironTankAspects);

            AspectList detectorAspects = new AspectList().add(Aspect.SENSES, 4).add(Aspect.MECHANISM, 4);
            for (EnumDetector detector : EnumDetector.VALUES) {
                addItemAspect(detector.getStack(), detectorAspects);
            }

            AspectList glassAspects = new AspectList().add(Aspect.CRYSTAL, 4).add(Aspect.METAL, 1);
            for (EnumColor color : EnumColor.VALUES) {
                addItemAspect(RailcraftBlocks.GLASS.getStack(color), glassAspects);
            }

            addItemAspect(RailcraftBlocks.ANVIL_STEEL.getStack(), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.EXCHANGE, 2).add(Aspect.METAL, 4).add(Aspect.ORDER, 2));

            addCartAspect(RailcraftCarts.LOCO_STEAM_SOLID, steamAspects.copy().add(Aspect.MOTION, 2).add(Aspect.MECHANISM, 4));
            addCartAspect(RailcraftCarts.LOCO_ELECTRIC, new AspectList().add(Aspect.FLUX, 6).add(Aspect.MECHANISM, 6).add(Aspect.MOTION, 2));
            addCartAspect(RailcraftCarts.PUMPKIN, new AspectList(new ItemStack(Blocks.PUMPKIN)).add(Aspect.MOTION, 2).add(Aspect.ENTROPY, 6));
            addCartAspect(RailcraftCarts.ANCHOR_WORLD, anchorAspects.copy().add(Aspect.MOTION, 2));
            addCartAspect(RailcraftCarts.ANCHOR_ADMIN, anchorAspects.copy().add(Aspect.MOTION, 2));
            addCartAspect(RailcraftCarts.ANCHOR_PERSONAL, anchorAspects.copy().add(Aspect.MOTION, 2));

            addItemAspect(RailcraftItems.GOGGLES.getStack(), new AspectList().add(Aspect.AURA, 4).add(Aspect.SENSES, 4));

            addItemAspect(RailcraftItems.FIRESTONE_RAW.getStack(), new AspectList().add(Aspect.FIRE, 6).add(Aspect.CRYSTAL, 2).add(Aspect.ENTROPY, 4));
            addItemAspect(RailcraftItems.FIRESTONE_CUT.getStack(), new AspectList().add(Aspect.FIRE, 6).add(Aspect.CRYSTAL, 2).add(Aspect.ENTROPY, 2).add(Aspect.ORDER, 2));
            addItemAspect(ItemFirestoneRefined.getItemCharged(), new AspectList().add(Aspect.FIRE, 6).add(Aspect.CRYSTAL, 2).add(Aspect.ORDER, 4));
            addItemAspect(ItemFirestoneCracked.getItemCharged(), new AspectList().add(Aspect.FIRE, 6).add(Aspect.CRYSTAL, 2).add(Aspect.ENTROPY, 4));
        } catch (Throwable error) {
            Game.logErrorAPI("Thaumcraft", error, ThaumcraftApi.class);
        }
    }

    public static AspectList copyAspects(Block block) {
        return new AspectList(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
    }

    private static void addItemAspect(@Nullable ItemStack stack, AspectList aspects) {
        if (InvTools.isEmpty(stack))
            return;
        ThaumcraftApi.registerObjectTag(stack, aspects);
    }

    private static void addItemAspect(@Nullable Block block, AspectList aspects) {
        if (block == null)
            return;
        ThaumcraftApi.registerObjectTag(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), aspects);
    }

    private static void addItemAspectComplex(ItemStack stack, AspectList aspects) {
        ThaumcraftApi.registerComplexObjectTag(stack, aspects);
    }

    private static void addCartAspect(IRailcraftCartContainer cart, AspectList aspects) {
        addItemAspect(cart.getStack(), aspects);
        ThaumcraftApi.registerEntityTag(cart.getEntityLocalizationTag(), aspects);
    }

    private static void addBrickAspects(BrickTheme brick, Aspect baseAspect) {
        addBrickAspects(brick, 4, baseAspect);
    }

    private static void addBrickAspects(BrickTheme brick, int baseAmount, Aspect... baseAspects) {
        Block block = brick.getBlock();
        if (block == null)
            return;
        AspectList aspects = new AspectList().add(Aspect.EARTH, 4);
        for (Aspect a : baseAspects) {
            aspects.add(a, baseAmount);
        }
        ThaumcraftApi.registerObjectTag(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), aspects);
        ThaumcraftApi.registerObjectTag(brick.getStack(BrickVariant.COBBLE), aspects.copy().remove(Aspect.EARTH, 2).add(Aspect.ENTROPY, 2));
        ThaumcraftApi.registerObjectTag(brick.getStack(BrickVariant.BLOCK), aspects.copy().remove(Aspect.EARTH, 2).add(Aspect.ORDER, 2));
        ThaumcraftApi.registerObjectTag(brick.getStack(BrickVariant.ETCHED), aspects.copy().remove(Aspect.EARTH, 2).add(Aspect.DESIRE, 2));
    }

    public static ToolMaterial getThaumiumToolMaterial() {
        try {
            return ThaumcraftMaterials.TOOLMAT_THAUMIUM;
        } catch (Throwable error) {
            Game.logErrorAPI("Thaumcraft", error, ThaumcraftApi.class);
        }
        return ToolMaterial.IRON;
    }

    public static ToolMaterial getVoidmetalToolMaterial() {
        try {
            return ThaumcraftMaterials.TOOLMAT_VOID;
        } catch (Throwable error) {
            Game.logErrorAPI("Thaumcraft", error, ThaumcraftApi.class);
        }
        return ToolMaterial.IRON;
    }
}
