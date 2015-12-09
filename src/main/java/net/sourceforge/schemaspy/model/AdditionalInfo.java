package net.sourceforge.schemaspy.model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AdditionalInfo {
	
	private String key;
	private String value;
	
	public AdditionalInfo(Node adNode) {
		NodeList childNodes = adNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); ++i) {
            Node item = childNodes.item(i);
            if("additionalKey".equals(item.getNodeName())){
            	key = item.getTextContent();
            }else if("additionalInfoValue".equals(item.getNodeName())){
            	value = item.getTextContent();
            }
        }
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
