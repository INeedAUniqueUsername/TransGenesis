package designType.subElements;
import designType.TypeFactory;
import designType.Types;
import xml.DesignAttribute;
import xml.DesignElement;
import xml.RenameableElement;
import xml.DesignAttribute.ValueType;
import static xml.DesignAttribute.ValueType.*;
import static xml.DesignAttribute.*;
public class SubElementFactory {
	public enum SystemGroupElements implements ElementType {
		;

		@Override
		public DesignElement get() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	public enum DockScreensElements implements ElementType {
		DockScreen_Named, Pane_Named, Action;
		
		@Override
		public DesignElement get() {
			DesignElement e = null;
			switch(this) {
			case DockScreen_Named:
				e = new RenameableElement("DockScreen");
				e.addOptionalSingleSubElements(TypeFactory.createSingleSubElementsForType(Types.DockScreen));
				break;
			case Pane_Named:
				e = new RenameableElement("Pane");
				e.addAttributes(
						att("layout", LAYOUT),
						att("desc", STRING),
						att("noListNavigation", BOOLEAN),
						att("showCounter", BOOLEAN),
						att("showTextInput", BOOLEAN)
						);
				DesignElement controls = new DesignElement("Controls");
				for(String name : new String[] {"Counter", "ItemDisplay", "ItemListDisplay", "Text", "TextInput"}) {
					DesignElement subelement = new DesignElement(name);
					subelement.addAttributes(
							att("id", STRING),
							att("style", STYLE_CONTROLS)
							);
					controls.addOptionalSingleSubElements(subelement);
				}
				DesignElement actions = new DesignElement("Actions");
				actions.addOptionalMultipleSubElements(DockScreensElements.Action);
				e.addOptionalSingleSubElements(
						new DesignElement("OnPaneInit"),
						new DesignElement("Initialize"),
						controls,
						actions
						);
				break;
			case Action:
				e = new DesignElement("Action");
				e.addAttributes(
						att("name", STRING),
						att("id", STRING),
						att("descID", STRING),
						att("prevKey", BOOLEAN),
						att("nextKey", BOOLEAN),
						att("default", BOOLEAN),
						att("cancel", BOOLEAN),
						att("key", CHARACTER),
						att("minor", BOOLEAN)
						
						);
				DesignElement navigate = new DesignElement("Navigate");
				navigate.addAttributes(att("screen", STRING));
				DesignElement showPane = new DesignElement("ShowPane");
				showPane.addAttributes(att("pane", STRING));
				e.addOptionalSingleSubElements(
						navigate,
						showPane,
						new DesignElement("Exit")
						);
				break;
			}
			return e;
		}
	}
	public enum DisplayAttributesElements implements ElementType {
		ItemAttribute;

		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			switch(this) {
			case ItemAttribute:
				e.addAttributes(
						att("label", STRING),
						att("criteria", STRING),
						att("labelType", LABEL_TYPE)
						);
				break;
			};
			return e;
		}
		
	}
	public static enum AdventureDescElements implements ElementType {
		EncounterOverrides,

		ArmorDamageAdj,
		ShieldDamageAdj,
		
		;
		@Override
		public DesignElement get() {
			DesignElement result = new DesignElement(name());
			switch(this) {
			case EncounterOverrides:
				result = Types.StationType.get().getOptionalSingleByName("Encounter");
				result.addAttributes(att("unid", TYPE_STATION));
				break;
			case ArmorDamageAdj:
			case ShieldDamageAdj:
				result.addAttributes(
						att("level", WHOLE),
						att("damageAdj", STRING)
						);
				break;
			default:
				break;
			
			}
			return result;
		}
	}
	public static enum DisplayElements implements ElementType {
		Group,
		Text,
		Image;
		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			switch(this) {
			case Group:
				e.addAttributes(
						att("left", INTEGER),
						att("top", INTEGER),
						att("width", INTEGER),
						att("height", INTEGER),
						att("center", INTEGER),
						att("vcenter", INTEGER)
						);
				break;
			case Text:
				e.addAttributes(
						att("id", STRING),
						att("left", INTEGER),
						att("right", INTEGER),
						att("top", INTEGER),
						att("bottom", INTEGER),
						att("font", FONT),
						att("color", HEX_COLOR),
						att("align", ALIGN_HORIZONTAL)
						);
				break;
			case Image:
				e.addAttributes(
						att("left", INTEGER),
						att("right", INTEGER),
						att("top", INTEGER),
						att("bottom", INTEGER),
						att("align", ALIGN_HORIZONTAL),
						att("valign", ALIGN_VERTICAL),
						att("transparent", BOOLEAN)
						);
				break;
			}
			return e;
		}
	}
	public static enum DeviceGeneratorElements implements ElementType {
		Device, Item,
		DeviceSlot,
		Table,
		Group, Devices,
		LevelTable,
		Null;

		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			switch(this) {
			case Device:
			case Item:
				e.addAttributes(
						att("deviceID", ValueType.TYPE_DEVICE),
						att("count", WHOLE),
						att("level", WHOLE),
						att("posAngle", INTEGER),
						att("posRadius", INTEGER),
						att("posZ", INTEGER),
						att("external", BOOLEAN),
						att("omnidirectional", BOOLEAN),
						att("minFireArc", INTEGER),
						att("maxFireArc", INTEGER),
						att("linkedFire", ValueType.LINKED_FIRE_OPTIONS),
						att("secondaryWeapon", BOOLEAN),
						att("hpBonus", INTEGER)
						);
				e.addOptionalMultipleSubElements(
						ItemGeneratorElements.values()
						);
				break;
			case DeviceSlot:
				e.addAttributes(
						att("criteria", STRING),
						att("maxCount", WHOLE),
						
						att("posAngle", INTEGER),
						att("posRadius", INTEGER),
						att("posZ", INTEGER),
						att("external", BOOLEAN),
						att("omnidirectional", BOOLEAN),
						att("minFireArc", INTEGER),
						att("maxFireArc", INTEGER),
						att("linkedFire", ValueType.LINKED_FIRE_OPTIONS),
						att("secondaryWeapon", BOOLEAN),
						att("hpBonus", INTEGER)
						);
				break;
			case Group:
			case Devices:
			case Table:
				e.addOptionalMultipleSubElements(DeviceTableElements.values());
				break;
			case LevelTable:
				e.addOptionalMultipleSubElements(DeviceLevelTableElements.values());
				break;
			case Null:
				break;
			}
			return e;
		}
		
		//Same as DeviceGeneratorElements, but every element has a chance attribute
		enum DeviceTableElements implements ElementType {
			Device, Item,
			DeviceSlot,
			Table,
			Group, Devices,
			LevelTable,
			Null;
			public DesignElement get() {
				DesignElement result = DeviceGeneratorElements.valueOf(name()).get();
				result.addAttributes(att("chance", WHOLE));
				return result;
			}
		}
		
		//Same as DeviceGeneratorElements, but every element has a levelFrequency attribute
		enum DeviceLevelTableElements implements ElementType {
			Device, Item,
			DeviceSlot,
			Table,
			Group, Devices,
			LevelTable,
			Null;
			public DesignElement get() {
				DesignElement result = DeviceGeneratorElements.valueOf(name()).get();
				result.addAttributes(att("levelFrequency", LEVEL_FREQUENCY));
				return result;
			}
		}
	}
	public static enum ItemGeneratorElements implements ElementType {
		Item,
		Table,
		RandomItem,
		Group, Components, Items, AverageValue,
		Lookup,
		LevelTable,
		LocationCriteria,
		Null;

		@Override
		public DesignElement get() {
			// TODO Auto-generated method stub
			DesignElement e = new DesignElement(name());
			e.addAttributes(
					att("chance", WHOLE),						//Every element//Table, LevelTable, Group, Components, Items, AverageValue, LocationCriteria
					att("count", DICE_RANGE)						//Every element//Table, LevelTable, Group, Components, Items, AverageValue, LocationCriteria
					);
			switch(this) {
			case Item:
				e.addAttributes(
						att("item", ValueType.TYPE_ITEM),
						att("damaged", WHOLE),
						att("debugOnly", BOOLEAN),
						
						att("enhanced", WHOLE),
						att("enhancement", STRING)
						);
				break;
			case Table:
				e.addOptionalMultipleSubElements(
						ItemGeneratorElements.values()
						);
				break;
			case RandomItem:
				e.addAttributes(
						att("criteria", STRING),
						att("attributes", STRING),
						att("modifiers", STRING),
						att("categories", STRING),
						att("levelFrequency", LEVEL_FREQUENCY),
						att("level", WHOLE),
						att("levelCurve", WHOLE),
						att("damaged", WHOLE),
						
						att("enhanced", WHOLE),
						att("enhancement", STRING)
						);
				break;
			case Group:
			case Components:
			case Items:
			case AverageValue:
				e.addAttributes(
						att("levelValue", ValueType.CURRENCY_VALUE_SEQUENCE),
						att("value", CURRENCY_VALUE_SEQUENCE)
						);
				e.addOptionalMultipleSubElements(ItemGeneratorElements.values());
				break;
			case Lookup:
				e.addAttributes(att("table", ValueType.TYPE_ITEM_TABLE));
				break;
			case LevelTable:
				e.addOptionalMultipleSubElements(LevelTableElements.values());
				break;
			case LocationCriteria:
				e.addOptionalMultipleSubElements(LocationCriteriaElements.values());
				break;
			}
			return e;
		}
		enum LocationCriteriaElements implements ElementType {
			Item,
			Table,
			RandomItem,
			Group, Components, Items, AverageValue,
			Lookup,
			LevelTable,
			LocationCriteria,
			Null;
			public DesignElement get() {
				DesignElement e = ItemGeneratorElements.valueOf(name()).get();
				e.addAttributes(att("criteria", STRING));
				return e;
			}
			
		}
		enum LevelTableElements implements ElementType {
			Item,
			Table,
			RandomItem,
			Group, Components, Items, AverageValue,
			Lookup,
			LevelTable,
			LocationCriteria,
			Null;
			public DesignElement get() {
				DesignElement e = ItemGeneratorElements.valueOf(name()).get();
				e.addAttributes(att("levelFrequency", LEVEL_FREQUENCY));
				return e;
			}
		}
	}
	public static enum EffectElements implements ElementType {
		;

		@Override
		public DesignElement get() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	public static enum TradeElements implements ElementType {
		AcceptDonation,
		Buy,
		Sell,
		Refuel,
		RepairArmor,
		ReplaceArmor,
		InstallDevice,
		UpgradeDevice,
		RemoveDevice,
		EnhanceItem,
		RepairItem,
		BuyShip,
		SellShip,
		Custom,;

		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			e.addAttributes(
					att("actualPrice", BOOLEAN),
					att("criteria", STRING),
					att("inventoryAdj", WHOLE),
					att("messageID", STRING),
					att("priceAdj", PRICE_ADJ),					
					att("noDescription", BOOLEAN),
					att("upgradeInstallOnly", BOOLEAN),
					att("levelFrequency", LEVEL_FREQUENCY)
					);
			//All trade elements have the same attributes
			return e;
		}
	}
	public static enum SovereignElements implements ElementType {
		//Relationships,
			Relationship,
			
		;

		@Override
		public DesignElement get() {
			// TODO Auto-generated method stub
			DesignElement e = new DesignElement(name());
			switch(this) {
			case Relationship:
				e.addAttributes(
						att("sovereign", TYPE_SOVEREIGN),
						att("disposition", DISPOSITION),
						att("mutual", BOOLEAN)
						);
				break;
			}
			return e;
		}
		
	}
	public static enum ShipGeneratorElements implements ElementType {
		;

		@Override
		public DesignElement get() {
			// TODO Auto-generated method stub
			return new DesignElement("W.I.P.");
		}
		
	}
	public static enum SystemCriteria implements ElementType {
		Attributes,
		Chance,
		DistanceBetweenNodes,
		DistanceTo,
		StargateCount,;

		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			switch(this) {
			case Attributes:
				e.addAttributes(att("criteria", STRING));
				break;
			case Chance:
				e.addAttributes(att("chance", WHOLE));
				break;
			case DistanceBetweenNodes:
				e.addAttributes(
						att("min", WHOLE),
						att("max", WHOLE)
						);
				break;
			case DistanceTo:
				e.addAttributes(
						att("criteria", STRING),
						att("nodeID", STRING),
						att("min", WHOLE),
						att("max", WHOLE)
						);
				break;
			case StargateCount:
				e.addAttributes(
						att("min", WHOLE),
						att("max", WHOLE)
						);
				break;
			default:
				break;
			}
			return e;
		}
	}
	public static enum ExtensionElements implements ElementType {
		Module, Library;

		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			switch(this) {
			case Library:
				e.addAttributes(att("unid", TYPE_MOD));
				break;
			case Module:
				e.addAttributes(att("filename", STRING));
				break;
			}
			return e;
		}
	}
	
	/*
	switch(s) {
	
		
	case Relationship:
		e = new Element("Relationship");
		
		break;
	case EdgeMask:
		e = new Element("EdgeMask");
		initializeImage(e);
		break;
	case Image:
		e = new Element("Image");
		initializeImage(e);
		break;
	default:
		System.out.println("Not supported: " + s.toString());
		/*
		try {
			throw new Exception("Not supported: " + s.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		*//*
		e = new Element(s.name());
		break;
	}
	*/
	public static DesignAttribute[] createImageDescAttributes() {
		return new DesignAttribute[] {
				att("imageID", TYPE_IMAGE),
				att("imageX", WHOLE),
				att("imageY", WHOLE),
				att("imageWidth", WHOLE),
				att("imageHeight", WHOLE),
				att("imageFrameCount", WHOLE),
				att("rotationCount", WHOLE),
				att("rotationColumns", WHOLE),
				att("animationColumns", WHOLE),
				att("imageTicksPerFrame", WHOLE),
				att("flashTicks", WHOLE),
				att("blending", BLENDING),
				att("viewportRatio", DECIMAL),
				att("viewportSize", INTEGER),
				att("rotationOffset", INTEGER),
				att("xOffset", INTEGER),
				att("yOffset", INTEGER)
		};
	}
	public static DesignAttribute[] createNameAttributes() {
		return new DesignAttribute[] {
				att("definiteArticle", BOOLEAN),
				att("firstPlural", BOOLEAN),
				att("esPlural", BOOLEAN),
				att("customPlural", BOOLEAN),
				att("secondPlural", BOOLEAN),
				att("reverseArticle", BOOLEAN),
				att("noArticle", BOOLEAN),
				att("personalName", BOOLEAN)
		};
	}
	
	//WIP
	public static DesignElement createEffects() {
		return new DesignElement("Effects");
	}
}