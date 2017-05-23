package designType;

import xml.Attribute;
import xml.Attribute.ValueType;

public class Power extends DesignType {
	public Power() {
		super();
		addAttributes(
				new Attribute("name", ValueType.STRING, ""),
				new Attribute("key", ValueType.CHARACTER, "")
				);
		addSubElements(
				new DesignElement("OnShow"),
				new DesignElement("OnInvokedByPlayer"),
				new DesignElement("OnInvoke"),
				new DesignElement("OnDestroyCheck")
				);
	}
}
