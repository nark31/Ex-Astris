package ExAstris;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import ExAstris.Data.ModData;
import ExAstris.Proxy.Proxy;
import ExAstris.Bridge.*;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModData.ID, name = ModData.NAME, version = ModData.VERSION, dependencies = ModData.DEPENDENCIES)
public class ExAstris {
	@Instance(ModData.ID)
	public static ExAstris instance;
	
	@SidedProxy(clientSide = "ExAstris.Proxy.ProxyClient", serverSide = "ExAstris.Proxy.ProxyServer")
	public static Proxy proxy = Proxy.getProxy();
	
	public static Configuration config;
	public static Logger log;
	@EventHandler
	public void PreInitialize(FMLPreInitializationEvent event)
	{
		log = LogManager.getLogger(ModData.NAME);
		
		ModData.setMetadata(event.getModMetadata());
		
		
		config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "ExAstris.cfg"));
		config.load();
		
		ModData.load(config);
		
		ExAstrisBlock.registerBlocks();
		ExAstrisItem.registerItems();
		
		if(config.hasChanged())
			config.save();
		
		proxy.initializeSounds();
		proxy.initializeRenderers();
		MinecraftForge.EVENT_BUS.register(this);
	}
	@EventHandler
	public void Initialize(FMLInitializationEvent event)
	{
		ExAstrisRecipe.registerCraftingRecipes();
		FMLInterModComms.sendMessage("Waila", "register", "ExAstris.Bridge.Waila.callbackRegister");
	}
	@EventHandler
	public void PostInitialize(FMLPostInitializationEvent event)
	{
		if (Loader.isModLoaded("ThermalExpansion"))
		{
			log.info("+++ - Found ThermalExpansion!");

			ThermalExpansion.Initialize();
		}
		
		if (Loader.isModLoaded("Thaumcraft"))
		{
			log.info("+++ - Found Thaumcraft!");

			Thaumcraft.Initialize();
		}
		

		if (Loader.isModLoaded("TConstruct"))
		{
			log.info("+++ - Found TConstruct!");

			TConstruct.Initialize();
		}
		
		if (Loader.isModLoaded("TSteelworks"))
		{
			log.info("+++ - Found TSteelworks!");

			TSteelworks.Initialize();
		}
		
		if (Loader.isModLoaded("MineFactoryReloaded"))
		{
			log.info("+++ - Found MineFactoryReloaded!");

			MineFactoryReloaded.Initialize();
		}
		if(Loader.isModLoaded("AWWayofTime"))
		{

			log.info("+++ - Found Blood Magic!");

			BloodMagic.Initialize();
		}
	}
}
