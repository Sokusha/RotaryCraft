/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.RotaryCraft;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import Reika.DragonAPI.Instantiable.ExpandedOreRecipe;
import Reika.DragonAPI.Libraries.ReikaDyeHelper;
import Reika.DragonAPI.Libraries.ReikaItemHelper;
import Reika.DragonAPI.Libraries.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.ReikaRecipeHelper;
import Reika.RotaryCraft.Auxiliary.ExtractorModOres;
import Reika.RotaryCraft.Auxiliary.ItemStacks;
import Reika.RotaryCraft.Auxiliary.WorktableRecipes;
import Reika.RotaryCraft.Registry.ConfigRegistry;
import Reika.RotaryCraft.Registry.EnumEngineType;
import Reika.RotaryCraft.Registry.ItemRegistry;
import Reika.RotaryCraft.Registry.MachineRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class RotaryRecipes {

	public static void addRecipes() {
		OreDictionary.initVanillaEntries();
		RotaryRegistration.loadOreDictionary();
		addMachines();
		addCraftItems();
		addItemBlocks();
		addToolItems();
		addMisc();
		addFurnace();
	}

	private static void addMachines() {
		MachineRegistry.COMPACTOR.addCrafting(new Object[]{"SPS", "PGP", "#P#", '#', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'P', ItemStacks.presshead, 'G', ItemStacks.gearunit16});

		MachineRegistry.FAN.addCrafting(new Object[]{"SSS", "SIS", "#G#", '#', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'G', ItemStacks.gearunit});

		MachineRegistry.AEROSOLIZER.addCrafting(new Object[]{"BRB", "RIR", "BRB", 'B', ItemStacks.basepanel, 'R', MachineRegistry.RESERVOIR.getCraftedProduct(), 'I', ItemStacks.impeller});

		MachineRegistry.HEATRAY.addCrafting(new Object[]{"OOO", "BLb", "#P#", '#', ItemStacks.basepanel, 'B', ItemStacks.bulb, 'b', ItemStacks.barrel, 'P', ItemStacks.power, 'L', ItemStacks.lens, 'O', Block.obsidian});

		MachineRegistry.FLOODLIGHT.addCrafting(new Object[]{"ISO", "Ggd", "I#O", '#', ItemStacks.basepanel, 'I', Item.ingotIron, 'd', Item.ingotGold, 'S', ItemStacks.steelingot, 'G', Block.glass, 'g', Block.glowStone, 'O', Block.obsidian});

		MachineRegistry.SHAFT.addMetaCrafting(RotaryNames.shaftItemNames.length-1, new Object[]{" S ", "SSS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem}); //Shaft cross

		addRecipeToBoth(MachineRegistry.BLASTFURNACE.getCraftedProduct(), new Object[]{"SSS", "SrS", "SSS", 'r', Item.redstone, 'S', ReikaItemHelper.stoneBricks});

		addRecipeToBoth(MachineRegistry.WORKTABLE.getCraftedProduct(), new Object[]{" C ", "SBS", "srs", 'r', Item.redstone, 'S', ItemStacks.steelingot, 'B', Block.brick, 'C', Block.workbench, 's', ReikaItemHelper.stoneSlab});

		MachineRegistry.BEVELGEARS.addCrafting(new Object[]{"ISB", "SGB", "BBB", 'B', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.gearunit});

		MachineRegistry.SPLITTER.addCrafting(new Object[]{"ISP", "SGP", "ISP", 'P', ItemStacks.basepanel, 'I', ItemStacks.steelingot, 'S', ItemStacks.shaftitem, 'G', ItemStacks.gearunit});

		MachineRegistry.CLUTCH.addCrafting(new Object[]{"S", "M", "R", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'R', Item.redstone});
		MachineRegistry.CLUTCH.addCrafting(new Object[]{"S", "R", 'S', MachineRegistry.SHAFT.getCraftedMetadataProduct(2), 'R', Item.redstone});

		MachineRegistry.DYNAMOMETER.addCrafting(new Object[]{" S ", " E ", " Ms", 's', ItemStacks.screen, 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'E', Item.enderPearl});

		MachineRegistry.BEDROCKBREAKER.addCrafting(new Object[]{"BDO", "BSO", "BDO", 'S', ItemStacks.steelingot, 'D', Item.diamond, 'O', Block.obsidian, 'B', ItemStacks.basepanel});

		MachineRegistry.FERMENTER.addCrafting(new Object[]{"BPB", "PIP", "BPB", 'B', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel});

		MachineRegistry.GRINDER.addCrafting(new Object[]{"B B", "SGS", "PPP", 'B', ItemStacks.steelingot, 'G', ItemStacks.steelgear, 'P', ItemStacks.basepanel, 'S', ItemStacks.saw});

		MachineRegistry.RESERVOIR.addCrafting(new Object[]{"B B", "B B", "BBB", 'B', ItemStacks.basepanel});

		MachineRegistry.FIREWORK.addCrafting(new Object[]{"BEB", "BDB", "BRB", 'B', ItemStacks.basepanel, 'R', Item.redstone, 'E', Item.eyeOfEnder, 'D', Block.dispenser});

		MachineRegistry.AUTOBREEDER.addCrafting(new Object[]{"B B", "BBB", 'B', ItemStacks.basepanel});

		MachineRegistry.FRACTIONATOR.addCrafting(new Object[]{"GFG", "GIG", "GPG", 'P', ItemStacks.basepanel, 'I', ItemStacks.mixer, 'G', Item.ingotGold, 'F', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 2)});

		MachineRegistry.BAITBOX.addCrafting(new Object[]{"BBB", "BAB", "BBB", 'B', Block.fenceIron, 'A', MachineRegistry.AUTOBREEDER.getCraftedProduct()});

		MachineRegistry.WINDER.addCrafting(new Object[]{" ss", " hg", "ppp", 'h', ItemStacks.shaftitem, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel});

		MachineRegistry.ECU.addCrafting(new Object[]{"IPI", "ICI", "IRI", 'I', ItemStacks.steelingot, 'P', ItemStacks.power, 'C', ItemStacks.pcb, 'R', Item.redstone});

		MachineRegistry.WOODCUTTER.addCrafting(new Object[]{"IS ", "PGS", "PPI", 'I', ItemStacks.steelingot, 'S', ItemStacks.saw, 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit});

		MachineRegistry.VACUUM.addCrafting(new Object[]{"SIS", "ICI", "SIS", 'C', Block.chest, 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller});

		MachineRegistry.BORER.addCrafting(new Object[]{"SSS", "DGC", "BBB", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', ItemStacks.drill, 'G', ItemStacks.gearunit, 'C', ItemStacks.pcb});

		MachineRegistry.SPRINKLER.addSizedCrafting(4, new Object[]{" s ", " p ", " i ", 's', ItemStacks.steelingot, 'p', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1), 'i', ItemStacks.impeller});

		MachineRegistry.SPAWNERCONTROLLER.addCrafting(new Object[]{"PCP", "OGO", "g g", 'O', Block.obsidian, 'P', ItemStacks.basepanel, 'G', Item.ingotGold, 'g', Block.glowStone, 'C', ItemStacks.pcb});

		MachineRegistry.PLAYERDETECTOR.addCrafting(new Object[]{"LRL", "OGO", "OPO", 'L', ReikaItemHelper.lapisDye, 'R', ItemStacks.radar, 'O', Block.obsidian, 'P', ItemStacks.basepanel, 'G', Item.ingotGold});

		MachineRegistry.OBSIDIAN.addCrafting(new Object[]{"SpS", "PMP", "BBB", 'M', ItemStacks.mixer, 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'p', Block.thinGlass, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});

		MachineRegistry.HEATER.addCrafting(new Object[]{"sBs", "prp", "scs", 's', ItemStacks.steelingot, 'B', Block.fenceIron, 'p', ItemStacks.basepanel, 'c', ItemStacks.combustor});

		MachineRegistry.GPR.addCrafting(new Object[]{"SsS", "PCP", "SRS", 'S', ItemStacks.steelingot, 's', ItemStacks.screen, 'P', ItemStacks.basepanel, 'R', ItemStacks.radar, 'C', ItemStacks.pcb});

		MachineRegistry.PULSEJET.addCrafting(new Object[]{"OCD", "PcO", "BBB", 'B', ItemStacks.basepanel, 'O', Block.obsidian, 'C', ItemStacks.compressor, 'D', ItemStacks.diffuser, 'c', ItemStacks.combustor, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});

		MachineRegistry.EXTRACTOR.addRecipe(new ExpandedOreRecipe(MachineRegistry.EXTRACTOR.getCraftedProduct(), new Object[]{"SWS", "siD", "PIN", 'D', ItemStacks.drill, 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'I', ItemStacks.shaftitem, 's', Block.stone, 'i', ItemStacks.impeller, 'N', Block.netherrack, 'W', ExpandedOreRecipe.getWoodList()}));

		MachineRegistry.LIGHTBRIDGE.addCrafting(new Object[]{"GgG", "BgS", "BBD", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'D', Item.diamond, 'G', Item.ingotGold, 'g', Block.glass});

		MachineRegistry.PILEDRIVER.addCrafting(new Object[]{"PGP", "gFg", "PDP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit8, 'g', ItemStacks.shaftitem, 'F', ItemStacks.flywheelcore3, 'D', ItemStacks.drill});

		MachineRegistry.PUMP.addCrafting(new Object[]{"SGS", "pIp", "PpP", 'P', ItemStacks.basepanel, 'p', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1), 'I', ItemStacks.impeller, 'G', Block.thinGlass, 'S', ItemStacks.steelingot});

		MachineRegistry.MOBRADAR.addCrafting(new Object[]{" rs", " g ", "pcp", 'r', ItemStacks.radar, 's', ItemStacks.screen, 'c', ItemStacks.pcb, 'g', ItemStacks.gearunit, 'p', ItemStacks.basepanel});

		MachineRegistry.TNTCANNON.addCrafting(new Object[]{"sgc", "pcp", "pCr", 'g', Block.blockRedstone, 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Block.chest, 'p', ItemStacks.basepanel});

		MachineRegistry.SONICWEAPON.addCrafting(new Object[]{"psp", "sts", "psp", 't', ItemStacks.turbine, 's', ItemStacks.sonar, 'p', ItemStacks.basepanel});

		MachineRegistry.FORCEFIELD.addCrafting(new Object[]{"lnl", "ddd", "sgs", 'd', Item.diamond, 's', ItemStacks.basepanel, 'n', Item.netherStar, 'g', Item.ingotGold, 'l', ReikaItemHelper.lapisDye});

		MachineRegistry.MUSICBOX.addCrafting(new Object[]{"sns", "ncn", "sns", 'n', Block.music, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb});

		MachineRegistry.WEATHERCONTROLLER.addCrafting(new Object[]{"s s", "sls", "pcp", 'l', Block.daylightSensor, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel});

		MachineRegistry.MOBHARVESTER.addCrafting(new Object[]{"shs", "sps", 'h', MachineRegistry.HEATER.getCraftedProduct(), 'p', Item.enderPearl, 's', ItemStacks.basepanel});

		MachineRegistry.PROJECTOR.addCrafting(new Object[]{"sss", "gcl", "ppp", 'c', ItemStacks.pcb, 's', ItemStacks.steelingot, 'g', Block.glass, 'l', Block.glowStone, 'p', ItemStacks.basepanel});

		MachineRegistry.REFRESHER.addCrafting(new Object[]{"ses", "epe", "ses", 'p', Item.enderPearl, 's', ItemStacks.steelingot, 'e', Block.whiteStone});

		MachineRegistry.CAVESCANNER.addCrafting(new Object[]{"sps", "pcp", "sns", 'n', ItemStacks.sonar, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'p', ItemStacks.basepanel});

		MachineRegistry.SCALECHEST.addCrafting(new Object[]{"sss", "scs", "sss", 'c', Block.chest, 's', ItemStacks.steelingot});

		MachineRegistry.SPILLER.addCrafting(new Object[]{" p ", "s s", 'p', ItemStacks.pipe, 's', ItemStacks.steelingot});

		MachineRegistry.SMOKEDETECTOR.addCrafting(new Object[]{" S ", "RRR", " N ", 'S', ReikaItemHelper.stoneSlab, 'R', Item.redstone, 'N', Block.music});

		MachineRegistry.IGNITER.addCrafting(new Object[]{"OGO", "GCG", "OGO", 'O', Block.obsidian, 'G', Item.ingotGold, 'C', ItemStacks.combustor});

		MachineRegistry.CONTAINMENT.addCrafting(new Object[]{"lnl", "ddd", "sgs", 'd', Item.diamond, 's', ItemStacks.basepanel, 'n', Item.netherStar, 'g', Item.ingotGold, 'l', ReikaItemHelper.purpleDye});

		MachineRegistry.MAGNETIZER.addCrafting(new Object[]{"p p", "gmg", "prp", 'r', Item.redstone, 'p', ItemStacks.basepanel, 'm', ItemStacks.mount, 'g', ItemStacks.goldcoil});

		MachineRegistry.FREEZEGUN.addCrafting(new Object[]{" ss", "iig", "sgp", 'i', Block.ice, 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'g', ItemStacks.gearunit});

		MachineRegistry.SCREEN.addCrafting(new Object[]{"sss", "mcs", "ppp", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'm', ItemStacks.screen, 'c', ItemStacks.pcb});

		MachineRegistry.CCTV.addCrafting(new Object[]{" g ", "brs", " p ", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'b', Block.thinGlass, 'r', Item.redstone, 'g', Item.ingotGold});

		MachineRegistry.PURIFIER.addCrafting(new Object[]{"sbs", "prp", "sps", 'p', ItemStacks.basepanel, 's', ItemStacks.steelingot, 'r', Item.redstone, 'b', Block.fenceIron});

		MachineRegistry.MIRROR.addCrafting(new Object[]{" m ", " g ", "pcp", 'p', ItemStacks.basepanel, 'c', ItemStacks.pcb, 'm', ItemStacks.mirror, 'g', ItemStacks.steelgear});

		MachineRegistry.SOLARTOWER.addCrafting(new Object[]{"pPp", "iPi", "pPp", 'p', ItemStacks.basepanel, 'P', ItemStacks.pipe, 'i', ReikaItemHelper.inksac});

		MachineRegistry.RAILGUN.addCrafting(new Object[]{" H ", " A ", " B ", 'B', ItemStacks.railbase, 'A', ItemStacks.railaim, 'H', ItemStacks.railhead});

		MachineRegistry.LASERGUN.addCrafting(new Object[]{"CLB", "APG", " b ", 'b', ItemStacks.railbase, 'C', ItemStacks.bulb, 'L', ItemStacks.lens, 'P', ItemStacks.power, 'B', ItemStacks.barrel, 'A', ItemStacks.railaim, 'G', ItemStacks.gearunit});

		MachineRegistry.ITEMCANNON.addCrafting(new Object[]{"s c", "pcp", "pCr", 'C', ItemStacks.compressor, 'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.gearunit, 'r', Block.chest, 'p', ItemStacks.basepanel});

		MachineRegistry.BLOCKCANNON.addCrafting(new Object[]{"s c", "pcp", "pCr", 'C', ItemStacks.compressor,  'c', ItemStacks.screen, 's', ItemStacks.steelingot, 'c', ItemStacks.pcb, 'r', Block.chest, 'p', ItemStacks.basepanel});

		MachineRegistry.FRICTION.addCrafting(new Object[]{"S  ", "Sss", "SPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem});

		MachineRegistry.LANDMINE.addCrafting(new Object[]{" P ", "RGR", "SIS", 'P', Block.pressurePlateStone, 'S', ItemStacks.steelingot, 'I', ItemStacks.igniter, 'R', Item.redstone, 'G', Item.ingotGold});

		MachineRegistry.BUCKETFILLER.addCrafting(new Object[]{"SPS", "PCP", "SPS", 'P', ItemStacks.pipe, 'S', ItemStacks.steelingot, 'C', Block.chest});

		MachineRegistry.SPYCAM.addCrafting(new Object[]{"SCS", "PRP", "SGS", 'P', ItemStacks.basepanel, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Block.thinGlass, 'R', Item.redstone});

		MachineRegistry.COOLINGFIN.addCrafting(new Object[]{"SSS", "SSS", "PPP", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem});

		MachineRegistry.SELFDESTRUCT.addCrafting(new Object[]{"STS", "TCs", "STS", 'T', Block.tnt, 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem, 'C', ItemStacks.pcb});

		MachineRegistry.DISPLAY.addCrafting(new Object[]{"SES", "SCS", " P ", 'P', ItemStacks.basepanel, 'E', Item.enderPearl, 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb});

		MachineRegistry.COMPRESSOR.addCrafting(new Object[]{"SSS", " G ", "CPC", 'S', ItemStacks.steelingot, 'G', Block.glass, 'P', Block.pistonBase, 'C', ItemStacks.compressor});

		MachineRegistry.PNEUENGINE.addCrafting(new Object[]{"ppS", "sT ", "PPP", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem, 'p', ItemStacks.pipe, 'P', ItemStacks.basepanel, 'T', ItemStacks.turbine});
	}

	private static void addCraftItems() {

		int partcraft;
		switch(RotaryConfig.getDifficulty()) {
		case EASY:
			partcraft = 6;
			break;
		case MEDIUM:
			partcraft = 3;
			break;
		case HARD:
			partcraft = 2;
			break;
		default:
			partcraft = 3;
			break;
		}

		GameRegistry.addRecipe(ItemStacks.impeller, new Object[]{
				" S ", "SGS", " S ", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.compressor, new Object[]{
				"SSS", "SGS", "SSS", 'S', ItemStacks.steelingot, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.turbine, new Object[]{
				"sss", "sGs", "sss", 's', ItemStacks.prop, 'G', ItemStacks.compressor});
		GameRegistry.addRecipe(ItemStacks.diffuser, new Object[]{
				" SS", "S  ", " SS", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.combustor, new Object[]{
				"SSS", "SRS", "SGS", 'S', ItemStacks.steelingot, 'G', ItemStacks.igniter, 'R', Item.redstone});
		GameRegistry.addRecipe(ItemStacks.radiator, new Object[]{
				"GGG", "PPP", "SSS", 'G', Item.ingotGold, 'S', ItemStacks.steelingot, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});
		GameRegistry.addRecipe(ItemStacks.condenser, new Object[]{
				"SPS", "PSP", "SPS", 'S', ItemStacks.steelingot, 'P', new ItemStack(RotaryCraft.pipeplacer.itemID, 1, 1)});
		GameRegistry.addRecipe(ItemStacks.goldcoil, new Object[]{
				"GGG", "GSG", "GGG", 'S', ItemStacks.steelingot, 'G', Item.ingotGold});
		GameRegistry.addRecipe(ItemStacks.cylinder, new Object[]{
				"SSS", "S S", "SSS", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.compoundturb, new Object[]{
				"  S", " s ", "S  ", 'S', ItemStacks.turbine, 's', ItemStacks.shaftcore});

		GameRegistry.addRecipe(ItemStacks.shaftcore, new Object[]{
				"  s", " S ", "s  ", 'S', ItemStacks.steelingot, 's', ItemStacks.shaftitem});
		GameRegistry.addRecipe(ItemStacks.igniter, new Object[]{
				"G G", "SRS", "SSS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', Item.ingotGold});
		GameRegistry.addRecipe(ItemStacks.waterplate, new Object[]{
				"PPP", "PPP", "PPP", 'P', ItemStacks.basepanel});
		GameRegistry.addRecipe(ItemStacks.prop, new Object[]{
				" S ", " I ", " P ", 'P', ItemStacks.basepanel, 'S', ItemStacks.shaftitem, 'I', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.hub, new Object[]{
				"  B", " C ", "G  ", 'G', ItemStacks.steelgear, 'B', ItemStacks.bearing, 'C', ItemStacks.shaftcore});
		GameRegistry.addRecipe(ItemStacks.mirror, new Object[]{
				"GGG", "III", 'G', Block.glass, 'I', Item.ingotIron});
		GameRegistry.addRecipe(ItemStacks.railhead, new Object[]{
				"LLL", "LGL", "LLL", 'G', ItemStacks.power, 'L', ItemStacks.lim});
		GameRegistry.addRecipe(ItemStacks.railbase, new Object[]{
				" S ", "PGP", 'P', ItemStacks.basepanel, 'G', ItemStacks.gearunit, 'S', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.railaim, new Object[]{
				"sds", "CRC", "sgs", 'R', ItemStacks.radar, 'C', ItemStacks.pcb, 's', ItemStacks.steelingot, 'd', Item.diamond, 'g', ItemStacks.generator});

		GameRegistry.addRecipe(ItemStacks.bedingot, new Object[]{
				" B ", "BSB", " B ", 'S', ItemStacks.steelingot, 'B', ItemStacks.bedrockdust});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.basepanel, partcraft), new Object[]{
			"SSS", 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.mount, new Object[]{
				"S S","SBS", 'B', ItemStacks.basepanel, 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.drill, new Object[]{
				"SSS", "SSS", " S ", 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.presshead, new Object[]{
				"SOD", "ODB", "DBB", 'S', ItemStacks.steelingot, 'D', Item.diamond, 'O', Block.obsidian, 'B', ItemStacks.bedrockdust});
		GameRegistry.addRecipe(ItemStacks.screen, new Object[]{
				"SGS", "SCS", 'S', ItemStacks.steelingot, 'C', ItemStacks.pcb, 'G', Block.thinGlass});
		GameRegistry.addRecipe(ItemStacks.mixer, new Object[]{
				" S ", "SIS", " S ", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller});
		GameRegistry.addRecipe(ItemStacks.saw, new Object[]{
				"S S", " C ", "S S", 'S', ItemStacks.steelingot, 'C', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.pcb, new Object[]{
				"PGP", "RER", "GPG", 'P', ItemStacks.basepanel, 'G', Item.ingotGold, 'R', Item.redstone, 'E', Item.enderPearl});
		GameRegistry.addRecipe(ItemStacks.sonar, new Object[]{
				" S ", "SNS", "RCR", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'N', Block.music, 'C', ItemStacks.pcb});
		GameRegistry.addRecipe(ItemStacks.radar, new Object[]{
				"SSS", " G ", "RMR", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'G', Item.ingotGold, 'M', new ItemStack(RotaryCraft.engineitems.itemID, 1, 0)});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.belt, (partcraft+1)/2), new Object[]{
			"LLL", "LSL", "LLL", 'L', Item.leather, 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.bearing, new Object[]{
				"LLL", "LSL", "LLL", 'L', ItemStacks.bearingitem, 'S', ItemStacks.steelingot});
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bearingitem, 4), ItemStacks.steelingot);

		GameRegistry.addRecipe(ItemStacks.brake, new Object[]{
				" g ", "SBS", " G ", 'g', ItemStacks.gearunit, 'G', ItemStacks.steelgear, 'S', ItemStacks.shaftitem, 'B', ItemStacks.bearing});
		GameRegistry.addRecipe(ItemStacks.tenscoil, new Object[]{
				"WWW", "WSW", "WWW", 'W', ItemRegistry.SPRING.getStackOf(), 'S', ItemStacks.shaftitem});

		GameRegistry.addRecipe(ItemStacks.lens, new Object[]{
				" D ", "DGD", " D ", 'D', Item.diamond, 'G', Block.glass});
		GameRegistry.addRecipe(ItemStacks.power, new Object[]{
				"RER", "GGG", "SSS", 'R', Item.redstone, 'G', Item.ingotGold, 'E', Item.eyeOfEnder, 'S', ItemStacks.steelingot});
		GameRegistry.addRecipe(ItemStacks.barrel, new Object[]{
				"OOO", "ggG", "OOO", 'O', Block.obsidian, 'G', Block.glass, 'g', Block.glowStone});
		GameRegistry.addRecipe(ItemStacks.bulb, new Object[]{
				"GGG", "BDB", "BRB", 'D', Item.netherStar, 'G', Block.glowStone, 'R', Item.redstone, 'B', Item.blazeRod});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelgear, partcraft), new Object[]{
			" B ", "BBB", " B ", 'B', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.gearunit, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.gearunit4, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit});
		GameRegistry.addRecipe(ItemStacks.gearunit8, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit4});
		GameRegistry.addRecipe(ItemStacks.gearunit16, new Object[]{
				" GB", "BG ", 'B', ItemStacks.shaftitem, 'G', ItemStacks.gearunit8});

		GameRegistry.addRecipe(ItemStacks.wood2x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.woodgear});
		GameRegistry.addRecipe(ItemStacks.wood4x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.wood2x});
		GameRegistry.addRecipe(ItemStacks.wood8x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.wood4x});
		GameRegistry.addRecipe(ItemStacks.wood16x, new Object[]{
				" GB", "BG ", 'B', Item.stick, 'G', ItemStacks.wood8x});

		GameRegistry.addRecipe(ItemStacks.stone2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stonegear});
		GameRegistry.addRecipe(ItemStacks.stone4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone2x});
		GameRegistry.addRecipe(ItemStacks.stone8x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone4x});
		GameRegistry.addRecipe(ItemStacks.stone16x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.stonerod, 'G', ItemStacks.stone8x});

		GameRegistry.addRecipe(ItemStacks.diamond2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamondgear});
		GameRegistry.addRecipe(ItemStacks.diamond4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond2x});
		GameRegistry.addRecipe(ItemStacks.diamond8x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond4x});
		GameRegistry.addRecipe(ItemStacks.diamond16x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.diamondshaft, 'G', ItemStacks.diamond8x});

		GameRegistry.addRecipe(ItemStacks.bedrock2x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrockgear});
		GameRegistry.addRecipe(ItemStacks.bedrock4x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock2x});
		GameRegistry.addRecipe(ItemStacks.bedrock8x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock4x});
		GameRegistry.addRecipe(ItemStacks.bedrock16x, new Object[]{
				" GB", "BG ", 'B', ItemStacks.bedrockshaft, 'G', ItemStacks.bedrock8x});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.stonerod, 2), new Object[]{
			"  B", " B ", "B  ", 'B', Block.stone});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.shaftitem, partcraft), new Object[]{
			"  B", " B ", "B  ", 'B', ItemStacks.steelingot});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.diamondshaft, 8), new Object[]{
			"  B", " B ", "B  ", 'B', Item.diamond});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.bedrockshaft, 4), new Object[]{
			" D ", "DSD", " D ", 'D', ItemStacks.bedrockdust, 'S', ItemStacks.shaftitem});

		GameRegistry.addRecipe(ItemStacks.wormgear, new Object[]{
				"S  ", " G ", "  S", 'S', ItemStacks.shaftitem, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemStacks.woodgear, new Object[]{
				" W ", "WWW", " W ", 'W', ExpandedOreRecipe.getWoodList()}));
		GameRegistry.addRecipe(new ItemStack(ItemStacks.stonegear.itemID, 2, ItemStacks.stonegear.getItemDamage()), new Object[]{
			" W ", "WWW", " W ", 'W', Block.stone});
		GameRegistry.addRecipe(new ItemStack(ItemStacks.diamondgear.itemID, 8, ItemStacks.diamondgear.getItemDamage()), new Object[]{
			" W ", "WWW", " W ", 'W', Item.diamond});
		GameRegistry.addRecipe(new ItemStack(ItemStacks.bedrockgear.itemID, 8, ItemStacks.bedrockgear.getItemDamage()), new Object[]{
			"bWb", "WWW", "bWb", 'b', ItemStacks.bedrockdust, 'W', ItemStacks.steelingot});

		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemStacks.flywheelcore, new Object[]{
				"WWW", "WGW", "WWW", 'W', ExpandedOreRecipe.getWoodList(), 'G', ItemStacks.steelgear}));
		GameRegistry.addRecipe(ItemStacks.flywheelcore2, new Object[]{
				"WWW", "WGW", "WWW", 'W', Block.stone, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore3, new Object[]{
				"WWW", "WGW", "WWW", 'W', Item.ingotIron, 'G', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemStacks.flywheelcore4, new Object[]{
				"WWW", "WGW", "WWW", 'W', Item.ingotGold, 'G', ItemStacks.steelgear});

		GameRegistry.addRecipe(ItemStacks.lim, new Object[]{
				"WRW", "NNN", 'W', ItemStacks.goldcoil, 'N', ItemStacks.steelingot, 'R', Item.redstone});
	}

	private static void addToolItems() {
		GameRegistry.addRecipe(ItemRegistry.SPRING.getCraftedProduct(4), new Object[]{
			" S ", "S S", " S ", 'S', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemRegistry.TARGET.getCraftedProduct(1), new Object[]{
			" E ", "SRS", "SLS", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'E', Item.enderPearl, 'L', ReikaItemHelper.lapisDye});

		if (ReikaJavaLibrary.doesClassExist("mods.PamHarvestCraft.PamHarvestCraft")) {
			RotaryCraft.logger.log("ROTARYCRAFT: HarvestCraft found, not loading iron screwdriver recipe.");
		}
		else {
			GameRegistry.addRecipe(new ExpandedOreRecipe(ItemRegistry.SCREWDRIVER.getCraftedProduct(1), new Object[]{
				"I  ", " S ", "  W", 'S', Item.stick, 'I', Item.ingotIron, 'W', ExpandedOreRecipe.getWoodList()}));
			RotaryCraft.logger.log("ROTARYCRAFT: HarvestCraft not found, loading iron screwdriver recipe.");
		}

		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemRegistry.SCREWDRIVER.getCraftedProduct(1), new Object[]{
			"I  ", " S ", "  W", 'S', Item.stick, 'I', ItemStacks.steelingot, 'W', ExpandedOreRecipe.getWoodList()}));

		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemRegistry.METER.getCraftedProduct(1), new Object[]{
			"WWW", "WEW", " S ", 'S', Item.stick, 'E', Item.enderPearl, 'I', Item.ingotIron, 'W', ExpandedOreRecipe.getWoodList()}));
		GameRegistry.addRecipe(ItemRegistry.HANDBOOK.getCraftedProduct(1), new Object[]{
			"RSR", "PPP", "PPP", 'R', Item.redstone, 'S', Item.ingotIron, 'P', Item.paper});

		ItemStack pick = ItemRegistry.BEDPICK.getCraftedProduct(1);
		pick.addEnchantment(Enchantment.silkTouch, 1);
		GameRegistry.addRecipe(ItemRegistry.BEDAXE.getCraftedProduct(1), new Object[]{
			"BB ", "BS ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot});
		GameRegistry.addRecipe(pick, new Object[]{
				"BBB", " S ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.BEDSHOVEL.getCraftedProduct(1), new Object[]{
			" B ", " S ", " S ", 'S', ItemStacks.shaftitem, 'B', ItemStacks.bedingot});

		GameRegistry.addShapelessRecipe(ItemRegistry.NVH.getCraftedProduct(1), Item.helmetDiamond, ItemRegistry.NVG.getStackOf());

		GameRegistry.addRecipe(ItemRegistry.ULTRASOUND.getCraftedProduct(1), new Object[]{
			" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar});
		GameRegistry.addRecipe(ItemRegistry.MOTION.getCraftedProduct(1), new Object[]{
			" nr", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'n', ItemStacks.sonar, 'r', ItemStacks.radar});
		GameRegistry.addRecipe(ItemRegistry.VACUUM.getCraftedProduct(1), new Object[]{
			" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.impeller, 'n', ItemStacks.diffuser});
		GameRegistry.addRecipe(ItemRegistry.STUNGUN.getCraftedProduct(1), new Object[]{
			" n ", "scs", " s ", 's', ItemStacks.steelingot, 'c', ItemStacks.sonar, 'n', ItemStacks.diffuser});
		GameRegistry.addRecipe(ItemRegistry.GRAVELGUN.getCraftedProduct(1), new Object[]{
			" d ", "gcg", "sss", 's', ItemStacks.steelingot, 'c', Block.chest, 'd', Block.dispenser, 'g', ItemStacks.steelgear});
		GameRegistry.addRecipe(ItemRegistry.FIREBALL.getCraftedProduct(1), new Object[]{
			"b b", "scs", "srs", 's', ItemStacks.steelingot, 'c', ItemStacks.combustor, 'r', Item.redstone, 'b', Item.blazeRod});
		GameRegistry.addRecipe(ItemRegistry.HANDCRAFT.getCraftedProduct(1), new Object[]{
			" g ", "scs", " g ", 's', ItemStacks.steelingot, 'g', Item.ingotGold, 'c', Block.workbench});
		GameRegistry.addRecipe(ItemRegistry.NVG.getCraftedProduct(1), new Object[]{
			"scs", "ese", 's', ItemStacks.steelingot, 'c', ItemStacks.screen, 'e', Item.eyeOfEnder});

		GameRegistry.addRecipe(ItemRegistry.IOGOGGLES.getCraftedProduct(1), new Object[]{
			"scs", "ese", 's', ItemStacks.steelingot, 'c', Item.enderPearl, 'e', Item.redstone});

	}

	private static void addMisc() {
		if (ConfigRegistry.CRAFTABLEBEDROCK.getState())
			GameRegistry.addRecipe(new ItemStack(Block.bedrock.blockID, 1, 0), new Object[]{
				"DDD", "DDD", "DDD", 'D', ItemStacks.bedrockdust});

		GameRegistry.addRecipe(ItemRegistry.CANOLA.getCraftedMetadataProduct(1, 1), new Object[]{
			"DDD", "DDD", "DDD", 'D', ItemRegistry.CANOLA.getStackOf()});

		GameRegistry.addShapelessRecipe(ItemRegistry.CANOLA.getCraftedProduct(9),
				ItemRegistry.CANOLA.getStackOfMetadata(1));

		GameRegistry.addRecipe(ReikaItemHelper.oakWood, new Object[]{
				"WW", "WW", 'W', ItemStacks.sawdust});
		GameRegistry.addRecipe(ReikaItemHelper.spruceWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.BLACK.getStackOf()});
		GameRegistry.addRecipe(ReikaItemHelper.birchWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.WHITE.getStackOf()});
		GameRegistry.addRecipe(ReikaItemHelper.jungleWood, new Object[]{
				"WWD", "WW ", 'W', ItemStacks.sawdust, 'D', ReikaDyeHelper.RED.getStackOf()});

		GameRegistry.addRecipe(new ItemStack(Item.paper.itemID, 8, 0), new Object[]{
			" W ", "SSS", "RRR", 'R', Block.stone, 'S', ItemStacks.sawdust, 'W', Item.bucketWater});

		GameRegistry.addRecipe(new ItemStack(RotaryCraft.blastglass, 16, 0), new Object[]{
			"OOO", "OOO", 'O', RotaryCraft.obsidianglass});

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 15), ItemStacks.steelgear);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 48), ItemStacks.gearunit);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 114), ItemStacks.gearunit4);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 246), ItemStacks.gearunit8);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 510), ItemStacks.gearunit16);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.shaftitem);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 9), ItemStacks.basepanel);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.scrap, 45), ItemStacks.mount);

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"rrr", "rrr", "rr ", 'r', Block.rail});
		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"rrr", "rrr", "rr ", 'r', Block.fenceIron});

		GameRegistry.addRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.ironscrap, 3), new Object[]{
			"b", 'b', Item.bucketEmpty});

		GameRegistry.addRecipe(ItemStacks.steelblock, new Object[]{
				"BBB", "BBB", "BBB", 'B', ItemStacks.steelingot});

		GameRegistry.addRecipe(ItemStacks.anthrablock, new Object[]{
				"BBB", "BBB", "BBB", 'B', ItemStacks.anthracite});

		GameRegistry.addRecipe(ItemStacks.lonsblock, new Object[]{
				"BBB", "BBB", "BBB", 'B', ItemStacks.lonsda});

		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.steelingot, 9), ItemStacks.steelblock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.anthracite, 9), ItemStacks.anthrablock);
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.lonsda, 9), ItemStacks.lonsblock);

		GameRegistry.addShapelessRecipe(ItemStacks.salt, Item.bucketWater);
		GameRegistry.addRecipe(new ShapelessOreRecipe(ItemStacks.silveriodide, ItemStacks.salt, "ingotSilver"));
		GameRegistry.addShapelessRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.nitrate, 4), Item.gunpowder, Item.redstone, ItemStacks.salt, ItemStacks.coaloredust);

		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedProduct(3), new Object[]{
			"ss ", "s  ", 's', ItemStacks.steelingot});

		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 1), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(0), 'p', ExpandedOreRecipe.getWoodList()}));
		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 2), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(1), 'p', ExpandedOreRecipe.getWoodList()}));
		GameRegistry.addRecipe(new ExpandedOreRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 3), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(2), 'p', ExpandedOreRecipe.getWoodList()}));
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 4), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(3), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 5), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(4), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 6), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(5), 'p', Block.stone});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 7), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(6), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 8), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(7), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 9), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(8), 'p', Item.ingotIron});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 10), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(9), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 11), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(10), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 12), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(11), 'p', Item.ingotGold});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 13), new Object[]{
			"p  ", " s ", "  p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(12), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 14), new Object[]{
			"p p", " s ", "p p", 's', ItemRegistry.RAILGUN.getStackOfMetadata(13), 'p', ItemStacks.bedingot});
		GameRegistry.addRecipe(ItemRegistry.RAILGUN.getCraftedMetadataProduct(1, 15), new Object[]{
			"ppp", "psp", "ppp", 's', ItemRegistry.RAILGUN.getStackOfMetadata(14), 'p', ItemStacks.bedingot});

		GameRegistry.addRecipe(ItemRegistry.MINECART.getCraftedProduct(1), new Object[]{
			"g", "m", 'g', EnumEngineType.GAS.getCraftedProduct(), 'm', new ItemStack(Item.minecartEmpty)});

		GameRegistry.addRecipe(ItemRegistry.SHELL.getCraftedProduct(16), new Object[]{
			" s ", "sns", " s ", 's', ItemStacks.steelingot, 'n', ItemStacks.nitrate});

		addSlideRecipes();
	}

	private static void addSlideRecipes() {
		GameRegistry.addRecipe(ItemRegistry.SLIDE.getCraftedProduct(0), new Object[]{ //Wood shaft unit
			"PPP", "PGP", "PPP", 'G', Block.thinGlass, 'P', Item.paper});

		Random r = new Random();
		HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		boolean[] hasMapping = new boolean[16];
		for (int i = 0; i < 16; i++) {
			int randVal = r.nextInt(16);
			while (hasMapping[randVal]) {
				randVal = r.nextInt(16);
			}
			colors.put(i, randVal);
			hasMapping[randVal] = true;
			//ReikaJavaLibrary.pConsole("Color "+i+" registered to value "+randVal);
		}
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 24; j++) {
				int color = colors.get(i);
				while (color+j >= 24) {
					color -= 24;
				}
				if (color+j < 0)
					throw new RuntimeException("Color mapping < 0 at color "+color+" and dye color "+i+" for slide "+j);
				GameRegistry.addShapelessRecipe(ItemRegistry.SLIDE.getCraftedMetadataProduct(1, color+j), new ItemStack(Item.dyePowder.itemID, 1, i), ItemRegistry.SLIDE.getStackOfMetadata(j));
				//ReikaJavaLibrary.pConsole("Registering recipe with slide "+j+" and color "+i+" to result slide "+(color+j));
			}
		}
	}

	private static void addItemBlocks() {

		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(0, new Object[]{"SW ", " GS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'W', ItemStacks.wormgear, 'G', ItemStacks.steelgear}); //Worm gear
		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(1, new Object[]{"BSB", "BSB", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'B', ItemStacks.bearing}); //CVT
		MachineRegistry.ADVANCEDGEARS.addMetaCrafting(2, new Object[]{"BCS", " M ", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem, 'B', ItemStacks.brake, 'C', ItemStacks.tenscoil}); //Coil

		MachineRegistry.FLYWHEEL.addMetaCrafting(0, new Object[]{"W", "M", 'W', ItemStacks.flywheelcore, 'M', ItemStacks.mount});
		MachineRegistry.FLYWHEEL.addMetaCrafting(1, new Object[]{"W", "M", 'W', ItemStacks.flywheelcore2, 'M', ItemStacks.mount});
		MachineRegistry.FLYWHEEL.addMetaCrafting(2, new Object[]{"W", "M", 'W', ItemStacks.flywheelcore3, 'M', ItemStacks.mount});
		MachineRegistry.FLYWHEEL.addMetaCrafting(3, new Object[]{"W", "M", 'W', ItemStacks.flywheelcore4, 'M', ItemStacks.mount});

		MachineRegistry.SHAFT.addRecipe(new ExpandedOreRecipe(MachineRegistry.SHAFT.getCraftedMetadataProduct(0), new Object[]{"BSB", "BBB", 'B', ExpandedOreRecipe.getWoodList(), 'S', Item.stick}));
		MachineRegistry.SHAFT.addMetaCrafting(1, new Object[]{"sSs", "sss", 's', ReikaItemHelper.stoneSlab, 'S', ItemStacks.stonerod});
		MachineRegistry.SHAFT.addMetaCrafting(2, new Object[]{"S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.shaftitem});
		MachineRegistry.SHAFT.addMetaCrafting(3, new Object[]{"S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.diamondshaft});
		MachineRegistry.SHAFT.addMetaCrafting(4, new Object[]{"S", "M", 'M', ItemStacks.mount, 'S', ItemStacks.bedrockshaft});

		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.DC.ordinal(), new Object[]{"SSS", "SRs", "PRP", 'S', ItemStacks.steelingot, 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.WIND.ordinal(), new Object[]{"SSS", "SHS", "SSS", 'S', ItemStacks.prop, 'H', ItemStacks.hub});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.STEAM.ordinal(), new Object[]{"SSS", "CIs", "PGP", 'S', ItemStacks.steelingot, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', Item.ingotGold, 'C', ItemStacks.condenser});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.GAS.ordinal(), new Object[]{"CgC", "SGs", "PIP", 'g', Item.ingotGold, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.gearunit, 'C', ItemStacks.cylinder});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.AC.ordinal(), new Object[]{"SSS", "SGs", "PRP", 'S', Item.ingotGold, 'R', Item.redstone, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'G', ItemStacks.goldcoil});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.SPORT.ordinal(), new Object[]{"CrC", "SGs", "PIP", 'C', ItemStacks.cylinder, 'S', ItemStacks.igniter, 'I', ItemStacks.impeller, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem, 'r', ItemStacks.radiator, 'G', ItemStacks.gearunit});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.HYDRO.ordinal(), new Object[]{"PPP", "PGP", "PPP", 'P', ItemStacks.waterplate, 'G', ItemStacks.shaftcore});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.MICRO.ordinal(), new Object[]{"CSS", "cTs", "PPP", 'S', ItemStacks.steelingot, 'C', ItemStacks.compressor, 'c', ItemStacks.combustor, 'T', ItemStacks.turbine, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem});
		MachineRegistry.ENGINE.addMetaCrafting(EnumEngineType.JET.ordinal(), new Object[]{"DCS", "ScS", "PTs", 'S', ItemStacks.steelingot, 'D', ItemStacks.diffuser, 'C', ItemStacks.compressor, 'c', ItemStacks.combustor, 'T', ItemStacks.compoundturb, 'P', ItemStacks.basepanel, 's', ItemStacks.shaftitem});

		int pipenum;
		switch(RotaryConfig.getDifficulty()) {
		case EASY:
			pipenum = 32;
			break;
		case MEDIUM:
			pipenum = 16;
			break;
		case HARD:
			pipenum = 8;
			break;
		default:
			pipenum = 16;
			break;
		}

		MachineRegistry.HOSE.addRecipe(new ExpandedOreRecipe(ReikaItemHelper.getSizedItemStack(ItemStacks.hose, pipenum), new Object[]{"W W", "W W", "W W", 'W', ExpandedOreRecipe.getWoodList()}));
		MachineRegistry.PIPE.addSizedCrafting(pipenum, new Object[]{"S S", "S S", "S S", 'S', ItemStacks.steelingot});
		MachineRegistry.FUELLINE.addSizedCrafting(pipenum, new Object[]{"O O", "O O", "O O", 'O', Block.obsidian});

		ItemStack gear;
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(0));
		MachineRegistry.GEARBOX.addRecipe(new ExpandedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', ExpandedOreRecipe.getWoodList(), 'G', ItemStacks.wood2x}));
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(5));
		MachineRegistry.GEARBOX.addRecipe(new ExpandedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', ExpandedOreRecipe.getWoodList(), 'G', ItemStacks.wood4x}));
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(10));
		MachineRegistry.GEARBOX.addRecipe(new ExpandedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', ExpandedOreRecipe.getWoodList(), 'G', ItemStacks.wood8x}));
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(15));
		MachineRegistry.GEARBOX.addRecipe(new ExpandedOreRecipe(gear, new Object[]{"MGM", "MMM", 'M', ExpandedOreRecipe.getWoodList(), 'G', ItemStacks.wood16x}));

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(1));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(6));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(11));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(16));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"MGM", "MMM", 'M', ReikaItemHelper.stoneSlab, 'G', ItemStacks.stone16x});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(2));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(7));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit4});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(12));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit8});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(17));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.gearunit16});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(3));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(8));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(13));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(18));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.diamond16x});

		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(4));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock2x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(9));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock4x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(14));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock8x});
		gear = addDamageNBT(MachineRegistry.GEARBOX.getCraftedMetadataProduct(19));
		MachineRegistry.GEARBOX.addRecipe(gear, new Object[]{"G", "M", 'M', ItemStacks.mount, 'G', ItemStacks.bedrock16x});
	}

	private static void addFurnace() {
		ReikaRecipeHelper.addSmelting(ItemStacks.aluminumpowder, ItemStacks.aluminumingot, 0.4F);
		ReikaRecipeHelper.addSmelting(ItemStacks.sludge, ItemRegistry.ETHANOL.getStackOf(), 0.5F);

		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 24, new ItemStack(Item.coal.itemID, 1, 0), 0.1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 25, new ItemStack(Item.ingotIron.itemID, 1, 0), 0.7F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 26, new ItemStack(Item.ingotGold.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 27, new ItemStack(Item.redstone.itemID, 4, 0), 0.5F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 28, new ItemStack(Item.dyePowder.itemID, 6, 4), 0.6F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 29, new ItemStack(Item.diamond.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 30, new ItemStack(Item.emerald.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 31, new ItemStack(Item.netherQuartz.itemID, 1, 0), 1F);
		FurnaceRecipes.smelting().addSmelting(RotaryCraft.extracts.itemID, 32, new ItemStack(ItemStacks.silveringot.itemID, 1, ItemStacks.silveringot.getItemDamage()), 1F);

		ExtractorModOres.addSmelting();
	}

	private static ItemStack addDamageNBT(ItemStack is) {
		is.setTagCompound(new NBTTagCompound());
		is.stackTagCompound.setInteger("damage", 0);
		return is;
	}

	private static void addRecipeToBoth(ItemStack out, Object... in) {
		GameRegistry.addRecipe(out, in);
		WorktableRecipes.getInstance().addRecipe(out, in);
	}
}
