package mod;

import designType.Types;
import designType.subElements.ElementType;
import xml.DesignAttribute;
import xml.DesignElement;
import xml.DesignAttribute.ValueType;
import static xml.DesignAttribute.*;
import static xml.DesignAttribute.ValueType.STRING;
import static xml.DesignAttribute.ValueType.TYPE_MOD;
public class ExtensionFactory {
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
	public static enum EmbeddedExtensionElements implements ElementType {
		CoreLibrary, TranscendenceLibrary, TranscendenceAdventure;

		@Override
		public DesignElement get() {
			DesignElement e = new DesignElement(name());
			e.addAttributes(att("filename", STRING));
			return e;
		}
	}
	public static enum Extensions implements ElementType {
		TranscendenceAdventure,
		TranscendenceExtension,
		TranscendenceLibrary,
		CoreLibrary,
		TranscendenceUniverse,
		TranscendenceModule,
		;
		
		@Override
		public TranscendenceMod get() {
			TranscendenceMod e = new TranscendenceMod(name());
			switch(this) {
			//TranscendenceAdventure is an extension with an AdventureDesc
			case TranscendenceAdventure:
				e.addSubElements(
						Types.AdventureDesc.get()
						);
			case TranscendenceExtension:
			case TranscendenceLibrary:
			case TranscendenceUniverse:
			case CoreLibrary:
				e.addAttributes(
						att("apiVersion", ValueType.INTEGER, ""),
						att("autoInclude", ValueType.BOOLEAN, ""),//"false"),
						att("autoIncludeForCompatibility", ValueType.BOOLEAN, ""),//"false"),
						att("coverImageID", ValueType.TYPE_IMAGE, ""),
						att("credits", ValueType.STRING, ""),
						att("debugOnly", ValueType.BOOLEAN, ""),//"false"),
						//att("extends", ValueType.TYPE_MOD, ""),
						att("extensionAPIVersion", ValueType.INTEGER, ""),
						att("hidden", ValueType.BOOLEAN, ""),//"false"),
						att("name", ValueType.STRING, ""),
						att("private", ValueType.BOOLEAN, ""),//"false"),
						att("release", ValueType.INTEGER, ""),//"1"),
						att("UNID", ValueType.UNID, ""),
						att("usesXML", ValueType.BOOLEAN, ""),//"false"),
						att("version", ValueType.STRING, "")//"1.0")
						);
				break;
			//TranscendenceModule is an extension with only DesignTypes
			case TranscendenceModule:
				e.addOptionalMultipleSubElements(ExtensionElements.values());
				e.addOptionalSingleSubElements(new DesignElement("Modules") {{
					addOptionalMultipleSubElements(ExtensionElements.Module);
				}});
				break;
			default:
				try {
					throw new Exception("Not an Extension");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			e.addOptionalSingleSubElements(new DesignElement("Globals"));
			for(Types t : Types.values()) {
				if(!t.equals(Types.AdventureDesc)) {
					e.addOptionalMultipleSubElements(t);
				}
			}
			return e;
		}
	}
}
