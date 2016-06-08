package edu.iis.mto.staticmock;

import java.util.ArrayList;
import java.util.List;

public class PublishableNews {

	public static PublishableNews create() {
		return new PublishableNews();
	}

	private final List<String> publicContent = new ArrayList<>();
	private final List<String> subscribentContent = new ArrayList<>();

	public void addPublicInfo(String content) {
		this.getPublicContent() .add(content);
		
	}

	public void addForSubscription(String content, SubsciptionType subscriptionType) {
		this.getSubscribentContent() .add(content);
		
	}

	public List<String> getSubscribentContent() {
		return subscribentContent;
	}

	public List<String> getPublicContent() {
		return publicContent;
	}

}
